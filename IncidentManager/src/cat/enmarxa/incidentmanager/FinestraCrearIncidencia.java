package cat.enmarxa.incidentmanager;

<<<<<<< HEAD
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.util.ArrayList; 
=======
// Importa les classes d'AWT per gestionar la disposició i components gràfics
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent; // Importa ActionEvent per gestionar esdeveniments d'acció
import java.awt.event.ActionListener; // Importa ActionListener per escoltar esdeveniments
import java.util.ArrayList; // Importa la classe ArrayList per gestionar col·leccions dinàmiques

// Importa les classes de Swing per crear interfícies gràfiques
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

/**
 * La classe FinestraCrearIncidencia és l'encarregada de crear la finestra (interfície gràfica) quan l'usuari selecciona l'opció de "Crear una nova incidència"  des de la FinestraPrincipal de l'aplicació. Aquesta classe pertany a la part client de l'aplicació o 'Vista' dels del patró de disseny MVC.
 */
public class FinestraCrearIncidencia extends JDialog {
<<<<<<< HEAD
    private JTextField descripcioField; 
    private JComboBox<String> tipusField; 
    private JComboBox<String> prioritatField; 
    private JComboBox<String> actiu1Field; 
    private JComboBox<String> actiu2Field; 
    private JButton crearButton; 
    private String usuari; 
    private ServeiIncidencia serveiIncidencia; 

=======
    private JTextField descripcioField; // Camp de text per a la descripció de la incidència
    private JComboBox<String> tipusField; // ComboBox per seleccionar el tipus d'incidència
    private JComboBox<String> prioritatField; // ComboBox per seleccionar la prioritat de la incidència
    private JButton crearButton; // Botó per crear la incidència
    private String usuari; // Variable per emmagatzemar l'usuari que crea la incidència
    private ServeiIncidencia serveiIncidencia; // Instància del servei d'incidències
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

    // Constructor de la finestra per crear una incidència
    public FinestraCrearIncidencia(Frame parent, String usuari) {
<<<<<<< HEAD
        super(parent, "Crear Incidència", true); 
        this.usuari = usuari; 

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 

=======
        super(parent, "Crear Incidència", true); // Configura la finestra com a modal
        this.usuari = usuari; // Assignar l'usuari al camp de classe
        setLayout(new GridLayout(5, 2)); // Establir el layout de la finestra en una graella de 5 files i 2 columnes

        // Inicialitzar el servei d'incidències
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        serveiIncidencia = new ServeiIncidencia();

<<<<<<< HEAD
        // Campo Descripció
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.5; 
        add(new JLabel("Descripció:"), gbc);
        
        gbc.gridx = 1;
        descripcioField = new JTextField(20); 
        add(descripcioField, gbc);
=======
        // Camps per a la descripció
        add(new JLabel("Descripció:")); // Etiqueta per al camp de descripció
        descripcioField = new JTextField(250); // Camp de text per a l'entrada de la descripció
        add(descripcioField); // Afegir el camp de descripció al diàleg

        // Camp per al tipus d'incidència
        add(new JLabel("Tipus:")); // Etiqueta per al camp de tipus
        tipusField = new JComboBox<>(new String[]{"Incidència", "Petició", "Problema", "Canvi"}); // ComboBox per seleccionar el tipus d'incidència
        add(tipusField); // Afegir el comboBox al diàleg

        // Camp per a la prioritat
        add(new JLabel("Prioritat:")); // Etiqueta per al camp de prioritat
        prioritatField = new JComboBox<>(new String[]{"Baixa", "Normal", "Alta", "Crítica", "Urgent"}); // ComboBox per seleccionar la prioritat
        add(prioritatField); // Afegir el comboBox al diàleg

        // Camp per al actiu relacionat
        add(new JLabel("Actiu relacionat:")); // Etiqueta per al camp d'actiu
        JComboBox<String> comboActius = new JComboBox<>(); // Crear un ComboBox per seleccionar actius
        ServeiActiu serveiActiu = new ServeiActiu(); // Inicialitzar el servei d'actius
        // Obtenir la llista d'actius i afegir-los al ComboBox
        ArrayList<String> llistaActius = (ArrayList<String>) serveiActiu.obtenirNomTotsElsActius();
        for (String actiu : llistaActius) {
            comboActius.addItem(actiu); // Afegir cada actiu a la llista del ComboBox
        }
        add(comboActius); // Afegir el ComboBox d'actius al diàleg
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

<<<<<<< HEAD
        // Campo Tipus
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Tipus:"), gbc);
        
        gbc.gridx = 1;
        tipusField = new JComboBox<>(new String[]{"Incidència", "Petició", "Problema", "Canvi"}); 
        add(tipusField, gbc);

        // Campo Prioritat
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Prioritat:"), gbc);
        
        gbc.gridx = 1;
        prioritatField = new JComboBox<>(new String[]{"Baixa", "Normal", "Alta", "Crítica", "Urgent"}); 
        add(prioritatField, gbc);

        // Campo Actiu1 relacionat
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Actiu relacionat #1:"), gbc);
        
        gbc.gridx = 1;
        actiu1Field = new JComboBox<>();
        ServeiActiu serveiActiu1 = new ServeiActiu();
        ArrayList<String> llistaActius1 = (ArrayList<String>) serveiActiu1.getLlistaActius();
        for (String actiu1 : llistaActius1) {
            actiu1Field.addItem(actiu1); 
        }
        add(actiu1Field, gbc);

        // Campo Actiu2 relacionat
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Actiu relacionat #2:"), gbc);
        
        gbc.gridx = 1;
        actiu2Field = new JComboBox<>();
        ServeiActiu serveiActiu2 = new ServeiActiu();
        ArrayList<String> llistaActius2 = (ArrayList<String>) serveiActiu2.getLlistaActius();
        for (String actiu2 : llistaActius2) {
            actiu2Field.addItem(actiu2); 
        }
        add(actiu2Field, gbc);

        // Botó Crear Incidència
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2; 
        crearButton = new JButton("Crear Incidència");
=======
        // Botó per crear la incidència
        crearButton = new JButton("Crear Incidència"); // Crear el botó de crear incidència
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearIncidencia(); // Executar el mètode crearIncidencia quan es prem el botó
            }
        });
