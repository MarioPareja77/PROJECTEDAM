

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cat.enmarxa.incidentmanager.Incidencia;
import cat.enmarxa.incidentmanager.ServeiIncidencia;

import java.awt.*;
import java.util.List;

/**
 * Finestra per llistar incidències filtrades per prioritat.
 * Aquesta finestra permet seleccionar una prioritat i mostrar
 * les incidències associades a aquesta prioritat.
 * 
 * @author Enrique
 */
public class FinestraLlistarIncidenciesPrioritat extends JDialog {
    private ServeiIncidencia serveiIncidencia; // Instància del servei d'incidències
    private JComboBox<String> prioritatField; // Selector de prioritat

    /**
     * Constructor de la finestra per llistar incidències per prioritat.
     * 
     * @param parent La finestra pare de la finestra modal.
     */
    public FinestraLlistarIncidenciesPrioritat(Frame parent) {
        super(parent, "Llistar Incidències per Prioritat", true);
        serveiIncidencia = new ServeiIncidencia();

        // Configuració inicial de la finestra
        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear el selector per seleccionar la prioritat
        JPanel prioritatPanel = new JPanel();
        prioritatPanel.add(new JLabel("Prioritat:"));
        prioritatField = new JComboBox<>(new String[] {
            "Baixa", "Normal", "Alta", "Crítica", "Urgent"
        }); // Selector de prioritat
        prioritatPanel.add(prioritatField);
        add(prioritatPanel, BorderLayout.NORTH);

        // Botó per mostrar el llistat d'incidències
        JButton mostrarButton = new JButton("Mostrar Incidències");
        mostrarButton.addActionListener(e -> mostrarLlistatIncidencies((String) prioritatField.getSelectedItem())); // Obtenim la prioritat seleccionada

        // Botó per tancar la finestra
        JButton tancarButton = new JButton("Tancar");
        tancarButton.addActionListener(e -> dispose()); // Tancar la finestra

        // Panell de botons
        JPanel botonsPanel = new JPanel();
        botonsPanel.add(mostrarButton);
        botonsPanel.add(tancarButton);
        add(botonsPanel, BorderLayout.SOUTH);

    }

    /**
     * Mètode per mostrar el llistat d'incidències per prioritat.
     * 
     * @param prioritat La prioritat per filtrar les incidències.
     */
    private void mostrarLlistatIncidencies(String prioritat) {
        // Comprovar si s'ha seleccionat una prioritat
        if (prioritat == null || prioritat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Si us plau, selecciona una prioritat.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtenir les incidències a través del servei
        List<Incidencia> incidencies = serveiIncidencia.obtenirIncidenciesPrioritat(prioritat);

        // Verificar si s'han trobat incidències
        if (incidencies.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat incidències per a aquesta prioritat.", "Informació", JOptionPane.INFORMATION_MESSAGE);
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
            data[i][7] = incidencia.getDataCreacio();
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
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Incidències de la Prioritat escollida", JOptionPane.INFORMATION_MESSAGE);
    }
}
