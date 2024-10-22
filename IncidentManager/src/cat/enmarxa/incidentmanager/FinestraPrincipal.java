package cat.enmarxa.incidentmanager;

// Importa classes d'AWT per a la gestió de la disposició
import java.awt.Dimension;
// Importa classes per gestionar esdeveniments
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap; // Importa HashMap per a la gestió de mapes clau-valor
import java.util.List; // Importa la interfície List per gestionar llistes
import java.util.Map; // Importa la interfície Map per a mapes

// Importa les classes de Swing per a la interfície gràfica
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel; // Importa la classe per gestionar models de taula

public class FinestraPrincipal extends JFrame {

    // Declarem els components de la interfície
    private JMenuBar barraMenu; // Barra de menú principal
    private JMenu menuIncidencies, menuActius, menuUsuaris, menuSessions, menuLogout, menuAjuda; // Menús
    private ServeiIncidencia serveiIncidencia; // Servei per gestionar incidències
    private ServeiUsuari serveiUsuari; // Servei per gestionar usuaris
    private ServeiActiu serveiActiu; // Servei per gestionar actius
    private String usuari; // Nom d'usuari
    private Servidor servidor; // Instància del servidor

    // Mapa per emmagatzemar sessions actives
    private static Map<String, String> sessionsActives = new HashMap<>();

    // Constructor que inicialitza el servidor
    public FinestraPrincipal(Servidor servidor) {
        this.servidor = servidor; // Inicialitza el servidor
    }

    // Constructor privat per inicialitzar la finestra principal
    private FinestraPrincipal(String usuari, String rol, String idSessio) {
        setTitle("ENMARXA Incident Manager v1.0 (octubre 2024)"); // Títol de la finestra
        setSize(600, 400); // Mida de la finestra
        setResizable(false); // No permetre redimensionament
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // No tancar al fer clic a la X
        setLocationRelativeTo(null); // Centrar la finestra

        this.usuari = usuari; // Guardar el nom d'usuari

        // Inicialització dels serveis
        serveiIncidencia = new ServeiIncidencia(); // Inicialitzar servei d'incidència
        serveiUsuari = new ServeiUsuari(); // Inicialitzar servei d'usuari
        serveiActiu = new ServeiActiu(); // Inicialitzar servei d'actiu
        servidor = new Servidor(); // Inicialitzar servidor

        // Inicialització de la barra de menú
        barraMenu = new JMenuBar();

        // Menú Incidències
        menuIncidencies = new JMenu("Incidències"); // Crear menú d'incidències
        JMenuItem crearIncidencia = new JMenuItem("Crear Incidència"); // Opció per crear una incidència
        JMenuItem llistarIncidencies = new JMenuItem("Llistat de totes les Incidències"); // Opció per llistar incidències
        menuIncidencies.add(crearIncidencia); // Afegir opció al menú
        menuIncidencies.add(llistarIncidencies); // Afegir opció al menú

        // Accions del menú Incidències
        crearIncidencia.addActionListener(e -> {
            // Obrir finestra per crear una nova incidència
            FinestraCrearIncidencia finestraCrearIncidencia = new FinestraCrearIncidencia(this, usuari);
            finestraCrearIncidencia.setVisible(true);
        });

        // Acció per mostrar el llistat d'incidències
        llistarIncidencies.addActionListener(e -> mostrarLlistatIncidencies());

        // Menú Actius (només si no és 'Usuari')
        if (!rol.equals("usuari")) {
            menuActius = new JMenu("Actius"); // Crear menú d'actius
            JMenuItem crearActiu = new JMenuItem("Crear Actiu"); // Opció per crear un actiu
            JMenuItem llistarActius = new JMenuItem("Llistat de tots els Actius"); // Opció per llistar actius
            menuActius.add(crearActiu); // Afegir opció al menú
            menuActius.add(llistarActius); // Afegir opció al menú

            // Accions del menú Actius
            crearActiu.addActionListener(e -> {
                // Obrir finestra per crear un nou actiu
                FinestraCrearActiu finestraCrearActiu = new FinestraCrearActiu(this);
                finestraCrearActiu.setVisible(true);
            });
            llistarActius.addActionListener(e -> mostrarLlistatActius()); // Acció per mostrar el llistat d'actius
            barraMenu.add(menuActius); // Afegir menú d'actius a la barra
        }

        // Menú Usuaris (només si és 'Gestor' o 'Administrador')
        if (rol.equals("gestor") || rol.equals("administrador")) {
            menuUsuaris = new JMenu("Usuaris"); // Crear menú d'usuaris
            JMenuItem llistarUsuaris = new JMenuItem("Llistat de tots els Usuaris"); // Opció per llistar usuaris
            menuUsuaris.add(llistarUsuaris); // Afegir opció al menú

            // Acció del menú Usuaris
            llistarUsuaris.addActionListener(e -> mostrarLlistatUsuaris()); // Acció per mostrar el llistat d'usuaris
            barraMenu.add(menuUsuaris); // Afegir menú d'usuaris a la barra
        }

        // Menú Ajuda
        menuAjuda = new JMenu("Ajuda"); // Crear menú d'ajuda
        JMenuItem infoUsuari = new JMenuItem("Informació de l'Usuari"); // Opció per mostrar informació de l'usuari
        menuAjuda.add(infoUsuari); // Afegir opció al menú

        // Acció per mostrar informació de l'usuari
        infoUsuari.addActionListener(e -> mostrarInfoUsuari(usuari, rol, idSessio));

        // Menú Logout
        menuLogout = new JMenu("Logout"); // Crear menú de logout
        JMenuItem confirmarLogout = new JMenuItem("Tancar Sessió"); // Opció per tancar sessió
        menuLogout.add(confirmarLogout); // Afegir opció al menú

        // Acció per confirmar el logout
        confirmarLogout.addActionListener(e -> confirmarSortida());

        // Afegir els menús a la barra segons el rol
        barraMenu.add(menuIncidencies); // Afegir menú d'incidències a la barra

        // Menú Sessions (només si és 'Administrador')
        if (rol.equals("administrador")) {
            menuSessions = new JMenu("Sessions"); // Crear menú de sessions
            JMenuItem llistarSessions = new JMenuItem("Llistat de totes les sessions obertes"); // Opció per llistar sessions
            menuSessions.add(llistarSessions); // Afegir opció al menú

            // Acció del menú Sessions
            llistarSessions.addActionListener(e -> mostrarLlistatSessions()); // Acció per mostrar el llistat de sessions
            barraMenu.add(menuSessions); // Afegir menú de sessions a la barra
        }
        barraMenu.add(menuAjuda); // Afegir menú d'ajuda a la barra
        barraMenu.add(menuLogout); // Afegir menú de logout a la barra

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
        String info = "Nom d'Usuari: " + usuari + "\nRol: " + rol + "\nID de Sessió: " + idSessio; // Crear cadena d'informació
        JOptionPane.showMessageDialog(this, info, "Informació de l'Usuari", JOptionPane.INFORMATION_MESSAGE); // Mostrar diàleg d'informació
    }

