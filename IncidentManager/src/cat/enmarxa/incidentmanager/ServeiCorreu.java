//package cat.enmarxa.incidentmanager;
//
//import java.util.Properties;
//
//import javax.mail.MessagingException;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
////import java.util.Properties;
////import javax.mail.Message;
////import javax.mail.MessagingException;
////import javax.mail.PasswordAuthentication;
////import javax.mail.Session;
////import javax.mail.Transport;
////import javax.mail.internet.InternetAddress;
////import javax.mail.internet.MimeMessage;
//
//public class ServeiCorreu {
//
//    // Envia un correu electrònic per restablir la contrasenya d'un usuari
//    public void enviarCorreuRestablimentContrasenya(String destinatari, String enllaçRestabliment) {
//        String assumpte = "Restabliment de la contrasenya";  // Assumpte del correu
//        String missatge = "Fes clic en el següent enllaç per restablir la teva contrasenya: " + enllaçRestabliment;  // Contingut del correu
//
//        // Enviar el correu utilitzant el mètode genèric
//        enviarCorreu(destinatari, assumpte, missatge);
//    }
//
//    // Envia un correu electrònic per notificar la sol·licitud de registre d'un nou usuari
//    public void enviarCorreuAltaUsuari(String destinatari, String area) {
//        String assumpte = "Sol·licitud d'alta d'un nou usuari";  // Assumpte del correu
//        String missatge = "Un usuari de l'àrea " + area + " ha sol·licitat donar-se d'alta.";  // Contingut del correu
//
//        // Enviar el correu utilitzant el mètode genèric
//        enviarCorreu(destinatari, assumpte, missatge);
//    }
//
//    // Mètode genèric per enviar un correu electrònic
//    private void enviarCorreu(String destinatari, String assumpte, String missatge) {
//        // Configuració del servidor SMTP
//        Properties propietats = new Properties();
//        propietats.put("mail.smtp.auth", "true");  // Autenticació requerida
//        propietats.put("mail.smtp.starttls.enable", "true");  // Activació de STARTTLS per a la seguretat
//        propietats.put("mail.smtp.host", "smtp.example.com");  // Servidor SMTP
//        propietats.put("mail.smtp.port", "587");  // Port SMTP
//
//        // Credencials del correu de l'aplicació
//        final String usuari = "el_teu_correu@example.com";  // Nom d'usuari del correu (a substituir)
//        final String contrasenya = "la_teva_contrasenya";    // Contrasenya del correu (a substituir)
//
//        // Creació de la sessió amb autenticació
//        Session sessio = Session.getInstance(propietats, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(usuari, contrasenya);  // Autenticació amb l'usuari i contrasenya
//            }
//        });
//
//        try {
//            // Creació del missatge de correu electrònic
//            Message missatgeCorreu = new MimeMessage(sessio);
//            missatgeCorreu.setFrom(new InternetAddress(usuari));  // Adreça del remitent
//            missatgeCorreu.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatari));  // Destinataris del correu
//            missatgeCorreu.setSubject(assumpte);  // Assumpte del correu
//            missatgeCorreu.setText(missatge);  // Contingut del correu
//
//            // Enviament del correu
//            Transport.send(missatgeCorreu);
//
//            System.out.println("Correu enviat correctament a " + destinatari);  // Confirmació de l'enviament
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);  // Gestió de possibles excepcions
//        }
//    }
//}
