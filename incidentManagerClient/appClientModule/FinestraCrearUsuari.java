import javax.swing.*;
import cat.enmarxa.incidentmanager.ServeiUsuari;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Classe que defineix la finestra per crear un nou usuari.
 * Permet introduir les dades de l'usuari com el correu electrònic, contrasenya, intents fallits,
 * àrea de treball, cap superior, rol, i comentaris.
 * @author Enrique
 */
public class FinestraCrearUsuari extends JDialog {
    private JTextField emailField;
    private JTextField contrasenyaField;
    private JComboBox<String> intentsFallitsField;
    private JComboBox<String> areaField;
    private JComboBox<String> capField;
    private JComboBox<String> rolField;
    private JTextField comentarisField;
    private JPanel panelBotons = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JButton crearButton;
    private ServeiUsuari serveiUsuari;

    /**
     * Constructor de la finestra per crear un usuari.
     * @param parent la finestra pare.
     */
    public FinestraCrearUsuari(Frame parent) {
        super(parent, "Crear Usuari", true);
        serveiUsuari = new ServeiUsuari();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        // Configura el camp per introduir l'email
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        add(emailField, gbc);

        // Configura el camp per introduir la contrasenya
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Contrasenya:"), gbc);
        gbc.gridx = 1;
        contrasenyaField = new JTextField(20);
        add(contrasenyaField, gbc);

        // Configura el camp per seleccionar els intents fallits inicials
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Intents fallits inicials:"), gbc);
        gbc.gridx = 1;
        intentsFallitsField = new JComboBox<>(new String[]{"0", "1", "2", "3", "4"});
        add(intentsFallitsField, gbc);

        // Configura el camp per seleccionar l'àrea de treball
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Àrea:"), gbc);
        gbc.gridx = 1;
        areaField = new JComboBox<>(new String[]{"RRHH", "Vendes", "Màrqueting", "IT", "Compres", "Producció", "Comptabilitat"});
        add(areaField, gbc);

        // Configura el camp per seleccionar el cap superior
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Cap:"), gbc);
        gbc.gridx = 1;
        capField = new JComboBox<>();
        ArrayList<String> llistaCaps = (ArrayList<String>) serveiUsuari.getLlistatEmailCaps();
        for (String cap : llistaCaps) {
            capField.addItem(cap);
        }
        add(capField, gbc);

        // Configura el camp per seleccionar el rol de l'usuari
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        rolField = new JComboBox<>(new String[]{"Usuari", "Tècnic", "Administrador", "Gestor"});
        add(rolField, gbc);

        // Configura el camp per introduir comentaris
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Comentaris:"), gbc);
        gbc.gridx = 1;
        comentarisField = new JTextField(250);
        add(comentarisField, gbc);

        // Configura el botó per crear l'usuari
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        crearButton = new JButton("Crear Usuari");
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearUsuari();
            }
        });
        panelBotons.add(crearButton);
        add(panelBotons, gbc);

        getRootPane().setDefaultButton(crearButton);

        // Configura la finestra
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Crea un usuari amb les dades introduïdes als camps de la finestra.
     * Mostra un missatge d'error si algun dels camps és buit.
     */
    private void crearUsuari() {
        String email = emailField.getText();
        String contrasenya = contrasenyaField.getText();
        String intentsFallits = (String) intentsFallitsField.getSelectedItem();
        String area = (String) areaField.getSelectedItem();
        String cap = capField.getSelectedItem().toString();
        String rol = (String) rolField.getSelectedItem();
        String comentaris = comentarisField.getText();

        if (email.isEmpty() || contrasenya.isEmpty() || intentsFallits.isEmpty() || area.isEmpty() || cap.isEmpty() || rol.isEmpty() || comentaris.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tots els camps han d'estar omplerts.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean creacioExitosa = serveiUsuari.crearUsuari(email, contrasenya, intentsFallits, area, cap, rol, comentaris);

        if (creacioExitosa) {
            JOptionPane.showMessageDialog(this, "Usuari creat amb èxit.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear l'usuari.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
}
