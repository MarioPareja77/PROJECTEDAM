package cat.enmarxa.incidentmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.io.*;
import java.net.InetAddress;

public class FinestraLogin extends JFrame {
    private JTextField emailField;
    private JPasswordField contrasenyaField;
    private JTextField servidorField;
    private JTextField portField;
    private String idSessio;

    private Socket socket = null;
    private DataInputStream entrada, entrada2 = null;
    private DataOutputStream sortida = null;

    public FinestraLogin() {
    	  // Configuració de la finestra
        setTitle("ENMARXA Incident Manager v1.0 (octubre 2024)");
        setSize(450, 450); // Ajustar tamaño para incluir el nuevo campo
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Afegim els components
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);  // Establecemos el tamaño a 15 columnas

        JLabel contrasenaLabel = new JLabel("Contrasenya:");
        contrasenyaField = new JPasswordField(15);  // Establecemos el tamaño a 15 columnas

        JLabel servidorLabel = new JLabel("Adreça IP del servidor:");
        servidorField = new JTextField(15); // Campo para la IP del servidor
        servidorField.setText("127.0.0.1"); // Valor predeterminat per la IP

        JLabel portLabel = new JLabel("Port del servidor:");
        portField = new JTextField(5); // Camp per al port
        portField.setText("12345"); // Valor predeterminat per el port

        JButton loginButton = new JButton("Accedir");
        JButton resetButton = new JButton("Resetejar contrasenya");
        JButton altaButton = new JButton("Alta nou Usuari");
        JButton sortirButton = new JButton("Sortir"); // Botó "Sortir"

        // Afegim els components a la finestra
        panel.add(emailLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
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
        panel.add(sortirButton); // Afegir el botó "Sortir"
        add(panel);
        setVisible(true);


        // Acción de los botones
        loginButton.addActionListener(e -> intentarLogin());
        resetButton.addActionListener(e -> resetejarContrasenya());
        altaButton.addActionListener(e -> demanarAltaUsuari());
        sortirButton.addActionListener(e -> sortirAplicacio());

        // Añadir el KeyListener para los campos de entrada
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    intentarLogin(); // Llama a intentarLogin al presionar Enter
                }
            }
        };

        emailField.addKeyListener(enterKeyListener);
        contrasenyaField.addKeyListener(enterKeyListener);
        servidorField.addKeyListener(enterKeyListener);
        portField.addKeyListener(enterKeyListener);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return textField;
    }

    private void intentarLogin() {
        final int timeout = 5000; // Timeout de 5 segundos

        String host = servidorField.getText().trim();
        if (!isValidIP(host)) {
            JOptionPane.showMessageDialog(this, "IP del servidor no vàlida. Si us plau, introdueix una adreça IP vàlida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String port = portField.getText().trim();
        try {
            int portNumber = Integer.parseInt(port);
            if (portNumber < 0 || portNumber > 65535) {
                JOptionPane.showMessageDialog(this, "El port ha de ser un número entre el 0 i el 65535", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Conexión al servidor
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, portNumber), timeout);
                entrada = new DataInputStream(socket.getInputStream());
                entrada2 = new DataInputStream(socket.getInputStream());
                sortida = new DataOutputStream(socket.getOutputStream());

                // Obtener usuario y contraseña
                String usuari = emailField.getText();
                String contrasenya = new String(contrasenyaField.getPassword());

                // Enviar credenciales al servidor
                sortida.writeUTF(usuari);
                sortida.writeUTF(contrasenya);

                // Leer la respuesta del servidor
                String resposta = entrada.readUTF();

                if (resposta.contains("Autenticació exitosa")) {
                    idSessio = resposta.substring(resposta.indexOf(":") + 2);
                    JOptionPane.showMessageDialog(this, "Login exitós. ID de sessió: " + idSessio);
                    ServeiUsuari serveiUsuari = new ServeiUsuari(); 
                    String rol = serveiUsuari.obtenirRolUsuari(usuari);
                    this.dispose();
                    FinestraPrincipal.iniciarFinestra(usuari, rol, idSessio);
                } else {
                	 // String resposta2 = entrada2.readUTF();
                	  //  if (resposta2.contains("5")) {
                	  // 	JOptionPane.showMessageDialog(this, "Compte bloquejat per màxim d'intents permesos. Bye!");
                	  // 	sortirAplicacio();
                	  // }
                    JOptionPane.showMessageDialog(this, "Usuari o contrasenya incorrectes. Torna-ho a provar.");
                }

            } catch (SocketTimeoutException e) {
                JOptionPane.showMessageDialog(this, "Error: Temps de connexió esgotat. No es connectar a la IP " + host, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error en connectar amb el servidor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                closeResources();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El port ha de ser un número vàlid.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidIP(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.getHostAddress().equals(ip);
        } catch (Exception e) {
            return false;
        }
    }

    private void resetejarContrasenya() {
        // Implementa la lógica para resetear la contraseña
        JOptionPane.showMessageDialog(this, "Funcionalitat de reset de contrasenya no implementada.");
    }

    private void demanarAltaUsuari() {
        // Implementa la lógica para solicitar alta de un nuevo usuario
        JOptionPane.showMessageDialog(this, "Funcionalitat d'alta d'usuari no implementada.");
    }

    private void sortirAplicacio() {
        System.exit(0);
    }

    private void closeResources() {
        try {
            if (entrada != null) entrada.close();
            if (sortida != null) sortida.close();
            if (socket != null) socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FinestraLogin finestraLogin = new FinestraLogin();
            finestraLogin.setVisible(true);
        });
    }
}
