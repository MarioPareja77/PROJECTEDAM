package cat.enmarxa.incidentmanager;

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

public class FinestraCrearIncidencia extends JDialog {
    private JTextField descripcioField; // Camp de text per a la descripció de la incidència
    private JComboBox<String> tipusField; // ComboBox per seleccionar el tipus d'incidència
    private JComboBox<String> prioritatField; // ComboBox per seleccionar la prioritat de la incidència
    private JButton crearButton; // Botó per crear la incidència
    private String usuari; // Variable per emmagatzemar l'usuari que crea la incidència
    private ServeiIncidencia serveiIncidencia; // Instància del servei d'incidències

    // Constructor de la finestra per crear una incidència
    public FinestraCrearIncidencia(Frame parent, String usuari) {
        super(parent, "Crear Incidència", true); // Configura la finestra com a modal
        this.usuari = usuari; // Assignar l'usuari al camp de classe
        setLayout(new GridLayout(5, 2)); // Establir el layout de la finestra en una graella de 5 files i 2 columnes

        // Inicialitzar el servei d'incidències
        serveiIncidencia = new ServeiIncidencia();

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

        // Botó per crear la incidència
        crearButton = new JButton("Crear Incidència"); // Crear el botó de crear incidència
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearIncidencia(); // Executar el mètode crearIncidencia quan es prem el botó
            }
        });
        add(crearButton); // Afegir el botó al diàleg

        // Configuració de la finestra
        setSize(400, 400); // Establir la mida de la finestra
        setResizable(false); // Desactivar la redimensionabilitat
        setLocationRelativeTo(parent); // Centrar la finestra respecte al pare
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Tancar la finestra quan es prem tancar
    }

    // Mètode per crear la incidència
    private void crearIncidencia() {
        String tipus = (String) tipusField.getSelectedItem(); // Obtenir el tipus seleccionat d'incidència
        String descripcio = descripcioField.getText(); // Obtenir la descripció de l'incidència
        String prioritat = (String) prioritatField.getSelectedItem(); // Obtenir la prioritat seleccionada
        String usuari = this.usuari; // Assignar l'usuari de la classe

        // Validar que la descripció no estigui buida
        if (descripcio.isEmpty()) {
            // Mostrar un missatge d'error si la descripció està buida
            JOptionPane.showMessageDialog(this, "La descripció no pot estar buida.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Sortir del mètode si la descripció no és vàlida
        }

        // Enviar la incidència al servei
        boolean creacioExitosa = serveiIncidencia.crearIncidencia(tipus, prioritat, descripcio, usuari);

        if (creacioExitosa) {
            // Mostrar un missatge d'èxit si l'incidència es crea correctament
            JOptionPane.showMessageDialog(this, "Incidència creada amb èxit.");
            dispose(); // Tancar la finestra
        } else {
            // Mostrar un missatge d'error si no es pot crear l'incidència
            JOptionPane.showMessageDialog(this, "Error al crear la incidència.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose(); // Tancar la finestra
        }
    }
}
