package cat.enmarxa.incidentmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FinestraPrincipal extends JFrame {

    private JMenuBar barraMenu;
    private JMenu menuIncidencies, menuActius, menuUsuaris, menuLogout, menuAjuda;
    private ServeiIncidencia serveiIncidencia;
    private ServeiUsuari serveiUsuari;
    private ServeiActiu serveiActiu;

    // Constructor privat
    private FinestraPrincipal(String usuari, String rol, String idSessio) {
        setTitle("ENMARXA Incident Manager v1.0 (octubre 2024)");
        setSize(600, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la finestra

        // Inicialitzar serveis
        serveiIncidencia = new ServeiIncidencia();
        serveiUsuari = new ServeiUsuari();
        serveiActiu = new ServeiActiu();

        // Inicialització de la barra de menú
        barraMenu = new JMenuBar();

        // Menú Incidències
        menuIncidencies = new JMenu("Incidències");
        JMenuItem crearIncidencia = new JMenuItem("Crear Incidència");
        JMenuItem llistarIncidencies = new JMenuItem("Llistat de totes les Incidències");
        menuIncidencies.add(crearIncidencia);
        menuIncidencies.add(llistarIncidencies);

        // Accions del menú Incidències
        crearIncidencia.addActionListener(e -> {
            FinestraCrearIncidencia finestraCrearIncidencia = new FinestraCrearIncidencia(this);
            finestraCrearIncidencia.setVisible(true);
        });

        llistarIncidencies.addActionListener(e -> mostrarLlistatIncidencies());

        // Menú Actius (només si no és 'Usuari')
        if (!rol.equals("usuari")) {
            menuActius = new JMenu("Actius");
            JMenuItem crearActiu = new JMenuItem("Crear Actiu");
            JMenuItem llistarActius = new JMenuItem("Llistat de tots els Actius");
            menuActius.add(crearActiu);
            menuActius.add(llistarActius);

            // Accions del menú Actius
            crearActiu.addActionListener(e -> {
                FinestraCrearActiu finestraCrearActiu = new FinestraCrearActiu(this);
                finestraCrearActiu.setVisible(true);
            });
            llistarActius.addActionListener(e -> mostrarLlistatActius());
            barraMenu.add(menuActius);
        }

        // Menú Usuaris (només si és 'Gestor' o 'Administrador')
        if (rol.equals("gestor") || rol.equals("administrador")) {
            menuUsuaris = new JMenu("Usuaris");
            JMenuItem llistarUsuaris = new JMenuItem("Llistat de tots els Usuaris");
            menuUsuaris.add(llistarUsuaris);

            // Acció del menú Usuaris
            llistarUsuaris.addActionListener(e -> mostrarLlistatUsuaris());
            barraMenu.add(menuUsuaris); // Afegir el menú Usuaris si el rol és Gestor o Administrador
        }

        // Menú Ajuda
        menuAjuda = new JMenu("Ajuda");
        JMenuItem infoUsuari = new JMenuItem("Informació de l'Usuari");
        menuAjuda.add(infoUsuari);

        // Acció per mostrar informació de l'usuari
        infoUsuari.addActionListener(e -> mostrarInfoUsuari(usuari, rol, idSessio));

        // Menú Logout
        menuLogout = new JMenu("Logout");
        JMenuItem confirmarLogout = new JMenuItem("Tancar Sessió");
        menuLogout.add(confirmarLogout);

        // Acció per confirmar el logout
        confirmarLogout.addActionListener(e -> confirmarSortida());

        // Afegir els menús a la barra segons el rol
        barraMenu.add(menuIncidencies);
        barraMenu.add(menuAjuda);
        barraMenu.add(menuLogout);

        // Afegir la barra de menú a la finestra
        setJMenuBar(barraMenu);

        // Listener per la acció de tancar la finestra (X)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSortida();  // Preguntar si es desitja tancar la sessió
            }
        });
    }

    // Mètode per mostrar informació de l'usuari
    private void mostrarInfoUsuari(String usuari, String rol, String idSessio) {
        String info = "Nom d'Usuari: " + usuari + "\nRol: " + rol + "\nID de Sessió: " + idSessio;
        JOptionPane.showMessageDialog(this, info, "Informació de l'Usuari", JOptionPane.INFORMATION_MESSAGE);
    }

    // Mètode per mostrar el llistat d'incidències
    private void mostrarLlistatIncidencies() {
        // Usamos el servei per obtenir les incidències
        List<Incidencia> incidencies = serveiIncidencia.obtenirTotesLesIncidencies();

        // Verificar si s'han trobat incidències
        if (incidencies.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat incidències.", "Informació", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear un JTable per mostrar les incidències
        String[] columnNames = {"ID", "Descripció", "Tipus", "Prioritat", "Data de Creació"};
        Object[][] data = new Object[incidencies.size()][5];

        for (int i = 0; i < incidencies.size(); i++) {
            Incidencia incidencia = incidencies.get(i);
            data[i][0] = incidencia.getId(); // ID de la incidència
            data[i][1] = incidencia.getDescripcio(); // Descripció de la incidència
            data[i][2] = incidencia.getTipus(); // Tipus de la incidència
            data[i][3] = incidencia.getPrioritat(); // Prioritat de la incidència
            data[i][4] = incidencia.getDataCreacio(); // Data de creació
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 500)); // Ajustar la mida de la taula

        // Mostrar la taula en un quadre de diàleg
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Incidències", JOptionPane.INFORMATION_MESSAGE);
    }

    // Mètode per mostrar el llistat d'usuaris
    private void mostrarLlistatUsuaris() {
        // Usamos el servei per obtenir els usuaris
        List<Usuari> usuaris = serveiUsuari.obtenirTotsElsUsuaris();

        // Verificar si s'han trobat usuaris
        if (usuaris.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat usuaris.", "Informació", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear un JTable per mostrar els usuaris
        String[] columnNames = {"E-mail", "Contrasenya", "Àrea", "Cap", "Rol", "Intents Fallits"};
        Object[][] data = new Object[usuaris.size()][6]; // Modificat a 6 per incloure tots els camps

        for (int i = 0; i < usuaris.size(); i++) {
            Usuari usuari = usuaris.get(i);
            data[i][0] = usuari.getEmail();
            data[i][1] = usuari.getContrasenya();
            data[i][2] = usuari.getArea();
            data[i][3] = usuari.getCap();
            data[i][4] = usuari.getRol();
            data[i][5] = usuari.getIntentsFallits(); // Afegit per mostrar intents fallits
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 500)); // Ajustar el tamany de la taula

        // Mostrar la taula en un quadre de diàleg
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Usuaris", JOptionPane.INFORMATION_MESSAGE);
    }

    // Mètode per mostrar el llistat d'actius
    private void mostrarLlistatActius() {
        // Usamos el servei per obtenir els actius
        List<Actiu> actius = serveiActiu.obtenirTotsElsActius();

        // Verificar si s'han trobat actius
        if (actius.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat actius.", "Informació", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear un JTable per mostrar els actius
        String[] columnNames = {"Nom", "Tipus", "Àrea", "Marca", "Data Alta", "Descripció"};
        Object[][] data = new Object[actius.size()][6]; // Modificat a 6 per incloure tots els camps

        for (int i = 0; i < actius.size(); i++) {
            Actiu actiu = actius.get(i);
            data[i][0] = actiu.getNom();
            data[i][1] = actiu.getTipus(); 
            data[i][2] = actiu.getArea();
            data[i][3] = actiu.getMarca();
            data[i][4] = actiu.getDataAlta();
            data[i][5] = actiu.getDescripcio(); // Afegit per mostrar descripció
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 500)); // Ajustar el tamany de la taula

        // Mostrar la taula en un quadre de diàleg
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Actius", JOptionPane.INFORMATION_MESSAGE);
    }

    // Mètode per confirmar la sortida de l'aplicació
    private void confirmarSortida() {
        int resposta = JOptionPane.showConfirmDialog(this, "Estàs segur que vols tancar la sessió?", "Confirmar Logout", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            // Tanquem la connexió amb la base de dades i el servidor
            tancarConnexions();
            // Tanquem la finestra i sortim de l'aplicació
            this.dispose(); // Aquest mètode tanca la finestra actual
            System.exit(0);
        }
    }

    // Mètode per tancar la connexió amb la base de dades i el servidor
    private void tancarConnexions() {
        try {
            Servidor.TancarConnexioServidor(); // Tancar la connexió amb el servidor
            System.out.println("Connexió amb el servidor tancada.");
        } catch (Exception e) {
            e.printStackTrace(); // Maneig d'excepcions per assegurar que no es trenqui el programa
        }
    }

    // Mètode estàtic per crear una instància de FinestraPrincipal
    public static void iniciarFinestra(String usuari, String rol, String idSessio) {
        FinestraPrincipal finestra = new FinestraPrincipal(usuari, rol, idSessio);
        finestra.setVisible(true);
    }
}
