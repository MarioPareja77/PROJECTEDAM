

import javax.swing.*;

import cat.enmarxa.incidentmanager.ServeiIncidencia;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe que representa la finestra de diàleg per eliminar una incidència de la base de dades.
 * Permet introduir l'ID de la incidència que es vol eliminar i interactua amb el servei d'incidències per completar l'acció.
 * Mostra missatges de confirmació i notificació segons el resultat de l'operació.
 * 
 * @author Enrique
 */
public class FinestraEliminarIncidencia extends JDialog {
    private ServeiIncidencia serveiIncidencia; // Instància del servei d'incidències
    private JTextField idField; // Camp de text per introduir l'ID de la incidència

    /**
     * Constructor de la finestra per eliminar una incidència.
     * Configura els components de la interfície i els afegeix al diàleg.
     * 
     * @param parent El frame pare que obre aquest diàleg.
     */
    public FinestraEliminarIncidencia(Frame parent) {
        super(parent, "Eliminar Incidència", true);
        serveiIncidencia = new ServeiIncidencia(); // Crear instància del servei d'incidències

        // Configuració de la finestra
        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Crear camp de text per introduir l'ID de la incidència
        JPanel idPanel = new JPanel();
        idPanel.add(new JLabel("ID de la incidència a eliminar:"));
        idField = new JTextField(20); // Camp de text per introduir l'ID
        idPanel.add(idField);
        add(idPanel, BorderLayout.CENTER);

        // Botó per eliminar la incidència
        JButton eliminarButton = new JButton("Eliminar Incidència");
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarIncidencia(); // Crida al mètode que elimina la incidència
            }
        });
        JPanel botonsPanel = new JPanel();
        botonsPanel.add(eliminarButton);
        add(botonsPanel, BorderLayout.SOUTH);

        setVisible(true); // Mostrar la finestra
    }

    /**
     * Mètode per eliminar la incidència de la base de dades.
     * Obté l'ID de la incidència des del camp de text, comprova si l'usuari vol continuar amb l'eliminació
     * i crida el servei per eliminar la incidència.
     * Mostra missatges de notificació segons si la incidència ha estat trobada i eliminada amb èxit o no.
     */
    private void eliminarIncidencia() {
        String idText = idField.getText(); // Obtenir l'ID de la incidència

        // Verificar si s'ha introduït un ID vàlid
        if (idText == null || idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Si us plau, introdueix un ID vàlid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idText); // Convertir el text a número enter

            // Confirmació d'eliminació
            int confirm = JOptionPane.showConfirmDialog(this, "Estàs segur que vols eliminar la incidència amb ID " + id + "?", "Confirmar eliminació", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean eliminat = serveiIncidencia.eliminarIncidencia(id); // Crida al servei per eliminar la incidència

                // Mostrar missatge segons el resultat
                if (eliminat) {
                    JOptionPane.showMessageDialog(this, "La incidència amb ID " + id + " ha estat eliminada correctament.", "Eliminació completada", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No s'ha trobat cap incidència amb aquest ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "L'ID ha de ser un número enter.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
