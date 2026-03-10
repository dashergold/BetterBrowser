package org.webbrowser.accounts;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Properties;

public class EmailVerificationHandler {
    private static final String SENDER = System.getenv("EMAIL_EMAIL");
    private static final String PASSWORD = System.getenv("EMAIL_PASSWORD");
    private static final String SERVER  = System.getenv("EMAIL_SERVER");
    private static final String PORT = System.getenv("EMAIL_PORT");
    private static int code;
    private static String emailAddress;

    public static void setCode(int randomCode) {
        code = randomCode;

    }
    public static void setEmailAddress(String email) {
        emailAddress = email;
    }

    public static void sendVerificationEmail() {
        composeEmail();
    }
    private static void composeEmail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host",SERVER);
        props.put("mail.smtp.port",PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER, PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailAddress));

            message.setSubject("Signup Verification Code");
            message.setText(String.valueOf(code));
            Transport.send(message);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
