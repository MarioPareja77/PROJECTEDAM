package cat.enmarxa.incidentmanager;

import javax.swing.*; // Importa les classes de Swing per a la interfície gràfica
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel; // Importa la classe per Modificar models de taula
import java.awt.*; // Importa classes d'AWT per a la gestió de la disposició
import java.awt.event.*; // Importa classes per Modificar esdeveniments
import java.net.Socket; // Importa la classe Socket per Modificar connexions de xarxa
import java.util.ArrayList; // Importa ArrayList per Modificar col·leccions
import java.util.HashMap; // Importa HashMap per a la gestió de mapes clau-valor
import java.util.List; // Importa la interfície List per Modificar llistes
import java.util.Map; // Importa la interfície Map per a mapes
import java.util.Locale;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.net.URI;
import java.net.URISyntaxException;

public class FinestraPrincipal extends JFrame {

    // Declarem els components de la interfície
    private JMenuBar barraMenu; // Barra de menú principal
    private JMenu menuIncidencies, menuActius, menuUsuaris, menuSessions, menuLogout, menuAjuda, menuContrasenya; // Menús
    private ServeiIncidencia serveiIncidencia; // Servei per Modificar incidències
    private ServeiUsuari serveiUsuari; // Servei per Modificar usuaris
    private ServeiActiu serveiActiu; // Servei per Modificar actius
    private String usuari; // Nom d'usuari
    private Servidor servidor; // Instància del servidor
    private Image fonsPantalla;
    private PanelFons panelFons;
   
    
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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // No tancar al fer clic a la X
        setLocationRelativeTo(null); // Centrar la finestra
        
        Locale.setDefault(Locale.of("ca", "ES"));
        
        this.usuari = usuari; // Guardar el nom d'usuari
        
        // Crear el panel de fondo
        panelFons = new PanelFons(fonsPantalla);
        setContentPane(panelFons); // Añadir el panel como contenido de la ventana
        
        // Carregar imatge de internet
        carregarFons();
        
        // Inicialització dels serveis
        serveiIncidencia = new ServeiIncidencia(); // Inicialitzar servei d'incidència
        serveiUsuari = new ServeiUsuari(); // Inicialitzar servei d'usuari
        serveiActiu = new ServeiActiu(); // Inicialitzar servei d'actiu
        servidor = new Servidor(); // Inicialitzar servidor
        
        panelFons = new PanelFons(fonsPantalla);

        // Inicialització de la barra de menú
        barraMenu = new JMenuBar();

        // Menú Incidències
        menuIncidencies = new JMenu("Incidències"); // Crear menú d'incidències
        JMenuItem crearIncidencia = new JMenuItem("Crear incidència"); // Opció per crear una incidència
        JMenuItem modificarIncidencia = new JMenuItem("Modificar incidència existent"); // Nova opció per Modificar una incidència existent
        JMenuItem llistarIncidencies = new JMenuItem("Llistat de totes les incidències"); // Opció per llistar incidències
        JMenuItem llistarIncidenciesUsuari = new JMenuItem("Llistar incidències per usuari"); // Nova opció per llistar incidències per criteri
        JMenuItem llistarIncidenciesEstat = new JMenuItem("Llistar incidències per estat"); // Nova opció per llistar incidències per criteri
        JMenuItem llistarIncidenciesPrioritat = new JMenuItem("Llistar incidències per prioritat"); // Nova opció per llistar incidències per criteri
        JMenuItem llistarIncidenciesTipus = new JMenuItem("Llistar incidències per tipus"); // Nova opció per llistar incidències per criteri
        JMenuItem eliminarIncidencia = new JMenuItem("Eliminar incidència"); // Nova opció per eliminar incidències
        menuIncidencies.add(crearIncidencia); 
        menuIncidencies.add(modificarIncidencia);
        menuIncidencies.add(llistarIncidencies); 
        menuIncidencies.add(llistarIncidenciesUsuari);
        menuIncidencies.add(llistarIncidenciesEstat); 
        menuIncidencies.add(llistarIncidenciesPrioritat); 
        menuIncidencies.add(llistarIncidenciesTipus); 
        menuIncidencies.add(eliminarIncidencia); 

        // Acció per crear incidència
        crearIncidencia.addActionListener(e -> {
        crearIncidencia(usuari);
        carregarFons();
        });

        // Acció per mostrar el llistat d'incidències
        llistarIncidencies.addActionListener(e -> {
        mostrarLlistatIncidencies();
        carregarFons();
        });

