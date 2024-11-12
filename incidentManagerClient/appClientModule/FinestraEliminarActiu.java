

import javax.swing.*;

import cat.enmarxa.incidentmanager.ServeiActiu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe que representa la finestra de diàleg per eliminar un actiu de la base de dades.
 * Permet introduir el nom de l'actiu que es vol eliminar i interactua amb el servei d'actius per completar l'acció.
 * Mostra missatges de confirmació i notificació segons el resultat de l'operació.
 * 
 * @author Enrique
 */
public class FinestraEliminarActiu extends JDialog {
    private ServeiActiu serveiActiu; // Instància del servei d'actius
    private JTextField nomActiuField; // Camp de text per introduir el nom de l'actiu

    /**
     * Constructor de la finestra per eliminar un actiu.
     * Configura els components de la interfície i els afegeix al diàleg.
     * 
     * @param parent El frame pare que obre aquest diàleg.
     */
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

    /**
     * Mètode per eliminar l'actiu de la base de dades.
     * Obté el nom de l'actiu des del camp de text, comprova si l'usuari vol continuar amb l'eliminació
     * i crida el servei per eliminar l'actiu.
     * Mostra missatges de notificació segons si l'actiu ha estat trobat i eliminat amb èxit o no.
     */
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
