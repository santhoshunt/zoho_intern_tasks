package servletclass;

import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;

import java.util.Properties;

public class SendMail {

    public void send(String toAddress, String msg) {
        String result = "not sent";
        String scopes[] = new String[] {
                "https://mail.google.com/"
        };
        // out.println("Preparing to Send Email...");
        String recipient = toAddress;
        String sub = "Digital Vault e-mail verification";

        String userName = "digitalvault.zoho@gmail.com";
        Properties properties = new Properties();
        String Access_token = "digitalvault7";

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties);
        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(userName));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(sub);

            // new
            MimeMultipart multipart = new MimeMultipart();

            // MESSAGE PART
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(msg);

            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            result = "sending";
            Transport.send(message, userName, Access_token);
            result = "Email Sent Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            result = "Error: unable to send message....";
        }

    }

}