

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cat.enmarxa.incidentmanager.Incidencia;
import cat.enmarxa.incidentmanager.ServeiIncidencia;

import java.awt.*;
import java.util.List;

/**
 * Classe que representa la finestra de diàleg per llistar les incidències d'un estat seleccionat.
 * Permet triar un estat i visualitzar les incidències associades a aquest estat en una taula.
 * 
 * @author Enrique
 */
public class FinestraLlistarIncidenciesEstat extends JDialog {
    private ServeiIncidencia serveiIncidencia; // Instància del servei d'incidències
    private JComboBox<String> estatField; // Selector d'estat

    /**
     * Constructor de la finestra per llistar les incidències d'un estat.
     * Configura els components de la interfície i els afegeix a la finestra.
     * 
     * @param parent El frame pare que obre aquest diàleg.
     */
    public FinestraLlistarIncidenciesEstat(Frame parent) {
        super(parent, "Llistar Incidències per Estat", true);
        serveiIncidencia = new ServeiIncidencia();

        // Configuració inicial de la finestra
        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        // Crear el selector d'estat
        JPanel estatPanel = new JPanel();
        estatPanel.add(new JLabel("Estat:"));
        estatField = new JComboBox<>(new String[] {
            "Oberta", "Esperant", "Treballant", "Escalada", "Resolta", "Tancada"
        }); // Selector d'estat
        estatPanel.add(estatField);
        add(estatPanel, BorderLayout.NORTH);

        // Botó per mostrar el llistat d'incidències
        JButton mostrarButton = new JButton("Mostrar Incidències");
        mostrarButton.addActionListener(e -> mostrarLlistatIncidencies((String) estatField.getSelectedItem())); // Obtenim l'estat seleccionat

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
     * Mètode per mostrar el llistat d'incidències per l'estat seleccionat.
     * Utilitza el servei per obtenir les incidències de l'estat escollit i les mostra en una taula.
     * Si no es troben incidències, mostra un missatge d'informació.
     * 
     * @param estat L'estat de les incidències a cercar.
     */
    private void mostrarLlistatIncidencies(String estat) {
        // Comprovar si s'ha seleccionat un estat
        if (estat == null || estat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Si us plau, selecciona un estat.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtenir les incidències a través del servei
        List<Incidencia> incidencies = serveiIncidencia.obtenirIncidenciesEstat(estat);

        // Verificar si s'han trobat incidències
        if (incidencies.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat incidències per a aquest estat.", "Informació", JOptionPane.INFORMATION_MESSAGE);
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
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Incidències de l'Estat escollit", JOptionPane.INFORMATION_MESSAGE);
    }
}