    // Mètode per mostrar el llistat d'incidències
    private void mostrarLlistatIncidencies() {
        // Usamos el servei per obtenir les incidències
        List<Incidencia> incidencies = serveiIncidencia.obtenirTotesLesIncidencies(); // Obtenir totes les incidències

        // Verificar si s'han trobat incidències
        if (incidencies.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat incidències.", "Informació", JOptionPane.INFORMATION_MESSAGE); // Mostrar missatge si no hi ha incidències
            return; // Sortir del mètode
        }

        // Crear un JTable per mostrar les incidències
        String[] columnNames = {"ID", "Descripció", "Tipus", "Prioritat", "Data de Creació"}; // Noms de les columnes
        Object[][] data = new Object[incidencies.size()][5]; // Matriu per emmagatzemar les dades

        // Iterar a través de les incidències per omplir la matriu de dades
        for (int i = 0; i < incidencies.size(); i++) {
            Incidencia incidencia = incidencies.get(i);
            data[i][0] = incidencia.getId(); // ID de la incidència
            data[i][1] = incidencia.getDescripcio(); // Descripció de la incidència
            data[i][2] = incidencia.getTipus(); // Tipus de la incidència
            data[i][3] = incidencia.getPrioritat(); // Prioritat de la incidència
            data[i][4] = incidencia.getDataCreacio(); // Data de creació
        }

        // Crear taula i mostrar dades
        JTable table = new JTable(data, columnNames); // Crear taula amb dades
        JScrollPane scrollPane = new JScrollPane(table); // Crear un JScrollPane per la taula
        scrollPane.setPreferredSize(new Dimension(800, 500)); // Ajustar la mida de la taula

        // Mostrar la taula en un quadre de diàleg
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Incidències", JOptionPane.INFORMATION_MESSAGE);
    }

    // Mètode per mostrar les sessions actives
    public void mostrarSessions() {
        System.out.println(servidor.obtenirSessionsActives()); // Imprimir les sessions actives
    }

    // Mètode per mostrar el llistat d'usuaris
    private void mostrarLlistatUsuaris() {
        // Usamos el servei per obtenir els usuaris
        List<Usuari> usuaris = serveiUsuari.obtenirTotsElsUsuaris(); // Obtenir tots els usuaris

        // Verificar si s'han trobat usuaris
        if (usuaris.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat usuaris.", "Informació", JOptionPane.INFORMATION_MESSAGE); // Mostrar missatge si no hi ha usuaris
            return; // Sortir del mètode
        }

        // Crear un JTable per mostrar els usuaris
        String[] columnNames = {"E-mail", "Contrasenya", "Àrea", "Cap", "Rol"}; // Noms de les columnes
        Object[][] data = new Object[usuaris.size()][5]; // Matriu per emmagatzemar les dades

        // Iterar a través dels usuaris per omplir la matriu de dades
        for (int i = 0; i < usuaris.size(); i++) {
            Usuari usuari = usuaris.get(i);
            data[i][0] = usuari.getEmail(); // E-mail de l'usuari
            data[i][1] = usuari.getContrasenya(); // Contrasenya de l'usuari
            data[i][2] = usuari.getArea(); // Àrea de l'usuari
            data[i][3] = usuari.getCap(); // Cap de l'usuari
            data[i][4] = usuari.getRol(); // Rol de l'usuari
        }

        // Crear taula i mostrar dades
        JTable table = new JTable(data, columnNames); // Crear taula amb dades
        JScrollPane scrollPane = new JScrollPane(table); // Crear un JScrollPane per la taula
        scrollPane.setPreferredSize(new Dimension(800, 500)); // Ajustar la mida de la taula

        // Mostrar la taula en un quadre de diàleg
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Usuaris", JOptionPane.INFORMATION_MESSAGE);
    }

