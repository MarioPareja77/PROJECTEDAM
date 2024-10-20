package cat.enmarxa.incidentmanager;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ServeiCorreu {

    // Envia un correu electrònic per restablir la contrasenya
    public void enviarCorreuRestablimentContrasenya(String destinatari, String enllaçRestabliment) {
        String assumpte = "Restabliment de la contrasenya";
        String missatge = "Fes clic en el següent enllaç per restablir la teva contrasenya: " + enllaçRestabliment;

        enviarCorreu(destinatari, assumpte, missatge);
    }

    // Envia un correu electrònic de sol·licitud de registre de nou usuari
    public void enviarCorreuAltaUsuari(String destinatari, String area) {
        String assumpte = "Sol·licitud d'alta d'un nou usuari";
        String missatge = "Un usuari de l'àrea " + area + " ha sol·licitat donar-se d'alta.";

        enviarCorreu(destinatari, assumpte, missatge);
    }

    // Mètode genèric per enviar un correu electrònic
    private void enviarCorreu(String destinatari, String assumpte, String missatge) {
        // Configuració del servidor SMTP
        Properties propietats = new Properties();
        propietats.put("mail.smtp.auth", "true");
        propietats.put("mail.smtp.starttls.enable", "true");
        propietats.put("mail.smtp.host", "smtp.example.com");
        propietats.put("mail.smtp.port", "587");

        // Credencials del correu de l'aplicació
        final String usuari = "el_teu_correu@example.com"; // Canvia això
        final String contrasenya = "la_teva_contrasenya";   // Canvia això

        // Creació de la sessió
        Session sessio = Session.getInstance(propietats, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuari, contrasenya);
            }
        });

        try {
            // Creació del missatge de correu electrònic
            Message missatgeCorreu = new MimeMessage(sessio);
            missatgeCorreu.setFrom(new InternetAddress(usuari));
            missatgeCorreu.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatari));
            missatgeCorreu.setSubject(assumpte);
            missatgeCorreu.setText(missatge);

            // Enviament del correu
            Transport.send(missatgeCorreu);

            System.out.println("Correu enviat correctament a " + destinatari);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
