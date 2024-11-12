

import javax.swing.*;

import cat.enmarxa.incidentmanager.ServeiActiu;
import cat.enmarxa.incidentmanager.ServeiIncidencia;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/**
 * Classe que representa la finestra de modificació d'una incidència.
 * Permet modificar les dades d'una incidència existent.
 * 
 * @author Enrique
 */
public class FinestraModificarIncidencia extends JDialog {
    private ServeiIncidencia serveiIncidencia; // Instància del servei d'incidències
    private ServeiActiu serveiActiu; // Instància del servei d'actius
    private JComboBox<String> idIncidenciaField;
    private JComboBox<String> tipusField; // Selector de tipus d'incidència
    private JComboBox<String> prioritatField; // Selector de prioritat
    private JTextField descripcioField; // Camp per la descripció
    private JComboBox<String> actiu1Field; // ComboBox per Actiu1
    private JComboBox<String> actiu2Field; // ComboBox per Actiu2
    private JComboBox<String> tecnicAssignatField; // Selector de tècnic assignat
    private JComboBox<String> estatField; // Selector de l'estat de la incidència

    /**
     * Constructor de la finestra per modificar una incidència.
     * 
     * @param parent El frame pare que obrirà aquesta finestra
     */
    public FinestraModificarIncidencia(Frame parent) {
        super(parent, "Modificar Incidència", true);
        serveiIncidencia = new ServeiIncidencia(); // Crear instància del servei d'incidències
        serveiActiu = new ServeiActiu(); // Crear instància del servei d'actius

        // Configuració de la finestra
        setLayout(new GridBagLayout()); // Usar GridBagLayout para mayor flexibilidad
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Asegurar que los componentes ocupen todo el ancho

        setSize(400, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Camp per introduir l'ID de la incidència
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        add(new JLabel("ID de la incidència a modificar:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        idIncidenciaField = new JComboBox<>(getLlistaIncidenciesID());
        add(idIncidenciaField, gbc);

        // Selector de tipus d'incidència
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.2;
        add(new JLabel("Tipus d'incidència:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tipusField = new JComboBox<>(new String[]{"Incidència", "Petició", "Problema", "Canvi"});
        add(tipusField, gbc);

        // Selector de prioritat
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.2;
        add(new JLabel("Prioritat:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        prioritatField = new JComboBox<>(new String[]{"Baixa", "Normal", "Alta", "Crítica", "Urgent"});
        add(prioritatField, gbc);

        // Camp per introduir la descripció
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.2;
        add(new JLabel("Descripció:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        descripcioField = new JTextField(50);
        add(descripcioField, gbc);

        // ComboBox per seleccionar el primer actiu relacionat
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.2;
        add(new JLabel("Actiu 1:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        actiu1Field = new JComboBox<>(getLlistaActius()); // Usar la consulta a la base de datos
        add(actiu1Field, gbc);

        // ComboBox per seleccionar el segon actiu relacionat
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.2;
        add(new JLabel("Actiu 2:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        actiu2Field = new JComboBox<>(getLlistaActius()); // Usar la consulta a la base de datos
        add(actiu2Field, gbc);

        // Selector de tècnic assignat
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.2;
        add(new JLabel("Tècnic assignat:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tecnicAssignatField = new JComboBox<>(new String[]{"tecnic1", "tecnic2", "tecnic3", "tecnic4", "tecnic5", "tecnic6", "tecnic7", "tecnic8", "tecnic9", "tecnic10"});
        add(tecnicAssignatField, gbc);

        // Selector de l'estat de la incidència
        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0.2;
        add(new JLabel("Estat de la incidència:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        estatField = new JComboBox<>(new String[]{"Oberta", "Treballant", "Esperant", "Escalada", "Resolta", "Tancada"});
        add(estatField, gbc);

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel(); // Panel para los botones
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centrar contenido

        // Botón per guardar els canvis
        JButton modificarButton = new JButton("Modificar Incidència");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarIncidencia(); // Crida al mètode per modificar la incidència
            }
        });
        buttonPanel.add(modificarButton); // Añadir el botón al panel

        // Botón per tancar la finestra
        JButton tancarButton = new JButton("Tancar");
        tancarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tancar la finestra
            }
        });
        buttonPanel.add(tancarButton); // Añadir el botón de tancar al panel

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2; // Ocupa dos espacios en el eje x
        add(buttonPanel, gbc); // Agregar panel de botones a la ventana
    }

    /**
     * Obté la llista d'actius de la base de dades.
     * 
     * @return Un array de cadenes amb els noms dels actius
     */
    private String[] getLlistaActius() {
        List<String> actius = serveiActiu.getLlistaActius(); // Suponemos que el serveiIncidencia tiene este método
        return actius.toArray(new String[0]);
    }
    
    /**
     * Obté la llista d'ID de totes les incidències obertes.
     * 
     * @return Un array de cadenes amb les ID de les incidències
     */
    private String[] getLlistaIncidenciesID() {
        List<String> incidencies = serveiIncidencia.getLlistaIncidenciesID(); // Suponemos que el serveiIncidencia tiene este método
        Collections.sort(incidencies, new Comparator<String>() {
            @Override
            public int compare(String num1, String num2) {
                return Integer.compare(Integer.parseInt(num1), Integer.parseInt(num2));
            }
        });
        return incidencies.toArray(new String[0]);
    }
    

    /**
     * Modifica una incidència a la base de dades amb les dades introduïdes a la finestra.
     */
    private void modificarIncidencia() {
        String idIncidencia = (String) idIncidenciaField.getSelectedItem(); // Obtenir l'ID de la incidència

        // Comprovar que s'ha introduït un ID vàlid
        if (idIncidencia == null || idIncidencia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Si us plau, introdueix un ID vàlid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idIncidencia); // Convertir l'ID a número enter

            // Obtenir els valors dels camps
            String tipus = (String) tipusField.getSelectedItem();
            String prioritat = (String) prioritatField.getSelectedItem();
            String descripcio = descripcioField.getText();
            String actiu1 = (String) actiu1Field.getSelectedItem();
            String actiu2 = (String) actiu2Field.getSelectedItem();
            String estat = (String) estatField.getSelectedItem();
            String tecnicAssignat = (String) tecnicAssignatField.getSelectedItem();

            // Confirmació de modificació
            int confirm = JOptionPane.showConfirmDialog(this, "Estàs segur que vols modificar la incidència amb ID " + id + "?", "Confirmar modificació", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean modificat = serveiIncidencia.modificarIncidencia(id, tipus, prioritat, descripcio, actiu1, actiu2, estat, tecnicAssignat); // Crida al servei per modificar la incidència

                // Mostrar missatge segons el resultat
                if (modificat) {
                    JOptionPane.showMessageDialog(this, "La incidència amb ID " + id + " ha estat modificada correctament.", "Modificació completada", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No s'ha trobat cap incidència amb aquest ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "L'ID ha de ser un número enter.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
