package cat.enmarxa.incidentmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * La classe FinestraEliminarMissatge és l'encarregada de crear la finestra (interfície gràfica) quan l'usuari selecciona l'opció de "Eliminar un missatge" des de la FinestraPrincipal de l'aplicació. Aquesta classe pertany a la part client de l'aplicació o 'Vista' dels del patró de disseny MVC.
 */
public class FinestraEliminarMissatge extends JDialog {
    private ServeiMissatge serveiMissatge; // Instància del servei de missatges
    private JComboBox<Integer> idMissatgeComboBox; // ComboBox per seleccionar l'ID del missatge

    public FinestraEliminarMissatge(Frame parent) {
        super(parent, "Eliminar Missatge", true); 
        serveiMissatge = new ServeiMissatge();

        // Configuració inicial de la finestra
        setLayout(new BorderLayout());
        setSize(400, 200); 
        setLocationRelativeTo(parent); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        
        // Crear el panell per seleccionar l'ID del missatge
        JPanel idMissatgePanel = new JPanel();
        idMissatgePanel.add(new JLabel("ID del missatge a eliminar:"));
        idMissatgeComboBox = new JComboBox<>(getMissatgeIDs()); // Omplir el ComboBox amb IDs
        idMissatgePanel.add(idMissatgeComboBox);
        add(idMissatgePanel, BorderLayout.CENTER);
        
        // Botó per eliminar el missatge
        JButton eliminarButton = new JButton("Eliminar Missatge");
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarMissatge(); // Crida al mètode que eliminarà el missatge
            }
        });
        
        // Botó per sortir
        JButton sortirButton = new JButton("Sortir");
        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tanca la finestra
            }
        });
        
        // Afegir els botons al panell de botons
        JPanel botonsPanel = new JPanel();
        botonsPanel.add(eliminarButton);
        botonsPanel.add(sortirButton); // Afegir el botó "Sortir" al panell
        add(botonsPanel, BorderLayout.SOUTH);
    }

    // Mètode per obtenir tots els IDs dels missatges de la base de dades
    private Integer[] getMissatgeIDs() {
        List<Missatge> missatges = serveiMissatge.llistarMissatges(); // Obtenim la llista de missatges
        return missatges.stream().map(Missatge::getId).toArray(Integer[]::new); // Retornar IDs com a array d'Integer
    }

    // Mètode per eliminar el missatge de la base de dades
    private void eliminarMissatge() {
        Integer idMissatge = (Integer) idMissatgeComboBox.getSelectedItem(); // Obtenir l'ID seleccionat

        if (idMissatge == null) {
            JOptionPane.showMessageDialog(this, "Si us plau, selecciona un ID de missatge.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmació d'eliminació
        int confirm = JOptionPane.showConfirmDialog(this, "Estàs segur que vols eliminar el missatge amb ID " + idMissatge + "?", "Confirmar eliminació", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminat = serveiMissatge.eliminarMissatge(idMissatge); // Crida al servei per eliminar el missatge

            // Mostrar missatge segons el resultat
            if (eliminat) {
                JOptionPane.showMessageDialog(this, "El missatge amb ID " + idMissatge + " ha estat eliminat correctament.", "Eliminació completada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No s'ha trobat cap missatge amb aquest ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