    // Mètode per mostrar el llistat de sessions
    private void mostrarLlistatSessions() {
        // Crear el model de la taula
        DefaultTableModel modelTaula = new DefaultTableModel();
        modelTaula.addColumn("ID de sessió"); // Afegir columna per ID de sessió
        modelTaula.addColumn("Usuari (e-mail)"); // Afegir columna per e-mail de l'usuari

        // Crear la taula amb el model
        JTable taulaSessions = new JTable(modelTaula);
        JScrollPane scrollPane = new JScrollPane(taulaSessions); // Crear un JScrollPane per la taula

        // Afegir les sessions actives a la taula
        for (Map.Entry<String, String> entrada : sessionsActives.entrySet()) {
            String idSessio = entrada.getKey(); // ID de sessió
            String email = entrada.getValue(); // E-mail de l'usuari
            modelTaula.addRow(new Object[]{idSessio, email}); // Afegir fila amb la sessió activa
        }

        // Mostrar la taula en un quadre de diàleg
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat de sessions", JOptionPane.INFORMATION_MESSAGE);
    }

    // Mètode que retorna l'usuari actual
    public String getUsuari() {
        return usuari; // Retornar el nom d'usuari
    }

    // Mètode per mostrar el llistat d'actius
    private void mostrarLlistatActius() {
        // Usamos el servei per obtenir els actius
        List<Actiu> actius = serveiActiu.obtenirTotsElsActius(); // Obtenir tots els actius

        // Verificar si s'han trobat actius
        if (actius.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No s'han trobat actius.", "Informació", JOptionPane.INFORMATION_MESSAGE); // Mostrar missatge si no hi ha actius
            return; // Sortir del mètode
        }

        // Crear un JTable per mostrar els actius
        String[] columnNames = {"Nom", "Tipus", "Àrea", "Marca", "Data Alta", "Descripció"}; // Noms de les columnes
        Object[][] data = new Object[actius.size()][6]; // Matriu per emmagatzemar les dades

        // Iterar a través dels actius per omplir la matriu de dades
        for (int i = 0; i < actius.size(); i++) {
            Actiu actiu = actius.get(i);
            data[i][0] = actiu.getNom(); // Nom de l'actiu
            data[i][1] = actiu.getTipus(); // Tipus de l'actiu
            data[i][2] = actiu.getArea(); // Àrea de l'actiu
            data[i][3] = actiu.getMarca(); // Marca de l'actiu
            data[i][4] = actiu.getDataAlta(); // Data d'alta de l'actiu
            data[i][5] = actiu.getDescripcio(); // Descripció de l'actiu
        }

        // Crear taula i mostrar dades
        JTable table = new JTable(data, columnNames); // Crear taula amb dades
        JScrollPane scrollPane = new JScrollPane(table); // Crear un JScrollPane per la taula
        scrollPane.setPreferredSize(new Dimension(1000, 500)); // Ajustar la mida de la taula

        // Mostrar la taula en un quadre de diàleg
        JOptionPane.showMessageDialog(this, scrollPane, "Llistat d'Actius", JOptionPane.INFORMATION_MESSAGE);
    }

    // Mètode per confirmar la sortida de l'aplicació
    private void confirmarSortida() {
        // Preguntar confirmació per tancar la sessió
        int resposta = JOptionPane.showConfirmDialog(this, "Estàs segur que vols tancar la sessió?", "Confirmar Logout", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            // Tanquem la connexió amb la base de dades i el servidor
            tancarConnexions();
            // Tanquem la finestra i sortim de l'aplicació
            this.dispose(); // Aquest mètode tanca la finestra actual
            System.exit(0); // Tancar l'aplicació
        }
    }

    // Mètode per tancar la connexió amb la base de dades i el servidor
    private void tancarConnexions() {
        try {
            Servidor.TancarConnexioServidor(); // Tancar la connexió amb el servidor
            System.out.println("Connexió amb el servidor tancada."); // Informar que la connexió ha estat tancada
        } catch (Exception e) {
            e.printStackTrace(); // Maneig d'excepcions per assegurar que no es trenqui el programa
        }
    }

    // Mètode estàtic per crear una instància de FinestraPrincipal
    public static void iniciarFinestra(String usuari, String rol, String idSessio) {
        FinestraPrincipal finestra = new FinestraPrincipal(usuari, rol, idSessio); // Crear nova instància de la finestra
        finestra.setVisible(true); // Fer visible la finestra
    }
}
