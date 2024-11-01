package cat.enmarxa.incidentmanager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * La classe FinestraLlistarIncidenciesUsuari és l'encarregada de crear la finestra (interfície gràfica) quan l'usuari selecciona l'opció de "Llistar incidències segons el seu usuari" des de la FinestraPrincipal de l'aplicació. Aquesta classe pertany a la part client de l'aplicació o 'Vista' dels del patró de disseny MVC.
 */
public class FinestraLlistarIncidenciesUsuari extends JDialog {
    private ServeiIncidencia serveiIncidencia; // Instància del servei d'incidències
    private JComboBox<String> usuariField; // Camp per seleccionar el correu de l'usuari
    
    // Mostrar dates en format europeu (a la BD es desen en format americà)
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public FinestraLlistarIncidenciesUsuari(Frame parent) {
        super(parent, "Llistar Incidències per Usuari", true);
        serveiIncidencia = new ServeiIncidencia();
        ServeiUsuari serveiUsuari = new ServeiUsuari(); // Instància del servei d'usuaris

        // Configuració inicial de la finestra
        setLayout(new BorderLayout());
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Crear el camp per seleccionar l'usuari
        JPanel usuariPanel = new JPanel();
        usuariPanel.add(new JLabel("E-mail de l'usuari:"));

        // Obtenir el llistat d'usuaris
        List<String> emails = serveiUsuari.getLlistatEmailUsuaris();
        usuariField = new JComboBox<>(emails.toArray(new String[0])); // Omplir el JComboBox amb els correus
        usuariPanel.add(usuariField);
        add(usuariPanel, BorderLayout.NORTH);

        // Botó per mostrar el llistat d'incidències
        JButton mostrarButton = new JButton("Mostrar Incidències");
        mostrarButton.addActionListener(e -> mostrarLlistatIncidencies((String) usuariField.getSelectedItem())); // Obtenim el text seleccionat

        // Añadir el KeyListener al JComboBox
        usuariField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    mostrarButton.doClick(); // Simula un clic en el botón
                }
            }
        });

        // Botó per tancar la finestra
        JButton tancarButton = new JButton("Tancar");
        tancarButton.addActionListener(e -> dispose()); // Tancar la finestra

        // Panell de botons
        JPanel botonsPanel = new JPanel();
        botonsPanel.add(mostrarButton);
        botonsPanel.add(tancarButton);
        add(botonsPanel, BorderLayout.SOUTH);

    }

    // Mètode per mostrar el llistat d'incidències per usuari
    private void mostrarLlistatIncidencies(String usuari) {
        // Comprovar si s'ha introduït un correu
        if (usuari == null || usuari.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Si us plau, selecciona el correu de l'usuari.", "Error", JOptionPane.ERROR_MESSAGE);
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
        String[] columnNames = {"ID", "Tipus", "Prioritat", "Descripció", "Actiu1", "Actiu2", "Data Creació", "Estat", "Tècnic assignat"};
        Object[][] data = new Object[incidencies.size()][9]; // Matriu per emmagatzemar les dades

        // Omplir la matriu amb les dades de les incidències
        for (int i = 0; i < incidencies.size(); i++) {
            Incidencia incidencia = incidencies.get(i);
            data[i][0] = incidencia.getId();
            data[i][1] = incidencia.getTipus();
            data[i][2] = incidencia.getPrioritat();
            data[i][3] = incidencia.getDescripcio();
            data[i][4] = incidencia.getActiu1();
            data[i][5] = incidencia.getActiu2();
            data[i][6] = dateFormat.format(incidencia.getDataCreacio()); // Data d'alta en format europeu
            data[i][7] = incidencia.getEstat();
            data[i][8] = incidencia.getTecnicAssignat();
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
