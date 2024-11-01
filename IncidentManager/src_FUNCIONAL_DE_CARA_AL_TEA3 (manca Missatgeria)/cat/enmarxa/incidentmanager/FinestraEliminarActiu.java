package cat.enmarxa.incidentmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinestraEliminarActiu extends JDialog {
    private ServeiActiu serveiActiu; // Instància del servei d'actius
    private JTextField nomActiuField; // Camp de text per introduir el nom de l'actiu

    public FinestraEliminarActiu(Frame parent) {
        super(parent, "Eliminar Actiu", true); 
        serveiActiu = new ServeiActiu(); 
        
        // Configuració inicial de la finestra
        setLayout(new BorderLayout());
        setSize(400, 200); 
        setLocationRelativeTo(parent); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        
        // Crear el camp per introduir el nom de l'actiu
        JPanel nomActiuPanel = new JPanel();
        nomActiuPanel.add(new JLabel("Nom de l'actiu a eliminar:"));
        nomActiuField = new JTextField(20); // Camp de text per al nom de l'actiu
        nomActiuPanel.add(nomActiuField);
        add(nomActiuPanel, BorderLayout.CENTER);
        
        // Botó per eliminar l'actiu
        JButton eliminarButton = new JButton("Eliminar Actiu");
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarActiu(); // Crida al mètode que eliminarà l'actiu
            }
        });
        JPanel botonsPanel = new JPanel();
        botonsPanel.add(eliminarButton);
        add(botonsPanel, BorderLayout.SOUTH);

    }

    // Mètode per eliminar l'actiu de la base de dades
    private void eliminarActiu() {
        String nomActiu = nomActiuField.getText(); // Obtenir el nom de l'actiu

        // Comprovar si el camp està buit
        if (nomActiu == null || nomActiu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Si us plau, introdueix el nom de l'actiu.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmació d'eliminació
        int confirm = JOptionPane.showConfirmDialog(this, "Estàs segur que vols eliminar l'actiu " + nomActiu + "?", "Confirmar eliminació", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminat = serveiActiu.eliminarActiu(nomActiu); // Crida al servei per eliminar l'actiu

            // Mostrar missatge segons el resultat
            if (eliminat) {
                JOptionPane.showMessageDialog(this, "L'actiu " + nomActiu + " ha estat eliminat correctament.", "Eliminació completada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No s'ha trobat cap actiu amb aquest nom.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
