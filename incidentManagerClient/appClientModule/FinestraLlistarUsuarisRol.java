

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cat.enmarxa.incidentmanager.ServeiUsuari;
import cat.enmarxa.incidentmanager.Usuari;

/**
 * Classe que representa una finestra per llistar els usuaris d'un rol específic.
 * Permet seleccionar el rol i mostrar un llistat d'usuaris associats a aquest rol.
 * 
 * @author Enrique
 */
public class FinestraLlistarUsuarisRol extends JDialog {
    private JComboBox<String> rolField; // Desplegable per seleccionar el rol
    private ServeiUsuari serveiUsuari; // Instància del servei d'usuaris

    /**
     * Constructor de la finestra per llistar els usuaris d'un rol.
     * 
     * @param parent la finestra pare
     */
    public FinestraLlistarUsuarisRol(Frame parent) {
        super(parent, "Llistar Usuaris per Rol", true);
        serveiUsuari = new ServeiUsuari();

        setLayout(new BorderLayout()); // Establir el layout de la finestra
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear el desplegable per seleccionar el rol
        rolField = new JComboBox<>(new String[]{"Usuari", "Tècnic", "Administrador", "Gestor"}); // Opcions d'exemple
        JPanel rolPanel = new JPanel();
        rolPanel.add(new JLabel("Selecciona el Rol:"));
        rolPanel.add(rolField);
        add(rolPanel, BorderLayout.NORTH); // Afegir el desplegable a la part superior de la finestra

        // Botó per mostrar el llistat d'usuaris segons el rol
        JButton mostrarButton = new JButton("Mostrar Usuaris");
        mostrarButton.addActionListener(e -> mostrarLlistatUsuaris());

        // Botó per tancar la finestra
        JButton tancarButton = new JButton("Tancar");
        tancarButton.addActionListener(e -> dispose()); // Tancar la finestra

        // Panell de botons
        JPanel panelBotons = new JPanel();
        panelBotons.add(mostrarButton);
        panelBotons.add(tancarButton);
        add(panelBotons, BorderLayout.SOUTH); // Afegir el panell de botons a la part inferior de la finestra

    }

    /**
     * Mètode per mostrar el llistat d'usuaris segons el rol seleccionat.
     * 
     */
    private void mostrarLlistatUsuaris() {
        // Obtenir el rol seleccionat
        String rol = (String) rolField.getSelectedItem();

        // Usar el servei per obtenir els usuaris segons el rol seleccionat
        List<Usuari> usuaris = serveiUsuari.obtenirUsuarisRol(rol);

        // Verificar si s'han trobat usuaris
        if (usuaris.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat usuaris amb aquest rol.", "Informació", JOptionPane.INFORMATION_MESSAGE);
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
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'usuaris amb el rol escollit", JOptionPane.INFORMATION_MESSAGE);
    }
}
