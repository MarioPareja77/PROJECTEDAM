package cat.enmarxa.incidentmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe FinestraEliminarUsuari és l'encarregada de crear la finestra (interfície gràfica) quan l'usuari selecciona l'opció de "Eliminar un usuari" des de la FinestraPrincipal de l'aplicació. Aquesta classe pertany a la part client de l'aplicació o 'Vista' dels del patró de disseny MVC.
 */
public class FinestraEliminarUsuari extends JDialog {
    private ServeiUsuari serveiUsuari; // Instància del servei d'usuaris
    private JTextField emailField; // Camp de text per introduir l'email de l'usuari

    public FinestraEliminarUsuari(Frame parent) {
        super(parent, "Eliminar Usuari", true);
        serveiUsuari = new ServeiUsuari(); // Crear instància del servei d'usuaris
        
        // Configuració de la finestra
        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Crear camp de text per introduir l'email de l'usuari
        JPanel emailPanel = new JPanel();
        emailPanel.add(new JLabel("E-mail de l'usuari a eliminar:"));
        emailField = new JTextField(20); // Camp de text per introduir l'email
        emailPanel.add(emailField);
        add(emailPanel, BorderLayout.CENTER);

        // Botó per eliminar l'usuari
        JButton eliminarButton = new JButton("Eliminar Usuari");
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuari(); // Crida al mètode que elimina l'usuari
            }
        });
        JPanel botonsPanel = new JPanel();
        botonsPanel.add(eliminarButton);
        add(botonsPanel, BorderLayout.SOUTH);

    }

    // Mètode per eliminar l'usuari de la base de dades
    private void eliminarUsuari() {
        String email = emailField.getText(); // Obtenir el correu electrònic

        // Verificar si s'ha introduït un email vàlid
        if (email == null || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Si us plau, introdueix un correu electrònic vàlid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmació d'eliminació
        int confirm = JOptionPane.showConfirmDialog(this, "Estàs segur que vols eliminar l'usuari amb email " + email + "?", "Confirmar eliminació", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminat = serveiUsuari.eliminarUsuari(email); // Crida al servei per eliminar l'usuari

            // Mostrar missatge segons el resultat
            if (eliminat) {
                JOptionPane.showMessageDialog(this, "L'usuari amb email " + email + " ha estat eliminat correctament.", "Eliminació completada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No s'ha trobat cap usuari amb aquest email.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
