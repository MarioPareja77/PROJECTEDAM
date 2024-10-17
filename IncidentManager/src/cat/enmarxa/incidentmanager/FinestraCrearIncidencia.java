package cat.enmarxa.incidentmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinestraCrearIncidencia extends JDialog {
    private JTextField descripcioField;
    private JComboBox<String> tipusField;
    private JComboBox<String> prioritatField;
    private JButton crearButton;

    public FinestraCrearIncidencia(Frame parent) {
        super(parent, "Crear Incidència", true);
        setLayout(new GridLayout(5, 2));

        // Camps per la descripció
        add(new JLabel("Descripció:"));
        descripcioField = new JTextField();
        add(descripcioField);

        // Camp per tipus d'incidència
        add(new JLabel("Tipus:"));
        tipusField = new JComboBox<>(new String[]{"Incidència", "Petició", "Problema", "Canvi"});
        add(tipusField);

        // Camp per prioritat
        add(new JLabel("Prioritat:"));
        prioritatField = new JComboBox<>(new String[]{"Baixa", "Normal", "Alta", "Crítica", "Urgent"});
        add(prioritatField);

        // Botó per crear la incidència
        crearButton = new JButton("Crear Incidència");
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearIncidencia();
            }
        });
        add(crearButton);

        setSize(300, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // Mètode per crear la incidència
    private void crearIncidencia() {
        String descripcio = descripcioField.getText();
        String tipus = (String) tipusField.getSelectedItem();
        String prioritat = (String) prioritatField.getSelectedItem();
        String usuari = FinestraLogin.contrasenaField;

        // Validar que la descripció no estigui buida
        if (descripcio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripció no pot estar buida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Enviar la incidència al servidor
    
        boolean creacioExitosa = ServeiIncidencia.crearIncidencia(descripcio, tipus, prioritat, usuari);
        
        if (creacioExitosa) {
            JOptionPane.showMessageDialog(this, "Incidència creada amb èxit.");
            dispose(); // Tancar la finestra
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear la incidència.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
