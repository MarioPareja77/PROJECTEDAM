

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cat.enmarxa.incidentmanager.Actiu;
import cat.enmarxa.incidentmanager.ServeiActiu;

/**
 * Classe que representa la finestra de diàleg per llistar els actius d'una àrea seleccionada.
 * Permet triar una àrea i visualitzar els actius associats a aquesta àrea en una taula.
 * 
 * @author Enrique
 */
public class FinestraLlistarActiusArea extends JDialog {
    private JComboBox<String> areaField; // Desplegable per seleccionar l'àrea
    private ServeiActiu serveiActiu; // Instància del servei d'actius

    /**
     * Constructor de la finestra per llistar els actius d'una àrea.
     * Configura els components de la interfície i els afegeix a la finestra.
     * 
     * @param parent El frame pare que obre aquest diàleg.
     */
    public FinestraLlistarActiusArea(Frame parent) {
        super(parent, "Llistar actius per Àrea", true);
        serveiActiu = new ServeiActiu();

        // Configuración inicial de la ventana
        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Cerrar correctamente

        // Crear el desplegable para seleccionar el área
        areaField = new JComboBox<>(new String[]{"RRHH", "IT", "Vendes", "Comptabilitat", "Màrqueting", "Compres", "Producció"});
        JPanel areaPanel = new JPanel();
        areaPanel.add(new JLabel("Selecciona l'Àrea:"));
        areaPanel.add(areaField);
        add(areaPanel, BorderLayout.NORTH);
        

        // Botón para mostrar el listado de activos
        JButton mostrarButton = new JButton("Mostrar Actius");
        mostrarButton.addActionListener(e -> mostrarLlistatActius());

        // Botón para cerrar la ventana
        JButton tancarButton = new JButton("Tancar");
        tancarButton.addActionListener(e -> dispose()); // Cierra la ventana

        // Panel de botones
        JPanel panelBotons = new JPanel();
        panelBotons.add(mostrarButton);
        panelBotons.add(tancarButton);
        add(panelBotons, BorderLayout.SOUTH);

    }

    /**
     * Mètode per mostrar el llistat d'actius segons l'àrea seleccionada.
     * Utilitza el servei per obtenir els actius de l'àrea escollida i els mostra en una taula.
     * Si no es troben actius, mostra un missatge d'informació.
     */
    private void mostrarLlistatActius() {
        // Obtener el área seleccionada
        String area = (String) areaField.getSelectedItem();

        // Usar el servicio para obtener los activos según el área seleccionada
        List<Actiu> actius = serveiActiu.obtenirActiusArea(area);

        // Verificar si se han encontrado activos
        if (actius.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat actius dins d'aquest àrea.", "Informació", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear un JTable para mostrar los activos
        String[] columnNames = {"Nom", "Tipus", "Marca", "Data d'alta", "Descripció"};
        Object[][] data = new Object[actius.size()][5];

        // Llenar la matriz de datos
        for (int i = 0; i < actius.size(); i++) {
            Actiu actiu = actius.get(i);
            data[i][0] = actiu.getNom();
            data[i][1] = actiu.getTipus();
            data[i][2] = actiu.getMarca();
            data[i][3] = actiu.getDataAlta();
            data[i][4] = actiu.getDescripcio();
        }

        // Crear tabla con los datos y añadirla a un JScrollPane
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 400));
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Mostrar la tabla en un cuadro de diálogo
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Actius en l'àrea escollida", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Mètode principal per executar la finestra.
     * S'executa la finestra en el fil d'esdeveniments de Swing.
     * 
     * @param args Arguments de la línia de comandes (no s'utilitzen).
     */
    public static void main(String[] args) {
        // Ejecutar la ventana en el hilo de eventos
        SwingUtilities.invokeLater(() -> {
            new FinestraLlistarActiusArea(null);
        });
    }
}
