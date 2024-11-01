package cat.enmarxa.incidentmanager;

<<<<<<< HEAD
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
=======
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
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

/**
 * La classe FinestraCrearActiu és l'encarregada de crear la finestra (interfície gràfica) quan l'usuari selecciona l'opció de "Crear un nou actiu". Aquesta classe pertany a la part client de l'aplicació o 'Vista' dels del patró de disseny MVC.
 */
public class FinestraCrearActiu extends JDialog {
<<<<<<< HEAD
    private JTextField nomField;
    private JComboBox<String> marcaField;
    private JComboBox<String> tipusField;
    private JComboBox<String> areaField;
    private JTextField descripcioField;
    private JComboBox<String> altaField;
    private JButton crearButton;
    private ServeiActiu serveiActiu;
    
=======
    private JTextField nomField; // Camp de text per al nom de l'actiu
    private JTextField marcaField; // Camp de text per a la marca de l'actiu
    private JComboBox<String> tipusField; // ComboBox per seleccionar el tipus d'actiu
    private JComboBox<String> areaField; // ComboBox per seleccionar l'àrea de l'actiu
    private JTextField descripcioField; // Camp de text per a la descripció de l'actiu
    private JTextField altaField; // Camp de text per a la data d'alta
    private JButton crearButton; // Botó per crear l'actiu
    private ServeiActiu serveiActiu; // Instància del servei d'actius
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

    // Constructor de la finestra per crear un actiu
    public FinestraCrearActiu(Frame parent) {
<<<<<<< HEAD
        super(parent, "Crear Actiu", true);
        serveiActiu = new ServeiActiu();

        // Establece el layout GridBagLayout para más control
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta y campo de texto para el nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nom:"), gbc);
        
        gbc.gridx = 1;
        nomField = new JTextField(20);
        add(nomField, gbc);

        // Etiqueta y campo de texto para la marca
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Marca:"), gbc);
        
        gbc.gridx = 1;
        marcaField = new JComboBox<>(new String[]{"Microsoft", "Red Hat", "Ubuntu", "Oracle", "Dell", "HP", "IBM", "Lenovo", "Logitech", "MSI", "Salesforce", "Odoo", "Samsung", "Cisco", "TP-Link", "D-Link", "Acer", "Altres"});
        add(marcaField, gbc);

        // Etiqueta y comboBox para el tipo de activo
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Tipus:"), gbc);
        
        gbc.gridx = 1;
        tipusField = new JComboBox<>(new String[]{"servidor", "PC", "pantalla", "portàtil", "switch", "router", "cablejat", "ratolí", "teclat", "rack", "software"});
        add(tipusField, gbc);

        // Etiqueta y comboBox para el área
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Àrea:"), gbc);
        
        gbc.gridx = 1;
        areaField = new JComboBox<>(new String[]{"RRHH", "Vendes", "Màrqueting", "IT", "Compres", "Producció", "Comptabilitat"});
        add(areaField, gbc);

        // Etiqueta y campo de texto para la descripción
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Descripció:"), gbc);
        
        gbc.gridx = 1;
        descripcioField = new JTextField(20);
        add(descripcioField, gbc);
        
        // Formato para mostrar las fechas en formato dd/MM/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Crear una llista de dates
        ArrayList<String> dates = new ArrayList<>();
        
        // Afegim dates al voltant de la data actual (per exemple, 30 dies abans i 30 dies després)
        LocalDate today = LocalDate.now();
        for (int i = -30; i <= 30; i++) {
            LocalDate date = today.plusDays(i);
            dates.add(date.format(formatter));
        }

        // Etiqueta y campo de texto para la fecha de alta
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Data d'alta (dd/mm/yyyy):"), gbc);
        
        gbc.gridx = 1;
        altaField = new JComboBox<>(dates.toArray(new String[0]));
        // Afegim la data d'avui com a predeterminada (pre-poblada)
        altaField.setSelectedItem(today.format(formatter));
        add(altaField, gbc);

        // Botón para crear el activo
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        crearButton = new JButton("Crear Actiu");
=======
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
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearActiu(); // Executar el mètode crearActiu quan es prem el botó
            }
        });
