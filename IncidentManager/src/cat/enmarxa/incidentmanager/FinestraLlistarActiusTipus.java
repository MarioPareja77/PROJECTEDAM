package cat.enmarxa.incidentmanager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * La classe FinestraLlistarActiusTipus és l'encarregada de crear la finestra (interfície gràfica) quan l'usuari selecciona l'opció de "Llistar actius segons el seu tipus" des de la FinestraPrincipal de l'aplicació. Aquesta classe pertany a la part client de l'aplicació o 'Vista' dels del patró de disseny MVC.
 */
public class FinestraLlistarActiusTipus extends JDialog {
    private ServeiActiu serveiActiu; // Instància del servei d'actius
    private JComboBox<String> tipusField; // Selector de tipus d'actiu
    
    // Mostrar dates en format europeu (a la BD es desen en format americà)
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public FinestraLlistarActiusTipus(Frame parent) {
        super(parent, "Llistar Actius per Tipus", true);
        serveiActiu = new ServeiActiu();

        // Configuració inicial de la finestra
        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear el selector de tipus
        JPanel tipusPanel = new JPanel();
        tipusPanel.add(new JLabel("Tipus d'Actiu:"));
        tipusField = new JComboBox<>(new String[]{
            "servidor", "PC", "pantalla", "portàtil", "switch", 
            "router", "cablejat", "ratolí", "teclat", "rack", "software"
        }); // Exemples de tipus
        tipusPanel.add(tipusField);
        add(tipusPanel, BorderLayout.NORTH);

        // Botó per mostrar el llistat d'actius
        JButton mostrarButton = new JButton("Mostrar Actius");
        mostrarButton.addActionListener(e -> mostrarLlistatActius((String) tipusField.getSelectedItem())); // Obtenim el tipus seleccionat
        
        // Botó per tancar la finestra
        JButton tancarButton = new JButton("Tancar");
        tancarButton.addActionListener(e -> dispose()); // Tancar la finestra

        // Panell de botons
        JPanel botonsPanel = new JPanel();
        botonsPanel.add(mostrarButton);
        botonsPanel.add(tancarButton);
        add(botonsPanel, BorderLayout.SOUTH);

    }

    // Mètode per mostrar el llistat d'actius segons el seu tipus
    private void mostrarLlistatActius(String tipus) {
        // Usar el servei per obtenir els actius segons el tipus
        List<Actiu> actius = serveiActiu.obtenirActiusTipus(tipus); // Correcció aquí

        // Verificar si s'han trobat actius
        if (actius.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat actius d'aquest tipus.", "Informació", JOptionPane.INFORMATION_MESSAGE);
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
            data[i][3] = dateFormat.format(actiu.getDataAlta()); // Data d'alta en format europeu
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
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Actius del Tipus escollit", JOptionPane.INFORMATION_MESSAGE);
    }
}
