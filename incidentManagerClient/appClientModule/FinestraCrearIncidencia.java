

import javax.swing.*;

import cat.enmarxa.incidentmanager.ServeiActiu;
import cat.enmarxa.incidentmanager.ServeiIncidencia;

import java.awt.*; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.util.ArrayList; 

/**
 * Classe que defineix la finestra per crear una nova incidència.
 * Permet seleccionar el tipus, prioritat, actius relacionats, i descripció de la incidència.
 * @author Enrique
 */
public class FinestraCrearIncidencia extends JDialog {
    private JTextField descripcioField; 
    private JComboBox<String> tipusField; 
    private JComboBox<String> prioritatField; 
    private JComboBox<String> actiu1Field; 
    private JComboBox<String> actiu2Field; 
    private JButton crearButton; 
    private String usuari; 
    private ServeiIncidencia serveiIncidencia; 
    private ServeiIncidencia serveiActiu; 

    /**
     * Constructor de la finestra per crear una incidència.
     * @param parent la finestra pare.
     * @param usuari l'usuari que crea la incidència.
     */
    public FinestraCrearIncidencia(Frame parent, String usuari) {
        super(parent, "Crear Incidència", true); 
        this.usuari = usuari; 

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 

        serveiIncidencia = new ServeiIncidencia();

        // Campo Descripció
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.5; 
        add(new JLabel("Descripció:"), gbc);
        
        gbc.gridx = 1;
        descripcioField = new JTextField(20); 
        add(descripcioField, gbc);

        // Campo Tipus
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Tipus:"), gbc);
        
        gbc.gridx = 1;
        tipusField = new JComboBox<>(new String[]{"Incidència", "Petició", "Problema", "Canvi"}); 
        add(tipusField, gbc);

        // Campo Prioritat
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Prioritat:"), gbc);
        
        gbc.gridx = 1;
        prioritatField = new JComboBox<>(new String[]{"Baixa", "Normal", "Alta", "Crítica", "Urgent"}); 
        add(prioritatField, gbc);

        // Campo Actiu1 relacionat
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Actiu relacionat #1:"), gbc);
        
        gbc.gridx = 1;
        actiu1Field = new JComboBox<>();
        ServeiActiu serveiActiu1 = new ServeiActiu();
        ArrayList<String> llistaActius1 = (ArrayList<String>) serveiActiu1.getLlistaActius();
        for (String actiu1 : llistaActius1) {
            actiu1Field.addItem(actiu1); 
        }
        add(actiu1Field, gbc);

        // Campo Actiu2 relacionat
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Actiu relacionat #2:"), gbc);
        
        gbc.gridx = 1;
        actiu2Field = new JComboBox<>();
        ServeiActiu serveiActiu2 = new ServeiActiu();
        ArrayList<String> llistaActius2 = (ArrayList<String>) serveiActiu2.getLlistaActius();
        for (String actiu2 : llistaActius2) {
            actiu2Field.addItem(actiu2); 
        }
        add(actiu2Field, gbc);

        // Botó Crear Incidència
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2; 
        crearButton = new JButton("Crear Incidència");
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearIncidencia();
            }
        });
        add(crearButton, gbc);

        // Asigna el botón "Crear Incidència" como acción predeterminada cuando se presione Enter
        getRootPane().setDefaultButton(crearButton);

        // Configuración de la ventana
        setSize(400, 400); 
        setResizable(false); 
        setLocationRelativeTo(parent); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Crea la incidència amb les dades introduïdes en els camps de la finestra.
     * Mostra un missatge d'error si falta informació necessària.
     */
    private void crearIncidencia() {
        String tipus = (String) tipusField.getSelectedItem(); 
        String descripcio = descripcioField.getText(); 
        String prioritat = (String) prioritatField.getSelectedItem(); 
        String usuari = this.usuari; 
        String actiu1 = (String) actiu1Field.getSelectedItem(); 
        String actiu2 = (String) actiu2Field.getSelectedItem();

        if (descripcio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripció no pot estar buida.", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        boolean creacioExitosa = serveiIncidencia.crearIncidencia(tipus, prioritat, "oberta", descripcio, usuari, actiu1, actiu2);
        
        if (creacioExitosa) {
            JOptionPane.showMessageDialog(this, "Incidència creada amb èxit.");
            dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear la incidència.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose(); 
        }
    }
}
