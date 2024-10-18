package cat.enmarxa.incidentmanager;

import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


public class ServeiLogin {

    private Socket socket;

	public ServeiLogin(Socket socket) {
        this.socket = socket;
	}
    
	// Mètode per gestionar el login d'un usuari
    public boolean iniciarSessio(String correu, String contrasenya) throws SQLException {
        String consulta = "SELECT contrasenya, intents_fallits FROM usuaris WHERE id_usuari = ?";
        String contrasenyaDesada = null;
        
        try (Connection connexio = ConnexioBaseDades.obtenirConnexio();
             PreparedStatement sentencia = connexio.prepareStatement(consulta)) {

            sentencia.setString(1, correu);
            ResultSet resultats = sentencia.executeQuery();
            
            if (resultats.next()) {
                contrasenyaDesada = resultats.getString("contrasenya");
                int intentsFallits = resultats.getInt("intents_fallits");
                
                if (intentsFallits >= 3) {
                    return false; // El compte estava bloquejat per haver arribar al màxim d'intents permesos
                }

                if (contrasenyaDesada != null) {
                	if (contrasenya.equals(contrasenyaDesada)) { // Comparar contrasenyes
                		reiniciarIntentsFallits(correu);
                		return true;
                	}
                    else {
                    	incrementarIntentsFallits(correu);
                    	return false;
                    }
                }
            }
            return false;
        }
    }

    // Reiniciar els intents de login fallits (quan el login és exitós)
    private void reiniciarIntentsFallits(String correu) throws SQLException {
        String consulta = "UPDATE usuaris SET intents_fallits = 0 WHERE id_usuari = ?";
        try (Connection connexio = ConnexioBaseDades.obtenirConnexio();
             PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, correu);
            sentencia.executeUpdate();
        }
    }

    // Incrementar els intents de login fallits (quan el login no és exitós)
    private void incrementarIntentsFallits(String correu) throws SQLException {
        String consulta = "UPDATE usuaris SET intents_fallits = intents_fallits + 1 WHERE id_usuari = ?";
        try (Connection connexio = ConnexioBaseDades.obtenirConnexio();
             PreparedStatement sentencia = connexio.prepareStatement(consulta)) {
            sentencia.setString(1, correu);
            sentencia.executeUpdate();
        }
    }
    
    public boolean enviarEmailReseteigContrasenya(String email) {
        // Configura les propietats del servidor de correu
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Autenticació amb el correu electrònic del remitent
        final String username = "enmarxa_incidentmanager@gmail.com";
        final String password = "123456"; 

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crea un nou missatge
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Restabliment de contrasenya");
            message.setText("Per restablir la contrasenya, prem el següent enllaç: \n\n" +
                    "http://www.enmarxa.cat/restablircontrasenya=" + email);

            // Envia el missatge
            Transport.send(message);
            return true; // Email enviat amb èxit
        } catch (MessagingException e) {
            e.printStackTrace(); // Maneig d'errors
            return false; // Intent d'enviar l'e-mail fallit
        }
    }
}
