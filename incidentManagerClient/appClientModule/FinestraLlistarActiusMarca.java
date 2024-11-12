

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cat.enmarxa.incidentmanager.Actiu;
import cat.enmarxa.incidentmanager.ServeiActiu;

import java.awt.*;
import java.util.List;

/**
 * Classe que representa la finestra de diàleg per llistar els actius d'una marca seleccionada.
 * Permet triar una marca i visualitzar els actius associats a aquesta marca en una taula.
 * 
 * @author Enrique
 */
public class FinestraLlistarActiusMarca extends JDialog {
    private ServeiActiu serveiActiu; // Instància del servei d'actius
    private JComboBox<String> marcaField; // Selector de marca

    /**
     * Constructor de la finestra per llistar els actius d'una marca.
     * Configura els components de la interfície i els afegeix a la finestra.
     * 
     * @param parent El frame pare que obre aquest diàleg.
     */
    public FinestraLlistarActiusMarca(Frame parent) {
        super(parent, "Llistar Actius per Marca", true);
        serveiActiu = new ServeiActiu();

        // Configuració inicial de la finestra
        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear el selector de marca
        JPanel marcaPanel = new JPanel();
        marcaPanel.add(new JLabel("Marca d'Actiu:"));
        marcaField = new JComboBox<>(new String[]{
            "Microsoft", "Red Hat", "Ubuntu", "Oracle", "Dell", 
            "HP", "IBM", "Lenovo", "Logitech", "MSI", 
            "Salesforce", "Odoo", "Samsung", "Cisco", "TP-Link", 
            "D-Link", "Acer", "Altres"
        }); // Exemples de marques
        marcaPanel.add(marcaField);
        add(marcaPanel, BorderLayout.NORTH);

        // Botó per mostrar el llistat d'actius
        JButton mostrarButton = new JButton("Mostrar Actius");
        mostrarButton.addActionListener(e -> mostrarLlistatActius((String) marcaField.getSelectedItem())); // Obtenim la marca seleccionada
        
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
     * Mètode per mostrar el llistat d'actius segons la marca seleccionada.
     * Utilitza el servei per obtenir els actius de la marca escollida i els mostra en una taula.
     * Si no es troben actius, mostra un missatge d'informació.
     * 
     * @param marca La marca dels actius a cercar.
     */
    private void mostrarLlistatActius(String marca) {
        // Usar el servei per obtenir els actius segons la marca seleccionada
        List<Actiu> actius = serveiActiu.obtenirActiusMarca(marca);

        // Verificar si s'han trobat actius
        if (actius.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat actius d'aquesta marca.", "Informació", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Definir noms de les columnes per la taula
        String[] columnNames = {"Nom", "Tipus", "Marca", "Data d'alta", "Descripció"};
        Object[][] data = new Object[actius.size()][5]; // Matriu per emmagatzemar les dades

        // Omplir la matriu amb les dades dels actius
        for (int i = 0; i < actius.size(); i++) {
            Actiu actiu = actius.get(i);
            data[i][0] = actiu.getNom();
            data[i][1] = actiu.getTipus();
            data[i][2] = actiu.getMarca();
            data[i][3] = actiu.getDataAlta();
            data[i][4] = actiu.getDescripcio();
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
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Actius de la Marca escollida", JOptionPane.INFORMATION_MESSAGE);
    }
}
