package cat.enmarxa.incidentmanager;

import java.security.GeneralSecurityException;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class Servidor {

    // Mapa per emmagatzemar l'ID de la sessió i el correu electrònic de l'usuari
    private static Map<String, String> sessionsActives = new HashMap<>();
    private static List<SSLSocket> socketsActius = new ArrayList<>(); // Llista de sockets actius per gestionar connexions múltiples

    public static void main(String[] args) {
        final int port = 12345; // Port on escoltarà el servidor

        try {
            // Cargar el almacén de claves
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("C:\\Program Files\\Java\\jdk-23\\bin\\miKeystore.jks"), "12345678".toCharArray());

            // Crear el gestor de claves
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "12345678".toCharArray());

            // Crear el contexto SSL
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // Crear el ServerSocket seguro
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket servidor = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);

            try (FileWriter fw = new FileWriter("servidor.log", true);
                 PrintWriter logWriter = new PrintWriter(fw)) {

                System.out.println("Servidor SSL escoltant pel port " + port);
                logWriter.println("Servidor SSL escoltant pel port " + port);
                logWriter.flush(); // Assegurar que s'escrigui al fitxer

                while (true) {
                    // Acceptar connexions dels clients
                    SSLSocket socket = (SSLSocket) servidor.accept();
                    System.out.println("Client connectat des de " + socket.getInetAddress());
                    logWriter.println("Client connectat des de " + socket.getInetAddress());
                    logWriter.flush();

                    // Afegir el socket a la llista de sockets actius
                    synchronized (socketsActius) {
                        socketsActius.add(socket);
                    }

                    // Crear un fil per gestionar múltiples clients simultàniament
                    new Thread(new FilClient(socket, logWriter)).start();
                }
            }
        } catch (BindException e) {
            System.err.println("Error: El port " + port + " ja està en ús. Finalitzant programa.");
            System.exit(1); // Finalitza el programa amb un codi d'error
        } catch (IOException | GeneralSecurityException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Mètode per tancar totes les connexions amb el servidor
    public static void TancarConnexioServidor() {
        synchronized (socketsActius) {
            for (SSLSocket socket : socketsActius) {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                        System.out.println("Connexió tancada: " + socket.getInetAddress());
                    }
                } catch (IOException e) {
                    System.err.println("Error tancant la connexió: " + e.getMessage());
                }
            }
            socketsActius.clear(); // Netejar la llista de sockets actius
        }
    }

    // Classe interna que gestiona les connexions dels clients en un fil independent
    static class FilClient implements Runnable {
        private SSLSocket socketClient;
        private PrintWriter logWriter;
        private ServeiLogin serveiLogin;

        // Constructor del FilClient, inicialitza el socket del client i el servei de login
        public FilClient(SSLSocket socketClient, PrintWriter logWriter) {
            this.socketClient = socketClient;
            this.logWriter = logWriter;

            // Inicialitzar el ServeiLogin per gestionar l'autenticació
            try {
                this.serveiLogin = new ServeiLogin();
            } catch (SQLException e) {
                System.err.println("Error al inicialitzar ServeiLogin: " + e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try (
                DataInputStream entrada = new DataInputStream(socketClient.getInputStream());
                DataOutputStream sortida = new DataOutputStream(socketClient.getOutputStream());
            ) {
                // Llegir les credencials enviades pel client
                String email = entrada.readUTF();
                String contrasenya = entrada.readUTF();

                // Comprovar l'autenticació
                if (serveiLogin.autenticar(email, contrasenya)) {
                    int comptadorIntents = serveiLogin.obtenirIntentsFallits(email);
                    if (comptadorIntents >= 5) {
                        // Compte bloquejat per múltiples intents fallits
                        System.out.println("El compte " + email + " està bloquejat per intents fallits.");
                        logWriter.println("El compte " + email + " està bloquejat per intents fallits.");
                        sortida.writeUTF("Compte bloquejat");
                    } else {
                        // Autenticació exitosa
                        String idSessio = UUID.randomUUID().toString();

                        // Sincronitzar l'accés al mapa de sessions actives
                        synchronized (sessionsActives) {
                            sessionsActives.put(idSessio, email); // Guardar sessió activa
                        }

                        System.out.println("Sessions actives: " + sessionsActives);
                        sortida.writeUTF("Autenticació exitosa. ID de sessió: " + idSessio);
                        logWriter.println("Usuari autenticat: " + email + " | ID de sessió: " + idSessio);
                        
                        // Reiniciar el comptador d'intents fallits després d'un login exitós
                        serveiLogin.restablirIntentsFallits(email);
                    }
                } else {
                    serveiLogin.augmentarIntentsFallits(email);
                    int comptadorIntents = serveiLogin.obtenirIntentsFallits(email);
                    if (comptadorIntents < 5) {
                        // La compte encara no està bloquejada
                        sortida.writeUTF(String.valueOf(comptadorIntents));
                        System.out.println("Intents fallits restants: " + comptadorIntents);
                        logWriter.println("Autenticació fallida. Usuari o contrasenya incorrectes per: " + email);
                    } else {
                        // Compte bloquejat
                        System.out.println("El compte " + email + " està bloquejat per intents fallits.");
                        logWriter.println("El compte " + email + " està bloquejat per intents fallits.");
                        sortida.writeUTF("Compte bloquejat");
                    }
                }

            } catch (IOException e) {
                System.err.println("Error durant la connexió amb el client: " + e.getMessage());
                logWriter.println("Error durant la connexió amb el client: " + e.getMessage());
            } finally {
                // Eliminar el socket de la llista de sockets actius i tancar la connexió
                synchronized (socketsActius) {
                    socketsActius.remove(socketClient);
                }
                try {
                    socketClient.close();
                } catch (IOException e) {
                    System.err.println("Error tancant el socket: " + e.getMessage());
                }
            }
        }
    }

    // Mètode per obtenir les sessions actives
    public Map<String, String> obtenirSessionsActives() {
        // Retornar el mapa de sessions actives
        synchronized (sessionsActives) {
            return sessionsActives;
        }
    }
}