<<<<<<< HEAD
        add(crearButton, gbc);

        // Establecer el botón "Crear Actiu" como predeterminado para la tecla Enter
        getRootPane().setDefaultButton(crearButton);

        // Configuración de la ventana
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
=======
        panelBotons.add(crearButton); // Afegir el botó al panell de botons
        add(panelBotons); // Afegir el panell de botons al diàleg

        // Configuració de la finestra
        setSize(400, 300); // Establir la mida de la finestra
        setResizable(false); // Desactivar la redimensionabilitat
        setLocationRelativeTo(parent); // Centrar la finestra respecte al pare
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Tancar la finestra quan es prem tancar
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
    }

    private void crearActiu() {
<<<<<<< HEAD
        String nom = nomField.getText();
        String marca =  (String) marcaField.getSelectedItem();
        String tipus = (String) tipusField.getSelectedItem();
        String area = (String) areaField.getSelectedItem();
        String descripcio = descripcioField.getText();
        String dataAltaText = (String) altaField.getSelectedItem();
        
=======
        // Obtenir dades dels camps
        String nom = nomField.getText(); // Obtenir el nom de l'actiu
        String marca = marcaField.getText(); // Obtenir la marca de l'actiu
        String tipus = (String) tipusField.getSelectedItem(); // Obtenir el tipus seleccionat
        String area = (String) areaField.getSelectedItem(); // Obtenir l'àrea seleccionada
        String descripcio = descripcioField.getText(); // Obtenir la descripció de l'actiu
        String dataAltaText = altaField.getText(); // Obtenir la data d'alta com a text
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM

<<<<<<< HEAD
        if (nom.isEmpty() || marca.isEmpty() || tipus.isEmpty() || descripcio.isEmpty() || dataAltaText.isEmpty()) {
=======
        // Validar que els camps necessaris no estiguin buits
        if (nom.isEmpty() || marca.isEmpty() || descripcio.isEmpty() || dataAltaText.isEmpty()) {
            // Mostrar un missatge d'error si hi ha camps buits
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
            JOptionPane.showMessageDialog(this, "Tots els camps han d'estar omplerts.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Sortir del mètode si hi ha camps buits
        }

<<<<<<< HEAD
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAlta = null;
        
=======
        // Convertir la data d'alta de String a Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Establir el format de data
        Date dataAlta = null; // Inicialitzar la data d'alta a null

>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        try {
<<<<<<< HEAD
            dataAlta = dateFormat.parse(dataAltaText);
=======
            dataAlta = dateFormat.parse(dataAltaText); // Intentar parsear la data
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
        } catch (ParseException e) {
            // Mostrar un missatge d'error si el format de data no és vàlid
            JOptionPane.showMessageDialog(this, "El format de la data d'alta no és vàlid. Usa el format dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Sortir del mètode si el format de data és incorrecte
        }

<<<<<<< HEAD
        boolean creacioExitosa = serveiActiu.crearActiu(nom, tipus, area, marca, descripcio, dataAlta);

        if (creacioExitosa) {
=======
        // Enviar l'actiu al servei (suponem que el mètode `crearActiu` existeix en el servei corresponent)
        if (serveiActiu.crearActiu(nom, tipus, area, marca, dataAlta, descripcio)) {
            // Mostrar un missatge d'èxit si l'actiu es crea correctament
>>>>>>> branch 'main' of https://github.com/MarioPareja77/PROJECTEDAM
            JOptionPane.showMessageDialog(this, "Actiu creat amb èxit.");
            dispose();
        } else {
            // Mostrar un missatge d'error si no es pot crear l'actiu
            JOptionPane.showMessageDialog(this, "Error al crear l'actiu.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
}
