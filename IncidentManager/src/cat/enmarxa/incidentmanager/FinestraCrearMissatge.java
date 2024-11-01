package cat.enmarxa.incidentmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * La classe FinestraCrearMissatge és l'encarregada de crear la finestra (interfície gràfica) quan l'usuari selecciona l'opció de "Crear un nou missatge" des de la FinestraPrincipal de l'aplicació. Aquesta classe pertany a la part client de l'aplicació o 'Vista' dels del patró de disseny MVC.
 */
public class FinestraCrearMissatge extends JDialog {
    private JTextField emailDeField;
    private JComboBox<String> emailPerField;
    private JTextArea missatgeField;
    private JButton crearButton, sortirButton;
    private ServeiUsuari serveiUsuari;
    private ServeiMissatge serveiMissatge;

    public FinestraCrearMissatge(Frame parent, String emailDe) {
        super(parent, "Crear Missatge", true);
        serveiUsuari = new ServeiUsuari();
        serveiMissatge = new ServeiMissatge();

        // Establece el layout GridBagLayout para más control
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta y campo de texto para el correo electrónico del remitente
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Email de:"), gbc);
        
        gbc.gridx = 1;
        emailDeField = new JTextField(emailDe, 20);
        emailDeField.setEditable(false); // Campo no editable
        add(emailDeField, gbc);

        // Etiqueta y comboBox para el correo electrónico del destinatario
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Email per:"), gbc);
        
        gbc.gridx = 1;
        emailPerField = new JComboBox<>();
        cargarEmailsDestinatarios(emailDe); // Excluir el propio correo del remitente
        add(emailPerField, gbc);

        // Etiqueta y campo de texto para el contenido del mensaje
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(new JLabel("Missatge:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH; // Para que el campo crezca verticalmente
        gbc.weighty = 1.0; // Para permitir el crecimiento vertical del área de texto
        missatgeField = new JTextArea(10, 30); // Área de texto más grande
        missatgeField.setLineWrap(true); // Ajuste de línea automático
        missatgeField.setWrapStyleWord(true); // Ajuste de línea entre palabras
        JScrollPane scrollPane = new JScrollPane(missatgeField); // Agregar scroll si el texto es largo
        add(scrollPane, gbc);

        // Panel de botones
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel botonsPanel = new JPanel();

        // Botón para crear el mensaje
        crearButton = new JButton("Crear Missatge");
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearMissatge();
            }
        });
        botonsPanel.add(crearButton);

        // Botón para salir
        sortirButton = new JButton("Sortir");
        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana
            }
        });
        botonsPanel.add(sortirButton);

        add(botonsPanel, gbc);

        // Configuración de la ventana
        setSize(500, 400);
        setResizable(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // Método para cargar emails de destinatarios excluyendo el propio correo del remitente
    private void cargarEmailsDestinatarios(String emailDe) {
        try {
            List<String> emails = serveiUsuari.getLlistatEmailUsuaris(); // Obtener lista de correos
            for (String email : emails) {
                if (!email.equals(emailDe)) { // Excluir el correo del remitente
                    emailPerField.addItem(email);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al carregar els correus dels destinataris.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearMissatge() {
        String emailDe = emailDeField.getText();
        String emailPer = (String) emailPerField.getSelectedItem();
        String missatge = missatgeField.getText();

        if (missatge.isEmpty() || emailPer == null) {
            JOptionPane.showMessageDialog(this, "Tots els camps han d'estar omplerts.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean creacioExitosa = serveiMissatge.crearMissatge(emailDe, emailPer, missatge); // Método que guarda el mensaje en la base de datos

        if (creacioExitosa) {
            JOptionPane.showMessageDialog(this, "Missatge creat amb èxit.");
            missatgeField.setText(""); // Limpiar el campo del mensaje para un nuevo ingreso
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear el missatge.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
