package cat.enmarxa.incidentmanager;

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

/**
 * La classe FinestraCrearActiu és l'encarregada de crear la finestra (interfície gràfica) quan l'usuari selecciona l'opció de "Crear un nou actiu". Aquesta classe pertany a la part client de l'aplicació o 'Vista' dels del patró de disseny MVC.
 */
public class FinestraCrearActiu extends JDialog {
    private JTextField nomField;
    private JComboBox<String> marcaField;
    private JComboBox<String> tipusField;
    private JComboBox<String> areaField;
    private JTextField descripcioField;
    private JComboBox<String> altaField;
    private JButton crearButton;
    private ServeiActiu serveiActiu;
    

    public FinestraCrearActiu(Frame parent) {
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
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearActiu();
            }
        });
        add(crearButton, gbc);

        // Establecer el botón "Crear Actiu" como predeterminado para la tecla Enter
        getRootPane().setDefaultButton(crearButton);

        // Configuración de la ventana
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void crearActiu() {
        String nom = nomField.getText();
        String marca =  (String) marcaField.getSelectedItem();
        String tipus = (String) tipusField.getSelectedItem();
        String area = (String) areaField.getSelectedItem();
        String descripcio = descripcioField.getText();
        String dataAltaText = (String) altaField.getSelectedItem();
        

        if (nom.isEmpty() || marca.isEmpty() || tipus.isEmpty() || descripcio.isEmpty() || dataAltaText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tots els camps han d'estar omplerts.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAlta = null;
        
        try {
            dataAlta = dateFormat.parse(dataAltaText);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "El format de la data d'alta no és vàlid. Usa el format dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean creacioExitosa = serveiActiu.crearActiu(nom, tipus, area, marca, descripcio, dataAlta);

        if (creacioExitosa) {
            JOptionPane.showMessageDialog(this, "Actiu creat amb èxit.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear l'actiu.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
}
