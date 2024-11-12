

import javax.swing.*;

import cat.enmarxa.incidentmanager.ServeiActiu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Classe per mostrar la finestra de modificació d'un actiu en el sistema.
 * Permet modificar les dades d'un actiu existent.
 * 
 * @author Enrique
 */
public class FinestraModificarActiu extends JDialog {
    private ServeiActiu serveiActiu; // Instància del servei d'actius
    private JComboBox<String> nomField; // Camp per seleccionar el nom de l'actiu
    private JComboBox<String> tipusField; // Selector de tipus d'actiu
    private JComboBox<String> marcaField; // Selector per modificar la marca
    private JComboBox<String> areaField; // Selector per modificar l'àrea
    private JTextArea descripcioField; // Camp per modificar la descripció

    /**
     * Constructor de la finestra de modificació d'un actiu.
     * 
     * @param parent Finestra pare on es mostrarà la finestra de modificació
     */
    public FinestraModificarActiu(Frame parent) {
        super(parent, "Modificar Actiu", true);
        serveiActiu = new ServeiActiu(); // Crear instància del servei d'actius

        // Configuració de la finestra
        setLayout(new GridBagLayout()); // Utilitzar GridBagLayout per major control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Marges entre components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        setSize(500, 400);
        setLocationRelativeTo(parent); // Centrar la finestra en la pantalla
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Camp per seleccionar el nom de l'actiu a modificar (ComboBox amb noms d'actius)
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nom de l'actiu a modificar:"), gbc);
        gbc.gridx = 1;
        nomField = new JComboBox<>(getLlistaActius()); // Omplir el ComboBox amb la llista d'actius
        add(nomField, gbc);

        // Selector de tipus d'actiu
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Tipus d'actiu:"), gbc);
        gbc.gridx = 1;
        tipusField = new JComboBox<>(new String[]{"servidor", "PC", "pantalla", "portàtil", "switch", "router", "cablejat", "teclat", "ratolí", "rack", "software"});
        add(tipusField, gbc);

        // Selector de marca de l'actiu
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1;
        marcaField = new JComboBox<>(new String[]{"Microsoft", "Red Hat", "Ubuntu", "Oracle", "Dell", "HP", "IBM", "Lenovo", "Logitech", "MSI", "Salesforce", "Odoo", "Altres"});
        add(marcaField, gbc);

        // Selector d'àrea de l'actiu
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Àrea:"), gbc);
        gbc.gridx = 1;
        areaField = new JComboBox<>(new String[]{"RRHH", "IT", "Vendes", "Comptabilitat", "Màrqueting", "Compres", "Producció"});
        add(areaField, gbc);

        // Camp per introduir la descripció (usant JTextArea)
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Descripció:"), gbc);
        gbc.gridx = 1;
        descripcioField = new JTextArea(5, 30); // Text area amb més espai (5 files, 30 columnes)
        descripcioField.setLineWrap(true); // Per permetre que es divideixi el text en múltiples línies
        descripcioField.setWrapStyleWord(true); // Divisió per paraules completes
        JScrollPane scrollPane = new JScrollPane(descripcioField); // Afegir un ScrollPane per millorar la visualització
        add(scrollPane, gbc);

        // Panel per al botó, centrat amb FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centra el botó
        JButton modificarButton = new JButton("Modificar Actiu");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarActiu(); // Crida al mètode per modificar l'actiu
            }
        });
        buttonPanel.add(modificarButton); // Afegir botó al panell

        // Agregar el panel del botó al GridBagLayout
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2; // Ocupa dues columnes
        gbc.anchor = GridBagConstraints.CENTER; // Centrar el panell del botó
        add(buttonPanel, gbc);

    }

    /**
     * Mètode per obtenir la llista de noms d'actius.
     * 
     * @return Un array de Strings amb els noms dels actius disponibles
     */
    private String[] getLlistaActius() {
        List<String> actius = serveiActiu.getLlistaActius(); // Consultar els noms d'actius
        return actius.toArray(new String[0]); // Retornar la llista com array de Strings
    }

    /**
     * Mètode per modificar l'actiu en la base de dades.
     * Recull les dades de la finestra i realitza la modificació.
     */
    private void modificarActiu() {
        String nom = (String) nomField.getSelectedItem(); // Obtenim el nom seleccionat

        // Comprovem que s'ha seleccionat un nom vàlid
        if (nom == null || nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Si us plau, selecciona un nom d'actiu vàlid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtenim els valors dels altres camps
        String tipus = (String) tipusField.getSelectedItem();
        String marca = (String) marcaField.getSelectedItem();
        String area = (String) areaField.getSelectedItem();
        String descripcio = descripcioField.getText();

        // Confirmació de modificació
        int confirm = JOptionPane.showConfirmDialog(this, "Estàs segur que vols modificar l'actiu amb nom " + nom + "?", "Confirmar modificació", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean modificat = serveiActiu.modificarActiu(nom, tipus, area, marca, descripcio); // Crida al servei per modificar l'actiu

            // Mostrar missatge segons el resultat
            if (modificat) {
                JOptionPane.showMessageDialog(this, "L'actiu amb nom " + nom + " ha estat modificat correctament.", "Modificació completada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No s'ha trobat cap actiu amb aquest nom.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
