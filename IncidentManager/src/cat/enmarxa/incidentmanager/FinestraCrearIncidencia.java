package cat.enmarxa.incidentmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FinestraCrearIncidencia extends JDialog {
    private JTextField descripcioField;
    private JComboBox<String> tipusField;
    private JComboBox<String> prioritatField;
    private JButton crearButton;
	private String usuari;
	private ServeiIncidencia serveiIncidencia;

    public FinestraCrearIncidencia(Frame parent, String usuari) {
        super(parent, "Crear Incidència", true);
        this.usuari = usuari;
        setLayout(new GridLayout(5, 2));
        
        // Inicialitzar serveis
        serveiIncidencia = new ServeiIncidencia();

        // Camps per a la descripció
        add(new JLabel("Descripció:"));
        descripcioField = new JTextField(250);
        add(descripcioField);

        // Camp per al tipus d'incidència
        add(new JLabel("Tipus:"));
        tipusField = new JComboBox<>(new String[]{"Incidència", "Petició", "Problema", "Canvi"});
        add(tipusField);

        // Camp per a la prioritat
        add(new JLabel("Prioritat:"));
        prioritatField = new JComboBox<>(new String[]{"Baixa", "Normal", "Alta", "Crítica", "Urgent"});
        add(prioritatField);
        
        // Camp per al actiu
        add(new JLabel("Actiu relacionat:"));
        JComboBox<String> comboActius = new JComboBox<>();
        ServeiActiu serveiActiu = new ServeiActiu();
        ArrayList<String> llistaActius = (ArrayList<String>) serveiActiu.obtenirNomTotsElsActius();
        for (String actiu : llistaActius) {
        	comboActius.addItem(actiu); 
        }
        add(comboActius);

        // Botó per crear la incidència
        crearButton = new JButton("Crear Incidència");
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearIncidencia();
            }
        });
        add(crearButton);

        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // Mètode per crear la incidència
    private void crearIncidencia() {
    	String tipus = (String) tipusField.getSelectedItem();
        String descripcio = descripcioField.getText();
        String prioritat = (String) prioritatField.getSelectedItem();
        String usuari = this.usuari;

        // Validar que la descripció no estigui buida
        if (descripcio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripció no pot estar buida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Enviar la incidència al servidor
    
        boolean creacioExitosa = serveiIncidencia.crearIncidencia(tipus, prioritat, descripcio, usuari);
        
        if (creacioExitosa) {
            JOptionPane.showMessageDialog(this, "Incidència creada amb èxit.");
            dispose(); // Tancar la finestra
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear la incidència.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose(); // Tancar la finestra
        }
    }
}
