package cat.enmarxa.incidentmanager;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class FinestraPrincipal extends JFrame {

    private JMenuBar barraMenu;
    private JMenu menuIncidencies, menuActius, menuInformes, menuLogout;
    private Connection connexioBD;
    private ServeiLogin serveiLogin;

    // Constructor de la finestra principal
    public FinestraPrincipal() {
        setTitle("Gestió d'Incidències TI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrem la finestra

        // Inicialització de la barra de menú
        barraMenu = new JMenuBar();

        // Menú Incidències
        menuIncidencies = new JMenu("Incidències");
        JMenuItem crearIncidencia = new JMenuItem("Crear Incidència");
        JMenuItem llistarIncidencies = new JMenuItem("Llistat d'Incidències");
        menuIncidencies.add(crearIncidencia);
        menuIncidencies.add(llistarIncidencies);

        // Accions dels submenús d'Incidències
        crearIncidencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		FinestraCrearIncidencia finestraCrearIncidencia = new FinestraCrearIncidencia(FinestraPrincipal.this);
	        finestraCrearIncidencia.setVisible(true);
            }
        });

	llistarIncidencies.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<Incidencia> incidencies = ServeiIncidencia.llistarIncidencies();

		        // Verificar si s'han trobat incidències
		        if (incidencies.isEmpty()) {
	 	        JOptionPane.showMessageDialog(FinestraPrincipal.this, "No s'han trobat incidències.", "Informació", JOptionPane.INFORMATION_MESSAGE);
			return;
       			 }

			// Crear un JTable per mostrar les incidències
		        String[] columnNames = {"ID", "Descripció", "Tipus", "Prioritat"};
		        Object[][] data = new Object[incidencies.size()][4];

		        for (int i = 0; i < incidencies.size(); i++) {
	   		        Incidencia incidencia = incidencies.get(i);
		 	        data[i][0] = incidencia.getId(); // Asegúrate de que la clase Incidencia tenga el método getId()
				data[i][1] = incidencia.getDescripcio(); // Métodos getters para cada campo
				data[i][2] = incidencia.getTipus();
				data[i][3] = incidencia.getPrioritat();
			}

			JTable table = new JTable(data, columnNames);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(new Dimension(400, 200)); // Ajustar el tamaño de la tabla

			// Mostrar la tabla en un cuadro de diálogo
			JOptionPane.showMessageDialog(FinestraPrincipal.this, scrollPane, "Llistat d'Incidències", JOptionPane.INFORMATION_MESSAGE);
		}
	});

        // Menú Actius
        menuActius = new JMenu("Actius");
        JMenuItem llistarActius = new JMenuItem("Llistat d'Actius");
        menuActius.add(llistarActius);

        // Acció del menú Actius
        llistarActius.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(FinestraPrincipal.this, "Funcionalitat per llistar actius.");
                // Aquí s'hauria d'afegir la funcionalitat per llistar actius
            }
        });

        // Menú Informes
        menuInformes = new JMenu("Informes");
        JMenuItem generarInforme = new JMenuItem("Generar Informe");
        menuInformes.add(generarInforme);

        // Acció del menú Informes
        generarInforme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(FinestraPrincipal.this, "Funcionalitat per generar informes.");
                // Aquí s'hauria d'afegir la funcionalitat per generar informes
            }
        });

        // Menú Logout
        menuLogout = new JMenu("Logout");
        JMenuItem confirmarLogout = new JMenuItem("Tancar Sessió");
        menuLogout.add(confirmarLogout);

        // Acció per confirmar el logout
        confirmarLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarSortida();
            }
        });

        // Afegim els menús a la barra de menú
        barraMenu.add(menuIncidencies);
        barraMenu.add(menuActius);
        barraMenu.add(menuInformes);
        barraMenu.add(menuLogout);

        // Afegim la barra de menú a la finestra
        setJMenuBar(barraMenu);
    }

    // Mètode per confirmar la sortida de l'aplicació
    private void confirmarSortida() {
        int resposta = JOptionPane.showConfirmDialog(this, "Estàs segur que vols tancar la sessió?", "Confirmar Logout", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            // Tanquem la connexió amb la base de dades i el servidor
            tancarConnexions();
            // Tanquem la finestra i sortim de l'aplicació
            System.exit(0);
        }
    }

    // Mètode per tancar la connexió amb la base de dades i el servidor
    private void tancarConnexions() {
        try {
            if (connexioBD != null && !connexioBD.isClosed()) {
                connexioBD.close();
                System.out.println("Connexió amb la base de dades tancada.");
            }
            // Aquí s'hauria de tancar la connexió amb el servidor (si escau)
            serveiLogin.tancarConnexioServidor(); // Tancar la connexió amb el servidor
            System.out.println("Connexió amb el servidor tancada.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Mètode principal per provar la finestra
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FinestraPrincipal finestra = new FinestraPrincipal();
                finestra.setVisible(true);
            }
        });
    }
}
