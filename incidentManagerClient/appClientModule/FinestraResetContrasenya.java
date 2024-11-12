

import javax.swing.*;

import cat.enmarxa.incidentmanager.ServeiUsuari;

import java.awt.*;

/**
 * Classe que representa una finestra modal per permetre als usuaris resetear la seva contrasenya.
 * Aquesta finestra mostra camps per introduir la nova contrasenya i repetir-la per confirmar.
 * Si les contrasenyes coincideixen, es modifica la contrasenya mitjançant el servei d'usuari.
 * 
 * @author Enrique
 */
public class FinestraResetContrasenya extends JDialog {
    private JTextField emailField;
    private JPasswordField newPasswordField;
    private JPasswordField repeatPasswordField;
    private String email;
    private ServeiUsuari serveiUsuari;

    /**
     * Constructor de la finestra de reset de contrasenya.
     * 
     * @param parent La finestra pare que obre la finestra modal.
     * @param email L'email de l'usuari que es mostra al camp de text.
     */
    public FinestraResetContrasenya(Frame parent, String email) {
        super(parent, "Reset Contrasenya", true);

        serveiUsuari = new ServeiUsuari();
        this.email = email;

        setTitle("Finestra per al canvi de contrasenya");
        setSize(350, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel central para los campos de entrada
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField(email);
        emailField.setEditable(false); // Hacer el campo no editable
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Nova contrasenya:"));
        newPasswordField = new JPasswordField();
        inputPanel.add(newPasswordField);

        inputPanel.add(new JLabel("Repetir nova contrasenya:"));
        repeatPasswordField = new JPasswordField();
        inputPanel.add(repeatPasswordField);

        add(inputPanel, BorderLayout.CENTER);

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        JButton resetButton = new JButton("Resetejar contrasenya");
        resetButton.addActionListener(e -> resetearContrasenya());
        buttonPanel.add(resetButton);

        // Establecer el botón de reinicio como el botón predeterminado para Enter
        getRootPane().setDefaultButton(resetButton);

        // Botón "Sortir" para cerrar la ventana
        JButton exitButton = new JButton("Sortir");
        exitButton.addActionListener(e -> dispose());
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(parent);
    }

    /**
     * Mètode que gestiona el reset de la contrasenya de l'usuari.
     * Comprova que la nova contrasenya coincideixi amb la repetida i, si és així, modifica la contrasenya.
     * Si les contrasenyes no coincideixen, es mostra un missatge d'error.
     */
    private void resetearContrasenya() {
        String newPassword = new String(newPasswordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());

        if (!newPassword.equals(repeatPassword)) {
            JOptionPane.showMessageDialog(this, "La nova contrasenya no coincideix. Si us plau, torneu a introduir-la.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean modificat = serveiUsuari.modificarContrasenya(email, newPassword);

        if (modificat) {
            JOptionPane.showMessageDialog(this, "La contrasenya ha estat modificada correctament.", "Modificació completada", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No s'ha trobat cap usuari amb aquest e-mail o la contrasenya actual no és correcta.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        dispose();
    }
}
