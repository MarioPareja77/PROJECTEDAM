package cat.enmarxa.incidentmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FinestraDemanarAlta extends JDialog {
    private JTextField emailField;
    private JTextField bossEmailField;

    public FinestraDemanarAlta(Frame parent) {
        super(parent, "Solicitar Alta", true);

        setSize(400, 250); // Tamaño más adecuado
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Margen entre los componentes

        // Panel principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Borde interno para mayor espacio
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Margen entre los elementos

        // Etiquetas y campos de texto
        JLabel emailLabel = new JLabel("Email de l'usuari:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(emailLabel, gbc);

        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(emailField, gbc);

        JLabel bossEmailLabel = new JLabel("Email del cap:");
        bossEmailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(bossEmailLabel, gbc);

        bossEmailField = new JTextField();
        bossEmailField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(bossEmailField, gbc);

        // Botón de enviar
        JButton submitButton = new JButton("Enviar Sol·licitud");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(59, 89, 182));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setPreferredSize(new Dimension(200, 40));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarSolicitudAlta();
            }
        });

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);

        // Añadir los paneles a la ventana principal
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Centrar la finestra en la pantalla
        setVisible(true);
    }

    private void enviarSolicitudAlta() {
        String userEmail = emailField.getText();
        String bossEmail = bossEmailField.getText();

        // Configuració de propietats per a l'enviament de correu
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Crear una sessió amb la configuració anterior
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("enmarxaincidentmanager@gmail.com", "hpcj fdkz gtad axan"); 
            }
        });

        try {
            // Crear un missatge de correu
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("enmarxaincidentmanager@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(bossEmail));
            message.setSubject("Sol·licitud d'alta d'usuari a aplicació ENMARXA Incident Manager");
            message.setText("L'usuari " + userEmail + " ha sol·licitat l'alta com a usuari a l'aplicació ENMARXA Incident Manager amb data " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " i hora " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + " \n \nRecorda que has de tenir el rol apropiat dins d l'aplicació per poder donar-lo d'alta.");

            // Enviar el correu
            Transport.send(message);
            JOptionPane.showMessageDialog(this, "Si el compte de correu del teu cap existeix, se li haurà \n enviat una sol·licitud d'alta a " + bossEmail, "Èxit", JOptionPane.INFORMATION_MESSAGE);
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en enviar la sol·licitud.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        dispose(); // Tancar la finestra
    }
}
