package org.webbrowser.accounts.service;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.webbrowser.util.DialogUtil;

import java.util.Properties;
import java.util.Random;

public class EmailVerificationService {
    private static final String SENDER = System.getenv("EMAIL_EMAIL");
    private static final String PASSWORD = System.getenv("EMAIL_PASSWORD");
    private static final String SERVER = System.getenv("EMAIL_SERVER");
    private static final String PORT = System.getenv("EMAIL_PORT");

    private String email;
    private int code;
    private int userCode;

    public EmailVerificationService(String email) {
        this.email = email;
    }

    public void sendVerification() {
        Random rnd = new Random();
        code = rnd.nextInt(100000,999999);
        composeEmail(code);
        userCode = DialogUtil.getUserInputCode(email);
    }

    public boolean isSuccessful() {
        return code == userCode;
    }

    private void composeEmail(int code) {
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
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
