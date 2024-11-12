

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cat.enmarxa.incidentmanager.Incidencia;
import cat.enmarxa.incidentmanager.ServeiIncidencia;

import java.awt.*;
import java.util.List;

/**
 * Classe que representa una finestra per llistar les incidències associades a un usuari.
 * Permet introduir el correu de l'usuari i mostrar un llistat amb les incidències associades.
 * 
 * @author Enrique
 */
public class FinestraLlistarIncidenciesUsuari extends JDialog {
    private ServeiIncidencia serveiIncidencia; // Instància del servei d'incidències
    private JTextField usuariField; // Camp per introduir el correu de l'usuari

    /**
     * Constructor de la finestra per llistar les incidències d'un usuari.
     * 
     * @param parent la finestra pare
     */
    public FinestraLlistarIncidenciesUsuari(Frame parent) {
        super(parent, "Llistar Incidències per Usuari", true);
        serveiIncidencia = new ServeiIncidencia();

        // Configuració inicial de la finestra
        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear el camp per introduir l'usuari
        JPanel usuariPanel = new JPanel();
        usuariPanel.add(new JLabel("E-mail de l'usuari:"));
        usuariField = new JTextField(20);
        usuariPanel.add(usuariField);
        add(usuariPanel, BorderLayout.NORTH);

        // Botó per mostrar el llistat d'incidències
        JButton mostrarButton = new JButton("Mostrar Incidències");
        mostrarButton.addActionListener(e -> mostrarLlistatIncidencies(usuariField.getText())); // Obtenim el text introduït

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
     * Mètode per mostrar el llistat d'incidències associades a un usuari.
     * 
     * @param usuari el correu de l'usuari per cercar les incidències
     */
    private void mostrarLlistatIncidencies(String usuari) {
        // Comprovar si s'ha introduït un correu
        if (usuari == null || usuari.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Si us plau, introdueix el correu de l'usuari.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtenir les incidències a través del servei
        List<Incidencia> incidencies = serveiIncidencia.obtenirIncidenciesUsuari(usuari);

        // Verificar si s'han trobat incidències
        if (incidencies.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat incidències per a aquest usuari.", "Informació", JOptionPane.INFORMATION_MESSAGE);
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
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Incidències de l'Usuari escollit", JOptionPane.INFORMATION_MESSAGE);
    }
}
