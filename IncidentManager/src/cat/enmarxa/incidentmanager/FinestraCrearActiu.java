package cat.enmarxa.incidentmanager;

// Importa les classes d'AWT per gestionar la disposició i components gràfics
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent; // Importa ActionEvent per gestionar esdeveniments d'acció
import java.awt.event.ActionListener; // Importa ActionListener per escoltar esdeveniments
import java.text.ParseException; // Importa ParseException per gestionar errors de format de data
import java.text.SimpleDateFormat; // Importa SimpleDateFormat per formatar dates
import java.util.Date; // Importa la classe Date per gestionar dates

// Importa les classes de Swing per crear interfícies gràfiques
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FinestraCrearActiu extends JDialog {
    private JTextField nomField; // Camp de text per al nom de l'actiu
    private JTextField marcaField; // Camp de text per a la marca de l'actiu
    private JComboBox<String> tipusField; // ComboBox per seleccionar el tipus d'actiu
    private JComboBox<String> areaField; // ComboBox per seleccionar l'àrea de l'actiu
    private JTextField descripcioField; // Camp de text per a la descripció de l'actiu
    private JTextField altaField; // Camp de text per a la data d'alta
    private JButton crearButton; // Botó per crear l'actiu
    private ServeiActiu serveiActiu; // Instància del servei d'actius

    // Constructor de la finestra per crear un actiu
    public FinestraCrearActiu(Frame parent) {
        super(parent, "Crear Actiu", true); // Configura la finestra com a modal
        serveiActiu = new ServeiActiu(); // Inicialitzar el servei d'actius

        // Establir el layout vertical
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Crear panells per a cada fila de la finestra
        JPanel panelNom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelMarca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelTipus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDescripcio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDataAlta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBotons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Camps per al nom
        panelNom.add(new JLabel("Nom:")); // Etiqueta per al camp de nom
        nomField = new JTextField(20); // Camp de text per a l'entrada del nom
        panelNom.add(nomField); // Afegir el camp de nom al panell
        add(panelNom); // Afegir el panell al diàleg

        // Camps per a la marca
        panelMarca.add(new JLabel("Marca:")); // Etiqueta per al camp de marca
        marcaField = new JTextField(20); // Camp de text per a l'entrada de la marca
        panelMarca.add(marcaField); // Afegir el camp de marca al panell
        add(panelMarca); // Afegir el panell al diàleg

        // Camp per tipus d'actiu
        panelTipus.add(new JLabel("Tipus:")); // Etiqueta per al camp de tipus
        tipusField = new JComboBox<>(new String[]{"servidor", "PC", "pantalla", "portàtil", "switch", "router", "cablejat", "ratolí", "teclat", "rack", "software"}); // ComboBox per seleccionar el tipus d'actiu
        panelTipus.add(tipusField); // Afegir el comboBox al panell
        add(panelTipus); // Afegir el panell al diàleg

        // Camps per a l'àrea
        panelArea.add(new JLabel("Àrea:")); // Etiqueta per al camp d'àrea
        areaField = new JComboBox<>(new String[]{"RRHH", "Vendes", "Màrqueting", "IT", "Compres", "Producció", "Comptabilitat"}); // ComboBox per seleccionar l'àrea
        panelArea.add(areaField); // Afegir el comboBox al panell
        add(panelArea); // Afegir el panell al diàleg

        // Camps per la descripció
        panelDescripcio.add(new JLabel("Descripció:")); // Etiqueta per al camp de descripció
        descripcioField = new JTextField(20); // Camp de text per a l'entrada de la descripció
        panelDescripcio.add(descripcioField); // Afegir el camp de descripció al panell
        add(panelDescripcio); // Afegir el panell al diàleg

        // Camps per a la data d'alta
        panelDataAlta.add(new JLabel("Data d'alta (dd/mm/yyyy):")); // Etiqueta per al camp de data d'alta
        altaField = new JTextField(10); // Camp de text per a l'entrada de la data d'alta
        panelDataAlta.add(altaField); // Afegir el camp de data d'alta al panell
        add(panelDataAlta); // Afegir el panell al diàleg

        // Botó per crear l'actiu
        crearButton = new JButton("Crear Actiu"); // Crear el botó de crear actiu
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearActiu(); // Executar el mètode crearActiu quan es prem el botó
            }
        });
        panelBotons.add(crearButton); // Afegir el botó al panell de botons
        add(panelBotons); // Afegir el panell de botons al diàleg

        // Configuració de la finestra
        setSize(400, 300); // Establir la mida de la finestra
        setResizable(false); // Desactivar la redimensionabilitat
        setLocationRelativeTo(parent); // Centrar la finestra respecte al pare
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Tancar la finestra quan es prem tancar
    }

    // Mètode per crear l'actiu
    private void crearActiu() {
        // Obtenir dades dels camps
        String nom = nomField.getText(); // Obtenir el nom de l'actiu
        String marca = marcaField.getText(); // Obtenir la marca de l'actiu
        String tipus = (String) tipusField.getSelectedItem(); // Obtenir el tipus seleccionat
        String area = (String) areaField.getSelectedItem(); // Obtenir l'àrea seleccionada
        String descripcio = descripcioField.getText(); // Obtenir la descripció de l'actiu
        String dataAltaText = altaField.getText(); // Obtenir la data d'alta com a text

        // Validar que els camps necessaris no estiguin buits
        if (nom.isEmpty() || marca.isEmpty() || descripcio.isEmpty() || dataAltaText.isEmpty()) {
            // Mostrar un missatge d'error si hi ha camps buits
            JOptionPane.showMessageDialog(this, "Tots els camps han d'estar omplerts.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Sortir del mètode si hi ha camps buits
        }

        // Convertir la data d'alta de String a Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Establir el format de data
        Date dataAlta = null; // Inicialitzar la data d'alta a null

        try {
            dataAlta = dateFormat.parse(dataAltaText); // Intentar parsear la data
        } catch (ParseException e) {
            // Mostrar un missatge d'error si el format de data no és vàlid
            JOptionPane.showMessageDialog(this, "El format de la data d'alta no és vàlid. Usa el format dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Sortir del mètode si el format de data és incorrecte
        }

        // Enviar l'actiu al servei (suponem que el mètode `crearActiu` existeix en el servei corresponent)
        if (serveiActiu.crearActiu(nom, tipus, area, marca, dataAlta, descripcio)) {
            // Mostrar un missatge d'èxit si l'actiu es crea correctament
            JOptionPane.showMessageDialog(this, "Actiu creat amb èxit.");
            dispose(); // Tancar la finestra
        } else {
            // Mostrar un missatge d'error si no es pot crear l'actiu
            JOptionPane.showMessageDialog(this, "Error al crear l'actiu.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