        // Acció per modificar incidència existent
        modificarIncidencia.addActionListener(e -> { 
        modificarIncidencia();
        carregarFons();
        });

        // Acció per llistar incidències per usuari
        llistarIncidenciesUsuari.addActionListener(e -> { 
        llistarIncidenciesUsuari();
        carregarFons();
        });
        
        // Acció per llistar incidències per estat
        llistarIncidenciesEstat.addActionListener(e -> { 
        llistarIncidenciesEstat();
        carregarFons();
        });
        
        // Acció per llistar incidències per prioritat
        llistarIncidenciesPrioritat.addActionListener(e -> { 
        llistarIncidenciesPrioritat();
        carregarFons();
        });
        
        // Acció per llistar incidències per tipus
        llistarIncidenciesTipus.addActionListener(e -> {
        llistarIncidenciesTipus();
        carregarFons();
        });
        
        // Acció per eliminar incidència
        eliminarIncidencia.addActionListener(e -> {
        eliminarIncidencia();
        carregarFons();
        });

        // Menú Actius (només si no és 'Usuari')
        if (!rol.equals("Usuari")) {
        	menuActius = new JMenu("Actius"); // Crear menú d'actius
            JMenuItem crearActiu = new JMenuItem("Crear actiu"); // Opció per crear un actiu
            JMenuItem modificarActiu = new JMenuItem("Modificar actiu existent"); // Nova opció per modificar un actiu existent
            JMenuItem llistarActius = new JMenuItem("Llistat de tots els actius"); // Opció per llistar actius
            JMenuItem llistarActiusArea = new JMenuItem("Llistar actius per àrea"); // Nova opció per llistar actius per criteri
            JMenuItem llistarActiusMarca = new JMenuItem("Llistar actius per marca"); // Nova opció per llistar actius per criteri
            JMenuItem llistarActiusTipus = new JMenuItem("Llistar actius per tipus"); // Nova opció per llistar actius per criteri
            JMenuItem eliminarActiu = new JMenuItem("Eliminar actiu");// Opció per eliminar un actiu
            menuActius.add(crearActiu); 
            menuActius.add(modificarActiu); 
            menuActius.add(llistarActius);
            menuActius.add(llistarActiusArea); 
            menuActius.add(llistarActiusMarca);
            menuActius.add(llistarActiusTipus); 
            menuActius.add(eliminarActiu); 

            // Acció per crear actiu
            crearActiu.addActionListener(e -> {
            crearActiu();
            carregarFons();
            });
           
            // Acció per llistar actiu
            llistarActius.addActionListener(e -> {
            llistarActius();
            carregarFons();
            });
            
            // Acció per llistar actius per àrea
            llistarActiusArea.addActionListener(e -> {
            llistarActiusArea();
            carregarFons();
            });
            
            // Acció per llistar actius per marca
            llistarActiusMarca.addActionListener(e -> {
            llistarActiusMarca();
            carregarFons();
            });
            
            // Acció per llistar actius per tipus
            llistarActiusTipus.addActionListener(e -> {
            llistarActiusTipus();
            carregarFons();
            });
            
            // Acció per modificar actiu
            modificarActiu.addActionListener(e -> {
            modificarActiu();
            carregarFons();
            });
            
            // Acció per eliminar actiu
            eliminarActiu.addActionListener(e -> { 
            eliminarActiu();
            carregarFons();
            });
            
            
            barraMenu.add(menuActius); // Afegir menú d'actius a la barra
        }
        
