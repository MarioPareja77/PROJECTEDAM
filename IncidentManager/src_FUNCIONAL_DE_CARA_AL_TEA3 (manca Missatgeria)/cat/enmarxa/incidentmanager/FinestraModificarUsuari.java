package cat.enmarxa.incidentmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FinestraModificarUsuari extends JDialog {
    private ServeiUsuari serveiUsuari; // Instància del servei d'usuaris
    private JComboBox<String> emailField; // ComboBox per seleccionar el correu de l'usuari
    private JTextField contrasenyaField; // Camp per introduir/modificar la contrasenya
    private JTextField nomField; // Camp per modificar el nom
    private JComboBox<String> areaField; // Selector d'àrea
    private JComboBox<String> capField; // ComboBox per seleccionar el correu del cap
    private JComboBox<String> rolField; // Selector de rol
    private JComboBox<Integer> intentsField; // ComboBox per modificar els intents fallits
    private JTextField comentarisField; // Camp per modificar els comentaris

    public FinestraModificarUsuari(Frame parent) {
        super(parent, "Modificar Usuari", true);
        serveiUsuari = new ServeiUsuari(); // Instanciem el servei d'usuaris

        // Configuració de la finestra
        setLayout(new GridBagLayout()); // Usarem GridBagLayout per més control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ComboBox per seleccionar l'email de l'usuari a modificar
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("E-mail de l'usuari a modificar:"), gbc);
        emailField = new JComboBox<>(getLlistatEmailUsuaris());
        gbc.gridx = 1;
        add(emailField, gbc);

        // Camp per introduir/modificar la contrasenya
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Contrasenya:"), gbc);
        contrasenyaField = new JPasswordField(20);
        gbc.gridx = 1;
        add(contrasenyaField, gbc);

        // Selector per modificar l'àrea
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Àrea:"), gbc);
        areaField = new JComboBox<>(new String[]{"RRHH", "IT", "Vendes", "Comptabilitat", "Màrqueting", "Compres", "Producció"});
        gbc.gridx = 1;
        add(areaField, gbc);

        // ComboBox per seleccionar el cap
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Cap:"), gbc);
        capField = new JComboBox<>(getLlistatEmailCaps());
        gbc.gridx = 1;
        add(capField, gbc);

        // Selector per modificar el rol
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Rol:"), gbc);
        rolField = new JComboBox<>(new String[]{"Usuari", "Tècnic", "Administrador", "Gestor"});
        gbc.gridx = 1;
        add(rolField, gbc);

        // ComboBox per seleccionar els intents fallits
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Intents fallits:"), gbc);
        intentsField = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4});
        gbc.gridx = 1;
        add(intentsField, gbc);

        // Camp per modificar els comentaris
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Comentaris:"), gbc);
        comentarisField = new JTextField(100);
        gbc.gridx = 1;
        add(comentarisField, gbc);

        // Botó per guardar els canvis (centrat)
        JButton modificarButton = new JButton("Modificar Usuari");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarUsuari(); // Crida al mètode per modificar l'usuari
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2; // Ocupa les dues columnes
        gbc.anchor = GridBagConstraints.CENTER; // Centre del GridBag
        add(modificarButton, gbc);

    }

    // Mètode per obtenir la llista de correus electrònics dels usuaris
    private String[] getLlistatEmailUsuaris() {
        List<String> emails = serveiUsuari.getLlistatEmailUsuaris();
        return emails.toArray(new String[0]);
    }

    // Mètode per obtenir la llista de correus electrònics dels usuaris que són caps
    private String[] getLlistatEmailCaps() {
        List<String> emails = serveiUsuari.getLlistatEmailCaps();
        return emails.toArray(new String[0]);
    }

    // Mètode per modificar l'usuari en la base de dades
    private void modificarUsuari() {
        String email = (String) emailField.getSelectedItem();
        String contrasenya = contrasenyaField.getText(); // Obtenim la contrasenya

        if (email == null || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Si us plau, selecciona un e-mail vàlid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String area = (String) areaField.getSelectedItem();
        String cap = (String) capField.getSelectedItem();
        String rol = (String) rolField.getSelectedItem();
        int intentsFallits = (int) intentsField.getSelectedItem();
        String comentaris = comentarisField.getText();

        int confirm = JOptionPane.showConfirmDialog(this, "Estàs segur que vols modificar l'usuari amb e-mail " + email + "?", "Confirmar modificació", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean modificat = serveiUsuari.modificarUsuari(email, contrasenya, area, cap, rol, comentaris, intentsFallits);

            if (modificat) {
                JOptionPane.showMessageDialog(this, "L'usuari amb e-mail " + email + " ha estat modificat correctament.", "Modificació completada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No s'ha trobat cap usuari amb aquest e-mail.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


