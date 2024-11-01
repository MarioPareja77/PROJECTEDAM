package cat.enmarxa.incidentmanager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * La classe FinestraLlistarIncidenciesTipus és l'encarregada de crear la finestra (interfície gràfica) quan l'usuari selecciona l'opció de "Llistar incidències segons el seu tipus" des de la FinestraPrincipal de l'aplicació. Aquesta classe pertany a la part client de l'aplicació o 'Vista' dels del patró de disseny MVC.
 */
public class FinestraLlistarIncidenciesTipus extends JDialog {
    private ServeiIncidencia serveiIncidencia; // Instància del servei d'incidències
    private JComboBox<String> tipusField; // Camp per introduir el tipus d'incidència
    
    // Mostrar dates en format europeu (a la BD es desen en format americà)
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public FinestraLlistarIncidenciesTipus(Frame parent) {
        super(parent, "Llistar Incidències per Tipus", true);
        serveiIncidencia = new ServeiIncidencia();

        // Configuració inicial de la finestra
        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear el camp per introduir el tipus d'incidència
        JPanel tipusPanel = new JPanel();
        tipusPanel.add(new JLabel("Tipus d'incidència:"));
        tipusField = new JComboBox<>(new String[] {
            "Incidència", "Petició", "Problema", "Canvi"
        }); // Selector de tipus d'incidència
        tipusPanel.add(tipusField);
        add(tipusPanel, BorderLayout.NORTH);

        // Botó per mostrar el llistat d'incidències
        JButton mostrarButton = new JButton("Mostrar Incidències");
        mostrarButton.addActionListener(e -> mostrarLlistatIncidencies((String) tipusField.getSelectedItem())); // Obtenim el tipus introduït

        // Botó per tancar la finestra
        JButton tancarButton = new JButton("Tancar");
        tancarButton.addActionListener(e -> dispose()); // Tancar la finestra

        // Panell de botons
        JPanel botonsPanel = new JPanel();
        botonsPanel.add(mostrarButton);
        botonsPanel.add(tancarButton);
        add(botonsPanel, BorderLayout.SOUTH);

    }

    // Mètode per mostrar el llistat d'incidències per tipus
    private void mostrarLlistatIncidencies(String tipus) {
        // Comprovar si s'ha introduït un tipus
        if (tipus == null || tipus.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Si us plau, introdueix un tipus d'incidència.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtenir les incidències a través del servei
        List<Incidencia> incidencies = serveiIncidencia.obtenirIncidenciesTipus(tipus);

        // Verificar si s'han trobat incidències
        if (incidencies.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat incidències per a aquest tipus.", "Informació", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Definir noms de les columnes per la taula
        String[] columnNames = {"ID", "Tipus", "Prioritat", "Descripció", "Email del creador", "Actiu1", "Actiu2", "Data Creació", "Estat", "Usuari creador", "Tècnic assignat"};
        Object[][] data = new Object[incidencies.size()][11]; // Matriu per emmagatzemar les dades

        // Omplir la matriu amb les dades de les incidències
        for (int i = 0; i < incidencies.size(); i++) {
            Incidencia incidencia = incidencies.get(i);
            data[i][0] = incidencia.getId();
            data[i][1] = incidencia.getTipus();
            data[i][2] = incidencia.getPrioritat();
            data[i][3] = incidencia.getDescripcio();
            data[i][4] = incidencia.getEmailCreador();
            data[i][5] = incidencia.getActiu1();
            data[i][6] = incidencia.getActiu2();
            data[i][7] = dateFormat.format(incidencia.getDataCreacio()); // Data d'alta en format europeu
            data[i][8] = incidencia.getEstat();
            data[i][9] = incidencia.getEmailCreador();
            data[i][10] = incidencia.getTecnicAssignat();
        }


        // Crear taula i mostrar les dades
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 400));
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Mostrar la taula en un quadre de diàleg
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Incidències del Tipus escollit", JOptionPane.INFORMATION_MESSAGE);
    }
}