        // Menú Usuaris (només si és 'Gestor' o 'Administrador')
        if (rol.equals("Gestor") || rol.equals("Administrador")) {
            menuUsuaris = new JMenu("Usuaris"); // Crear menú d'usuaris
            JMenuItem crearUsuari = new JMenuItem("Crear usuari"); // Nova opció per crear usuari
            JMenuItem modificarUsuari = new JMenuItem("Modificar usuari existent"); // Nova opció per modificar un usuari existent
            JMenuItem llistarUsuaris = new JMenuItem("Llistat de tots els usuaris"); // Opció per llistar usuaris
            JMenuItem llistarUsuarisArea = new JMenuItem("Llistar usuaris per àrea"); // Nova opció per llistar usuaris per criteri
            JMenuItem llistarUsuarisRol = new JMenuItem("Llistar usuaris per rol"); // Nova opció per llistar usuaris per criteri
            JMenuItem eliminarUsuari = new JMenuItem("Eliminar usuari"); // Nova opció per eliminar un usuari
            menuUsuaris.add(crearUsuari); // Afegir nova opció al menú
            menuUsuaris.add(modificarUsuari); 
            menuUsuaris.add(llistarUsuaris); 
            menuUsuaris.add(llistarUsuarisArea); 
            menuUsuaris.add(llistarUsuarisRol); 
            menuUsuaris.add(eliminarUsuari); 

            // Acció per crear usuari
            crearUsuari.addActionListener(e -> crearUsuari());
            
            // Acció per modificar usuari
            modificarUsuari.addActionListener(e -> { 
            modificarUsuari(); // Acció per Modificar usuaris existents
            carregarFons();
            });
            
            llistarUsuaris.addActionListener(e ->  {
            mostrarLlistatUsuaris(); // Acció per mostrar el llistat d'usuaris
            carregarFons();
            });
            
            // Acció per llistar usuaris per àrea
            llistarUsuarisArea.addActionListener(e -> {
            llistarUsuarisArea();
            carregarFons();
            });
            
            // Acció per llistar usuaris per rol
            llistarUsuarisRol.addActionListener(e -> {
            llistarUsuarisRol();
            carregarFons();
            });
            
            // Acció per eliminar un usuari
            eliminarUsuari.addActionListener(e -> {
            eliminarUsuari();
            carregarFons();
            });
            
            barraMenu.add(menuUsuaris); // Afegir menú d'usuaris a la barra
        }
        
        
        // Menú Ajuda
        menuAjuda = new JMenu("Ajuda"); // Crear menú d'ajuda
        JMenuItem infoUsuari = new JMenuItem("Informació de l'Usuari"); // Opció per mostrar informació de l'usuari
        menuAjuda.add(infoUsuari); // Afegir opció al menú

        // Acció per mostrar informació de l'usuari
        infoUsuari.addActionListener(e -> mostrarInfoUsuari(usuari, rol, idSessio));

        // Menú canvi de contrasenya
        menuContrasenya = new JMenu("Contrasenya");
        JMenuItem confirmarCanviContrasenya = new JMenuItem("Canvi de contrasenya");
        menuContrasenya.add(confirmarCanviContrasenya); // Afegir opció al menú

        // Menú Logout
        menuLogout = new JMenu("Logout"); // Crear menú de logout
        JMenuItem confirmarLogout = new JMenuItem("Tancar Sessió"); // Opció per tancar sessió
        menuLogout.add(confirmarLogout); // Afegir opció al menú
        
        // Acció per confirmar el canvi de contrasenya
        confirmarCanviContrasenya.addActionListener(e -> {
        resetContrasenya();
        carregarFons();
        });
        
        // Acció per confirmar el logout
        confirmarLogout.addActionListener(e -> {
        confirmarSortida();
        carregarFons();
        });
        

        // Afegir els menús a la barra segons el rol
        barraMenu.add(menuIncidencies); // Afegir menú d'incidències a la barra
        
        // Menú Sessions (només si és 'Administrador')
        if (rol.equals("administrador")) {
            menuSessions = new JMenu("Sessions"); // Crear menú de sessions
            JMenuItem llistarSessions = new JMenuItem("Llistat de totes les sessions obertes"); // Opció per llistar sessions
            menuSessions.add(llistarSessions); // Afegir opció al menú

            // Acció del menú Sessions
            // llistarSessions.addActionListener(e -> mostrarLlistatSessions()); // Acció per mostrar el llistat de sessions
            // barraMenu.add(menuSessions); // Afegir menú de sessions a la barra
        }
        barraMenu.add(menuAjuda); // Afegir menú d'ajuda a la barra
        barraMenu.add(menuContrasenya); // Afegir menú d'ajuda a la barra
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
        String[] columnNames = {"ID", "Tipus", "Prioritat", "Estat", "Actiu #1", "Actiu #2", "Descripció", "Data de creació", "Usuari creador", "Tècnic assignat"}; // Noms de les columnes
        Object[][] data = new Object[incidencies.size()][10]; // Matriu per emmagatzemar les dades

