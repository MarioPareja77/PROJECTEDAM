package cat.enmarxa.incidentmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinestraCrearActiu extends JDialog {
    private JTextField nomField;
    private JTextField marcaField;
    private JComboBox<String> tipusField;
    private JComboBox<String> areaField;
    private JTextField descripcioField;
    private JTextField altaField;
    private JButton crearButton;
    private ServeiActiu serveiActiu;

    public FinestraCrearActiu(Frame parent) {
        super(parent, "Crear Actiu", true);
        // Inicialitzar serveis
        serveiActiu = new ServeiActiu();
        
        // Establir el layout vertical
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        // Panel per a cada fila
        JPanel panelNom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelMarca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelTipus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDescripcio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelDataAlta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBotons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // Camps per al nom
        panelNom.add(new JLabel("Nom:"));
        nomField = new JTextField(20);
        panelNom.add(nomField);
        add(panelNom);  // Afegir el panel al dialog
        
        // Camps per a la marca
        panelMarca.add(new JLabel("Marca:"));
        marcaField = new JTextField(20);
        panelMarca.add(marcaField);
        add(panelMarca);
        
        // Camp per tipus d'actiu
        panelTipus.add(new JLabel("Tipus:"));
        tipusField = new JComboBox<>(new String[]{"servidor", "PC", "pantalla", "portàtil", "switch", "router", "cablejat", "ratolí", "teclat", "rack", "software"});
        panelTipus.add(tipusField);
        add(panelTipus);
        
        // Camps per a l'àrea
        panelArea.add(new JLabel("Àrea:"));
        areaField = new JComboBox<>(new String[]{"RRHH", "Vendes", "Màrqueting", "IT", "Compres", "Producció", "Comptabilitat"});
        panelArea.add(areaField);
        add(panelArea);
        
        // Camps per la descripció
        panelDescripcio.add(new JLabel("Descripció:"));
        descripcioField = new JTextField(20);
        panelDescripcio.add(descripcioField);
        add(panelDescripcio);
        
        // Camps per a la data d'alta
        panelDataAlta.add(new JLabel("Data d'alta (dd/mm/yyyy):"));
        altaField = new JTextField(10);
        panelDataAlta.add(altaField);
        add(panelDataAlta);
        
        // Botó per crear l'actiu
        crearButton = new JButton("Crear Actiu");
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearActiu();
            }
        });
        panelBotons.add(crearButton);
        add(panelBotons);
        
        // Configuració de la finestra
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // Mètode per crear l'actiu
    private void crearActiu() {
        // Obtenir dades dels camps
        String nom = nomField.getText();
        String marca = marcaField.getText();
        String tipus = (String) tipusField.getSelectedItem();
        String area = (String) areaField.getSelectedItem();
        String descripcio = descripcioField.getText();
        String dataAltaText = altaField.getText();

        // Validar que els camps necessaris no estiguin buits
        if (nom.isEmpty() || marca.isEmpty() || descripcio.isEmpty() || dataAltaText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tots els camps han d'estar omplerts.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Convertir la data d'alta de String a Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAlta = null;
        
        try {
        	dataAlta = dateFormat.parse(dataAltaText);  // Intentar parsear la data
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "El format de la data d'alta no és vàlid. Usa el format dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Enviar l'actiu al servei (suponem que el mètode `crearActiu` existeix en el servei corresponent)
        if (serveiActiu.crearActiu(nom, tipus, area, marca, dataAlta, descripcio)) {
            JOptionPane.showMessageDialog(this, "Actiu creat amb èxit.");
            dispose(); // Tancar la finestra
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear l'actiu.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
