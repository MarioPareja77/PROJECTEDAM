package cat.enmarxa.incidentmanager;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.sql.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

    private static Map<String, String> sessionsActives = new HashMap<>(); // Map per emmagatzemar l'ID de la sessió i l'usuari (email)
    private static List<Socket> socketsActius = new ArrayList<>(); // Llista de sockets actius

    public static void main(String[] args) {
        final int port = 12345;

        try (FileWriter fw = new FileWriter("servidor.log", true);
             PrintWriter logWriter = new PrintWriter(fw);
             ServerSocket servidor = new ServerSocket(port)) {
            System.out.println("Servidor escoltant pel port " + port);
            logWriter.println("Servidor escoltant pel port " + port);

            while (true) {
                // Acceptar connexions dels clients (esperar a que es connecti un client)
                Socket socket = servidor.accept();
                System.out.println("Client connectat des de " + socket.getInetAddress());
                logWriter.println("Client connectat des de " + socket.getInetAddress());
                logWriter.flush();

                // Crear fil per acceptar múltiples clients
                new Thread(new FilClient(socket, logWriter)).start();
            }
        } catch (BindException e) {
            System.err.println("Error: El port " + port + " ja es troba en ús. Finalitzant el programa.");
            System.exit(1); // Finalitza el programa amb un codi d'error
        } catch (IOException e) {
            System.err.println("Error d'entrada/sortida: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Mètode per tancar totes les connexions amb el servidor
    public static void TancarConnexioServidor() {
        synchronized (socketsActius) {
            for (Socket socket : socketsActius) {
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

    // Classe interna que manega les connexions dels clients en un fil
    static class FilClient implements Runnable {
        private Socket socketClient;
        private PrintWriter logWriter;
        private ServeiLogin serveiLogin;

        public FilClient(Socket socketClient, PrintWriter logWriter) {
            this.socketClient = socketClient;
            this.logWriter = logWriter;

            // Inicialitzar ServeiLogin
            try {
                this.serveiLogin = new ServeiLogin();
            } catch (SQLException e) {
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

                // Comprovar autenticació
                if (serveiLogin.autenticar(email, contrasenya)) {
                    // Generar un ID de sessió únic
                    String idSessio = UUID.randomUUID().toString();

                    // Desar la sessió activa
                    sessionsActives.put(idSessio, email);

                    // Enviar resposta d'autenticació amb l'ID de sessió
                    int comptadorIntents = serveiLogin.obtenirIntentsFallits(email);
                	if (comptadorIntents >= 5) {
                		System.out.println("El compte " + email + " està bloquejat per haver arribat al nombre màxim d'intents permesos. Bye!");
                		logWriter.println("El compte " + email + " està bloquejat per haver arribat al nombre màxim d'intents permesos. Bye!");
                		sortida.writeUTF("Compte bloquejat per haver arribat al nombre màxim d'intents");
                 } else {
                    sortida.writeUTF("Autenticació exitosa. ID de sessió: " + idSessio);
                    System.out.println("Usuari autenticat: " + email + " | ID de sessió: " + idSessio);
                    logWriter.println("Usuari autenticat: " + email + " | ID de sessió: " + idSessio);
                 }
                } else {
                    // Enviar missatge d'autenticació fallida
                    sortida.writeUTF("Autenticació fallida. Usuari o contrasenya incorrectes.");
                    logWriter.println("Autenticació fallida. Usuari o contrasenya incorrectes per a: " + email);
                    serveiLogin.augmentarIntentsFallits(email);
                }

            } catch (IOException e) {
                e.printStackTrace();
                logWriter.println("Error durant la connexió amb el client: " + e.getMessage());
            } finally {
                try {
                    socketClient.close(); // Tancar la connexió amb el client
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