        // Iterar a través de les incidències per omplir la matriu de dades
        for (int i = 0; i < incidencies.size(); i++) {
            Incidencia incidencia = incidencies.get(i);
            data[i][0] = incidencia.getId(); // ID de la incidència
            data[i][1] = incidencia.getTipus(); // Tipus d'incidència
            data[i][2] = incidencia.getPrioritat(); // Estat de la incidència
            data[i][3] = incidencia.getEstat(); // Prioritat de la incidència
            data[i][4] = incidencia.getActiu1(); // Actiu1 relacionat amb la incidència
            data[i][5] = incidencia.getActiu2(); // Actiu2 relacion amb la incidència
            data[i][6] = incidencia.getDescripcio(); // Data de creació
            data[i][7] = incidencia.getDataCreacio(); // Descripció de la incidència
            data[i][8] = incidencia.getEmailCreador(); // Descripció de la incidència
            data[i][9] = incidencia.getTecnicAssignat(); // Descripció de la incidència
        }
        
        // Crear taula i mostrar dades
        JTable table = new JTable(data, columnNames); // Crear taula amb dades
        JScrollPane scrollPane = new JScrollPane(table); // Crear un JScrollPane per la taula
        scrollPane.setPreferredSize(new Dimension(1500, 500)); // Ajustar la mida de la taula
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

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
        String[] columnNames = {"E-mail","Àrea", "Cap", "Rol", "Intents Fallits", "Comentaris"}; // Noms de les columnes
        Object[][] data = new Object[usuaris.size()][6]; // Matriu per emmagatzemar les dades

        // Iterar a través dels usuaris per omplir la matriu de dades
        for (int i = 0; i < usuaris.size(); i++) {
            Usuari usuari = usuaris.get(i);
            data[i][0] = usuari.getEmail(); // E-mail de l'usuari
            data[i][1] = usuari.getArea(); // Àrea de l'usuari
            data[i][2] = usuari.getCap(); // Cap de l'usuari
            data[i][3] = usuari.getRol(); // Rol de l'usuari
            data[i][4] = usuari.getIntentsFallits(); // Rol de l'usuari
            data[i][5] = usuari.getComentaris(); // Rol de l'usuari
        }

