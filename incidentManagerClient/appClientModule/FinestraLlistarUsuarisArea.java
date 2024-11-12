

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cat.enmarxa.incidentmanager.ServeiUsuari;
import cat.enmarxa.incidentmanager.Usuari;

import java.awt.*;
import java.util.List;

/**
 * Classe que representa una finestra per llistar els usuaris d'una àrea específica.
 * Permet seleccionar l'àrea i mostrar un llistat d'usuaris associats a aquesta àrea.
 * 
 * @author Enrique
 */
public class FinestraLlistarUsuarisArea extends JDialog {
    private JComboBox<String> areaField; // Desplegable per seleccionar l'àrea
    private ServeiUsuari serveiUsuari; // Instància del servei d'usuaris

    /**
     * Constructor de la finestra per llistar els usuaris d'una àrea.
     * 
     * @param parent la finestra pare
     */
    public FinestraLlistarUsuarisArea(Frame parent) {
        super(parent, "Llistar Usuaris per Àrea", true);
        serveiUsuari = new ServeiUsuari();

        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear el desplegable per seleccionar l'àrea
        areaField = new JComboBox<>(new String[]{"RRHH", "IT", "Vendes", "Comptabilitat", "Màrqueting", "Compres", "Producció"}); // Àrees d'exemple
        JPanel areaPanel = new JPanel();
        areaPanel.add(new JLabel("Selecciona l'Àrea:"));
        areaPanel.add(areaField);
        add(areaPanel, BorderLayout.NORTH);

        // Botó per mostrar el llistat d'usuaris segons l'àrea
        JButton mostrarButton = new JButton("Mostrar Usuaris");
        mostrarButton.addActionListener(e -> mostrarLlistatUsuaris());

        // Botó per tancar la finestra
        JButton tancarButton = new JButton("Tancar");
        tancarButton.addActionListener(e -> dispose()); // Tancar la finestra

        // Panell de botons
        JPanel panelBotons = new JPanel();
        panelBotons.add(mostrarButton);
        panelBotons.add(tancarButton);
        add(panelBotons, BorderLayout.SOUTH);

    }

    /**
     * Mètode per mostrar el llistat d'usuaris segons l'àrea seleccionada.
     * 
     */
    private void mostrarLlistatUsuaris() {
        // Obtenir l'àrea seleccionada
        String area = (String) areaField.getSelectedItem();

        // Usar el servei per obtenir els usuaris segons l'àrea seleccionada
        List<Usuari> usuaris = serveiUsuari.obtenirUsuarisArea(area);

        // Verificar si s'han trobat usuaris
        if (usuaris.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat usuaris dins d'aquest àrea.", "Informació", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear un JTable per mostrar els usuaris
        String[] columnNames = {"E-mail", "Àrea", "Cap", "Rol", "Intents Fallits", "Comentaris"};
        Object[][] data = new Object[usuaris.size()][6];

        // Omplir la matriu de dades
        for (int i = 0; i < usuaris.size(); i++) {
            Usuari usuari = usuaris.get(i);
            data[i][0] = usuari.getEmail();
            data[i][1] = usuari.getArea();
            data[i][2] = usuari.getCap();
            data[i][3] = usuari.getRol();
            data[i][4] = usuari.getIntentsFallits();
            data[i][5] = usuari.getComentaris();
        }

        // Crear taula amb les dades i afegir-la a un JScrollPane
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
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Usuaris en l'àrea escollida", JOptionPane.INFORMATION_MESSAGE);
    }
}
