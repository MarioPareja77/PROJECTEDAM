package cat.enmarxa.incidentmanager;

// Importa les classes d'AWT per gestionar la disposició i components gràfics
import java.awt.Dimension;
import java.awt.event.KeyAdapter; // Importa KeyAdapter per gestionar esdeveniments de teclat
import java.awt.event.KeyEvent; // Importa KeyEvent per gestionar esdeveniments de teclat
// Importa classes d'entrada/sortida per gestionar fluxos de dades
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress; // Importa InetAddress per gestionar adreces IP
import java.net.InetSocketAddress; // Importa InetSocketAddress per gestionar adreces de socket
import java.net.Socket; // Importa la classe Socket per gestionar connexions de xarxa
import java.net.SocketTimeoutException; // Importa SocketTimeoutException per gestionar el temps d'espera

// Importa les classes de Swing per crear interfícies gràfiques
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class FinestraLogin extends JFrame {
    private JTextField emailField; // Camp de text per introduir l'email
    private JPasswordField contrasenyaField; // Camp de text per introduir la contrasenya
    private JTextField servidorField; // Camp de text per introduir l'adreça IP del servidor
    private JTextField portField; // Camp de text per introduir el port del servidor
    private String idSessio; // Variable per emmagatzemar l'ID de sessió

    private Socket socket = null; // Socket per gestionar la connexió
    private DataInputStream entrada = null; // Flux d'entrada per gestionar la lectura de dades
    private DataOutputStream sortida = null; // Flux de sortida per gestionar l'escriptura de dades

    // Constructor de la finestra de login
    public FinestraLogin() {
        // Configuració de la finestra
        setTitle("ENMARXA Incident Manager v1.0 (octubre 2024)");
        setSize(450, 450); // Ajustar la mida de la finestra
        setResizable(false); // Desactivar la redimensionabilitat
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Tancar l'aplicació en tancar la finestra
        setLocationRelativeTo(null); // Centrar la finestra a la pantalla

        JPanel panel = new JPanel(); // Crear un panell per organitzar els components
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Establir el layout vertical

        // Afegir els components al panell
        JLabel emailLabel = new JLabel("Email:"); // Etiqueta per al camp de l'email
        emailField = new JTextField(15); // Camp de text per a l'email

        JLabel contrasenaLabel = new JLabel("Contrasenya:"); // Etiqueta per al camp de la contrasenya
        contrasenyaField = new JPasswordField(15); // Camp de text per a la contrasenya

        JLabel servidorLabel = new JLabel("Adreça IP del servidor:"); // Etiqueta per al camp de servidor
        servidorField = new JTextField(15); // Camp de text per a l'adreça IP
        servidorField.setText("127.0.0.1"); // Valor per defecte

        JLabel portLabel = new JLabel("Port del servidor:"); // Etiqueta per al camp de port
        portField = new JTextField(5); // Camp de text per al port
        portField.setText("12345"); // Valor per defecte

        // Crear els botons
        JButton loginButton = new JButton("Accedir"); // Botó d'accés
        JButton resetButton = new JButton("Resetejar contrasenya"); // Botó de reset
        JButton altaButton = new JButton("Alta nou Usuari"); // Botó d'alta d'usuari
        JButton sortirButton = new JButton("Sortir"); // Botó de sortida

        // Afegir els components al panell
        panel.add(emailLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espai entre components
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(contrasenaLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(contrasenyaField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(servidorLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(servidorField);
        panel.add(portLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(portField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(resetButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(altaButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(sortirButton);

        add(panel); // Afegir el panell a la finestra
        setVisible(true); // Fer visible la finestra

        // Accions dels botons
        loginButton.addActionListener(e -> intentarLogin()); // Acció per al botó d'accés
        resetButton.addActionListener(e -> resetejarContrasenya()); // Acció per al botó de reset
        altaButton.addActionListener(e -> demanarAltaUsuari()); // Acció per al botó d'alta
        sortirButton.addActionListener(e -> sortirAplicacio()); // Acció per al botó de sortida

        // Afegir el KeyListener per als camps d'entrada
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    intentarLogin(); // Cridar a intentarLogin al pressionar Enter
                }
            }
        };

        // Afegir el KeyListener als camps d'entrada
        emailField.addKeyListener(enterKeyListener);
        contrasenyaField.addKeyListener(enterKeyListener);
        servidorField.addKeyListener(enterKeyListener);
        portField.addKeyListener(enterKeyListener);
    }

    // Mètode per intentar iniciar sessió
    private void intentarLogin() {
        final int timeout = 5000; // Timeout de 5 segons

        String host = servidorField.getText().trim(); // Obtenir l'adreça IP del servidor
        if (!isValidIP(host)) {
            // Validar que l'adreça IP sigui vàlida
            JOptionPane.showMessageDialog(this, "IP del servidor no vàlida. Si us plau, introdueix una adreça IP vàlida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String port = portField.getText().trim(); // Obtenir el port introduït
        try {
            int portNumber = Integer.parseInt(port); // Convertir el port a número enter
            if (portNumber < 0 || portNumber > 65535) {
                // Validar que el port estigui en el rang vàlid
                JOptionPane.showMessageDialog(this, "El port ha de ser un número entre el 0 i el 65535", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Connexió al servidor
                socket = new Socket(); // Crear un nou socket
                socket.connect(new InetSocketAddress(host, portNumber), timeout); // Connectar al servidor
                entrada = new DataInputStream(socket.getInputStream()); // Inicialitzar el flux d'entrada
                sortida = new DataOutputStream(socket.getOutputStream()); // Inicialitzar el flux de sortida

                // Obtenir usuari i contrasenya
                String usuari = emailField.getText(); // Obtenir l'usuari
                String contrasenya = new String(contrasenyaField.getPassword()); // Obtenir la contrasenya
                int intents, intentsRestants;

                // Enviar les credencials al servidor
                sortida.writeUTF(usuari); // Enviar l'usuari
                sortida.writeUTF(contrasenya); // Enviar la contrasenya

                // Llegir la resposta del servidor
                String resposta = entrada.readUTF(); // Llegir la resposta del servidor

                if (resposta.contains("exitosa")) {
                    // Si la resposta indica que el login és exitós
                    idSessio = resposta.substring(resposta.indexOf(":") + 2); // Obtenir l'ID de sessió
                    JOptionPane.showMessageDialog(this, "Login exitós. ID de sessió: " + idSessio);
                    ServeiUsuari serveiUsuari = new ServeiUsuari(); // Crear un nou servei d'usuari
                    String rol = serveiUsuari.obtenirRolUsuari(usuari); // Obtenir el rol de l'usuari
                    this.dispose(); // Tancar la finestra de login
                    FinestraPrincipal.iniciarFinestra(usuari, rol, idSessio); // Iniciar la finestra principal
                } else {
                    // Si el login no és exitós
                    if (resposta.contains("bloquejat")) {
                        JOptionPane.showMessageDialog(this, "Compte bloquejat a l'aplicació per màxim nombre d'intents permesos sobrepassat!");
                    } else {
                        intents = Integer.parseInt(resposta); // Obtenir el nombre d'intents
                        System.out.println(resposta);
                        System.out.println(intents);
                        intentsRestants = 5 - intents; // Calcular els intents restants
                        if (intentsRestants < 0)
						 {
							intentsRestants = 0; // Assegurar que no sigui negatiu
						}
                        JOptionPane.showMessageDialog(this, "Usuari o contrasenya incorrectes. Torna-ho a provar. \n Et queden " + intentsRestants + " intents.");
                    }
                }

            } catch (SocketTimeoutException e) {
                // Gestionar el timeout de connexió
                JOptionPane.showMessageDialog(this, "Error: Temps de connexió esgotat. No es connectar a la IP " + host, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                // Gestionar errors d'entrada/sortida
                JOptionPane.showMessageDialog(this, "Error en connectar amb el servidor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                closeResources(); // Tancar els recursos al final
            }
        } catch (NumberFormatException e) {
            // Gestionar errors en la conversió del port
            JOptionPane.showMessageDialog(this, "El port ha de ser un número vàlid entre 0 i 65535.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mètode per validar que una adreça IP és vàlida
    private boolean isValidIP(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip); // Obtenir l'adreça
            return address.getHostAddress().equals(ip); // Comparar amb la IP original
        } catch (Exception e) {
            return false; // Retornar fals si hi ha una excepció
        }
    }

    // Mètode per resetejar la contrasenya
    private void resetejarContrasenya() {
        // Informar que la funcionalitat no està implementada
        JOptionPane.showMessageDialog(this, "Funcionalitat de reset de contrasenya no implementada.");
    }

    // Mètode per demanar alta d'usuari
    private void demanarAltaUsuari() {
        // Informar que la funcionalitat no està implementada
        JOptionPane.showMessageDialog(this, "Funcionalitat d'alta d'usuari no implementada.");
    }

    // Mètode per sortir de l'aplicació
    private void sortirAplicacio() {
        System.exit(0); // Tancar l'aplicació
    }

    // Mètode per tancar recursos oberts
    private void closeResources() {
        try {
            if (entrada != null)
			 {
				entrada.close(); // Tancar el flux d'entrada si no és null
			}
            if (sortida != null)
			 {
				sortida.close(); // Tancar el flux de sortida si no és null
			}
            if (socket != null)
			 {
				socket.close(); // Tancar el socket si no és null
			}
        } catch (IOException ex) {
            ex.printStackTrace(); // Imprimir l'error a la consola
        }
    }

    // Mètode principal per executar la finestra de login
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FinestraLogin finestraLogin = new FinestraLogin(); // Crear una nova finestra de login
            finestraLogin.setVisible(true); // Fer visible la finestra
        });
    }
}