<<<<<<< HEAD
        add(crearButton, gbc);
=======
        add(crearButton); // Afegir el botó al diàleg
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

<<<<<<< HEAD
        // Asigna el botón "Crear Incidència" como acción predeterminada cuando se presione Enter
        getRootPane().setDefaultButton(crearButton);

        // Configuración de la ventana
        setSize(400, 400); 
        setResizable(false); 
        setLocationRelativeTo(parent); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
=======
        // Configuració de la finestra
        setSize(400, 400); // Establir la mida de la finestra
        setResizable(false); // Desactivar la redimensionabilitat
        setLocationRelativeTo(parent); // Centrar la finestra respecte al pare
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Tancar la finestra quan es prem tancar
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
    }

    private void crearIncidencia() {
<<<<<<< HEAD
        String tipus = (String) tipusField.getSelectedItem(); 
        String descripcio = descripcioField.getText(); 
        String prioritat = (String) prioritatField.getSelectedItem(); 
        String usuari = this.usuari; 
        String actiu1 = (String) actiu1Field.getSelectedItem(); 
        String actiu2 = (String) actiu2Field.getSelectedItem();
=======
        String tipus = (String) tipusField.getSelectedItem(); // Obtenir el tipus seleccionat d'incidència
        String descripcio = descripcioField.getText(); // Obtenir la descripció de l'incidència
        String prioritat = (String) prioritatField.getSelectedItem(); // Obtenir la prioritat seleccionada
        String usuari = this.usuari; // Assignar l'usuari de la classe
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

        if (descripcio.isEmpty()) {
            // Mostrar un missatge d'error si la descripció està buida
            JOptionPane.showMessageDialog(this, "La descripció no pot estar buida.", "Error", JOptionPane.ERROR_MESSAGE);
<<<<<<< HEAD
            return; 
=======
            return; // Sortir del mètode si la descripció no és vàlida
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        }

<<<<<<< HEAD
        boolean creacioExitosa = serveiIncidencia.crearIncidencia(tipus, prioritat, "oberta", descripcio, usuari, actiu1, actiu2);
        
=======
        // Enviar la incidència al servei
        boolean creacioExitosa = serveiIncidencia.crearIncidencia(tipus, prioritat, descripcio, usuari);

>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        if (creacioExitosa) {
            // Mostrar un missatge d'èxit si l'incidència es crea correctament
            JOptionPane.showMessageDialog(this, "Incidència creada amb èxit.");
            dispose(); 
        } else {
            // Mostrar un missatge d'error si no es pot crear l'incidència
            JOptionPane.showMessageDialog(this, "Error al crear la incidència.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose(); 
        }
    }
}
