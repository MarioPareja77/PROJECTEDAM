package cat.enmarxa.incidentmanager;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class FinestraLogin extends JFrame {
    public JTextField emailField;
    JPasswordField contrasenaField;
    private ServeiLogin serveiLogin; // Referencia al servei de login

    public FinestraLogin() {
        // Configuració de la finestra
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        // Afegim els components
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel contrasenaLabel = new JLabel("Contrasenya:");
        contrasenaField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton resetButton = new JButton("Resetejar contrasenya");
        JButton altaButton = new JButton("Alta nou Usuari");

        // Afegim els components a la finestra
        add(emailLabel);
        add(emailField);
        add(contrasenaLabel);
        add(contrasenaField);
        add(loginButton);
        add(resetButton);
        add(altaButton);

        // Accions dels botons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intentarLogin();
            }
        });

        // Funció per resetejar la contrasenya
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetejarContrasenya();
            }
        });

        // Funció per alta d'un nou usuari
        altaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                altaUsuari();
            }
        });

        // Connexió al servidor
        try {
            Socket socket = new Socket("localhost", 12345); // Connexió amb el servidor
            serveiLogin = new ServeiLogin(null, socket); // Passar la connexió a la classe ServeiLogin
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error en connectar amb el servidor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Tancar l'aplicació si no es pot connectar
        }

        setVisible(true);
    }

    // Mètode per intentar fer login
    private void intentarLogin() {
        String email = emailField.getText();
        String contrasena = new String(contrasenaField.getPassword());

        if (serveiLogin.iniciarSessio(email, contrasena)) {
            // Si el login és exitós, obrir la finestra principal de l'aplicació
            new FinestraPrincipal(serveiLogin);
            this.dispose(); // Tancar la finestra de login
        } else {
            JOptionPane.showMessageDialog(this, "Login fallit. Comproveu les credencials.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// Mètode per resetejar la contrasenya
private void resetejarContrasenya() {
    String email = emailField.getText();

    // Validar si el camp de l'email no està buit
    if (email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "El camp email no pot estar buit.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Enviar sol·licitud per resetejar la contrasenya
    boolean enviamentExitosa = serveiLogin.enviarEmailReseteigContrasenya(email);

    // Notificar l'usuari sobre l'estat de l'enviament
    if (enviamentExitosa) {
        JOptionPane.showMessageDialog(this, "Email per resetejar la contrasenya enviat a: " + email);
    } else {
        JOptionPane.showMessageDialog(this, "Error en enviar l'email per resetejar la contrasenya.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

// Mètode per donar alta un nou usuari
private void altaUsuari() {
    String email = emailField.getText();
    
    // Validar si l'email no és buit
    if (email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "El camp de l'e-mail no pot estar buït.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validar si l'email segueix un format correcte
        if (!validarEmail(email)) {
            JOptionPane.showMessageDialog(FinestraLogin.this, "El format introduït per a l'e-mail és incorrecte", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
        }

    // Preguntar l'àrea
    String area = JOptionPane.showInputDialog(this, "Introdueix l'àrea:");
    
    // Validar si l'àrea no és buida
    if (area == null || area.isEmpty()) {
        JOptionPane.showMessageDialog(this, "L'àrea no pot estar buïda.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validar l'àrea
        if (!validarArea(area)) {
            JOptionPane.showMessageDialog(this, "Àrea no vàlida. Àrees permeses: Vendes, Comptabilitat, Compres, Màrqueting, RRHH, Producció, IT", "Error" , JOptionPane.ERROR_MESSAGE);
            return;
        }

    // Mètode per validar el format de l'email
    private boolean validarEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Mètode per validar l'àrea
    private boolean validarArea(String area) {
        String[] areasPermeses = {"Vendes", "Comptabilitat", "Compres", "Màrqueting", "RRHH", "Producció", "IT"};
        for (String a : areasPermeses) {
            if (a.equalsIgnoreCase(area)) {
                return true;
            }
        }
        return false;
    }


    // Enviar sol·licitud d'alta al cap de l'àrea a través del servei de login
    boolean altaExitosa = serveiLogin.solicitarAltaUsuari(email, area);

    // Notificar l'usuari sobre l'estat de la sol·licitud
    if (altaExitosa) {
        JOptionPane.showMessageDialog(this, "Sol·licitud d'alta enviada al responsable d'àrea de l'usuari, a l'adreça: " + email);
    } else {
        JOptionPane.showMessageDialog(this, "Error en enviar la sol·licitud d'alta.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(FinestraLogin::new);
    }
}