        // Crear taula i mostrar dades
        JTable table = new JTable(data, columnNames); // Crear taula amb dades
        JScrollPane scrollPane = new JScrollPane(table); // Crear un JScrollPane per la taula
        scrollPane.setPreferredSize(new Dimension(1500, 500)); // Ajustar la mida de la taula
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

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
    private void llistarActius() {
        // Usamos el servei per obtenir els actius
        List<Actiu> actius = serveiActiu.llistarActius(); // Obtenir tots els actius

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
        scrollPane.setPreferredSize(new Dimension(1500, 500)); // Ajustar la mida de la taula
        
        // Centrar el contingut de les cel·les de les taules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

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

    //Metode per crear una incidència
    private void crearIncidencia(String usuari) {
    	FinestraCrearIncidencia finestraCrearIncidencia = new FinestraCrearIncidencia(this, usuari);
    	finestraCrearIncidencia.setVisible(true);

    }
    
// Metode per modificar una incidència existent
private void modificarIncidencia() {
	FinestraModificarIncidencia finestraModificarIncidencia = new FinestraModificarIncidencia(this);
	finestraModificarIncidencia.setVisible(true);
}

//Metode per eliminar una incidència
private void eliminarIncidencia() {
	FinestraEliminarIncidencia finestraEliminarIncidencia = new FinestraEliminarIncidencia(this);
	finestraEliminarIncidencia.setVisible(true);
}

//Metode per llistar incidències per usuari
private void llistarIncidenciesUsuari() {
	FinestraLlistarIncidenciesUsuari finestraLlistarIncidenciesUsuari = new FinestraLlistarIncidenciesUsuari(this);
	finestraLlistarIncidenciesUsuari.setVisible(true);
	        
}

// Metode per llistar incidències per tipus
private void llistarIncidenciesTipus() {
	FinestraLlistarIncidenciesTipus finestraLlistarIncidenciesTipus = new FinestraLlistarIncidenciesTipus(this);
	finestraLlistarIncidenciesTipus.setVisible(true);
	        
}

//Metode per llistar incidències per prioritat
private void llistarIncidenciesPrioritat() {
	FinestraLlistarIncidenciesPrioritat finestraLlistarIncidenciesPrioritat = new FinestraLlistarIncidenciesPrioritat(this);
	finestraLlistarIncidenciesPrioritat.setVisible(true);
	        
}

//Metode per llistar incidències per estat
private void llistarIncidenciesEstat() {
	FinestraLlistarIncidenciesEstat finestraLlistarIncidenciesEstat = new FinestraLlistarIncidenciesEstat(this);
	finestraLlistarIncidenciesEstat.setVisible(true);
	        
}

//Metode per crear un actiu
private void crearActiu() {
	FinestraCrearActiu finestraCrearActiu = new FinestraCrearActiu(this);
	finestraCrearActiu.setVisible(true);

}

// Metode per modificar un actiu existent
private void modificarActiu() {
	FinestraModificarActiu finestraModificarActiu = new FinestraModificarActiu(this);
	finestraModificarActiu.setVisible(true);
}

//Metode per eliminar un actiu
private void eliminarActiu() {
	FinestraEliminarActiu finestraEliminarActiu = new FinestraEliminarActiu(this);
	finestraEliminarActiu.setVisible(true);
}


// Metode per llistar actius per tipus
private void llistarActiusTipus() {
	FinestraLlistarActiusTipus finestraLlistarActiusTipus = new FinestraLlistarActiusTipus(this);
	finestraLlistarActiusTipus.setVisible(true);
}

//Metode per llistar actius per àrea
private void llistarActiusArea() {
	FinestraLlistarActiusArea finestraLlistarActiusArea = new FinestraLlistarActiusArea(this);
	finestraLlistarActiusArea.setVisible(true);
}

//Metode per llistar actius per marca
private void llistarActiusMarca() {
	FinestraLlistarActiusMarca finestraLlistarActiusMarca = new FinestraLlistarActiusMarca(this);
	finestraLlistarActiusMarca.setVisible(true);
}

//Metode per crear un usuari
private void crearUsuari() {
	FinestraCrearUsuari finestraCrearUsuari = new FinestraCrearUsuari(this);
	finestraCrearUsuari.setVisible(true);

}


// Metode per modificar un usuari existent
private void modificarUsuari() {
	FinestraModificarUsuari finestraModificarUsuari = new FinestraModificarUsuari(this);
	finestraModificarUsuari.setVisible(true);
}

//Metode per eliminar un usuari existent
private void eliminarUsuari() {
	FinestraEliminarUsuari finestraEliminarUsuari = new FinestraEliminarUsuari(this);
	finestraEliminarUsuari.setVisible(true);
}


// Metode per llistar usuaris per àrea
private void llistarUsuarisArea() {
	FinestraLlistarUsuarisArea finestraLlistarUsuarisArea = new FinestraLlistarUsuarisArea(this);
	finestraLlistarUsuarisArea.setVisible(true);
}

//Metode per llistar usuaris per rol
private void llistarUsuarisRol() {
	FinestraLlistarUsuarisRol finestraLlistarUsuarisRol = new FinestraLlistarUsuarisRol(this);
	finestraLlistarUsuarisRol.setVisible(true);
}

// MNetode per canviar la contrasenya
private void resetContrasenya() {
    FinestraResetContrasenya finestraResetContrasenya = new FinestraResetContrasenya(this, usuari);
    finestraResetContrasenya.setVisible(true);
}
private void carregarFons() {
    try {
        URI uri = new URI("https://i.ibb.co/M6q3ZCZ/ENMARXA.png"); 
        URL url = uri.toURL(); 
        fonsPantalla = ImageIO.read(url); 
        panelFons.setFondo(fonsPantalla); // Actualiza la imagen de fondo en el panel
        panelFons.repaint(); // Repaint para mostrar la nueva imagen
    } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
    }
}


// Clase interna para el panel con imagen de fondo
class PanelFons extends JPanel {
    private Image fonsPantalla;

    public PanelFons(Image fonsPantalla) {
        this.fonsPantalla = fonsPantalla;
    }

    public void setFondo(Image fonsPantalla) {
        this.fonsPantalla = fonsPantalla;
    }

// Sobrescriure el mètode 'paint' per mostrar la imatge de fons (logo del nostre grup)
@Override
public void paint(Graphics g) {
    super.paint(g); // Dibuixar la finestra
    if (fonsPantalla != null) {
        int imgHeight = fonsPantalla.getHeight(this);
        int imgWidth = fonsPantalla.getWidth(this);
            // Dibuixar la imatge sense estirar-la
            // Image scaledImage = fonsPantalla.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            g.drawImage(fonsPantalla, 0, -imgHeight / 2 + getHeight() / 2, imgWidth, imgHeight, this);
    }
}

}

}