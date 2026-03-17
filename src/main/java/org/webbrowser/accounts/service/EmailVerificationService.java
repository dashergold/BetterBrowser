package org.webbrowser.accounts.service;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.webbrowser.util.DialogUtil;

import java.util.Properties;
import java.util.Random;

/**
 * Service responsible for handling email verification during account registration.
 * <p>
 * This service generates a verification code, sends it via email using SMTP, and validates the code entered by the user.
 * <p>
 * Email configuration is retrieved from environment variables (.env file):
 * <ul>
 *     <li>EMAIL_EMAIL - Sender email address.</li>
 *     <li>EMAIL_PASSWORD - Sender email app password.</li>
 *     <li>EMAIL_SERVER - SMTP server host.</li>
 *     <li>EMAIL_PORT - SMTP server port.</li>
 * </ul>
 * @author Axel
 * @since 2026
 */
public class EmailVerificationService {
    /**
     * Sender email address.
     */
    private static final String SENDER = System.getenv("EMAIL_EMAIL");
    /**
     * Sender email app password.
     */
    private static final String PASSWORD = System.getenv("EMAIL_PASSWORD");
    /**
     * SMTP server host.
     */
    private static final String SERVER = System.getenv("EMAIL_SERVER");
    /**
     * SMTP server port.
     */
    private static final String PORT = System.getenv("EMAIL_PORT");
    /**
     * Recipient email address.
     */
    private String email;
    /**
     * Randomly generated verification code.
     */
    private int code;
    /**
     * Code entered by the user.
     */
    private int userCode;

    /**
     * Creates a new verification service for the given email.
     * @param email the email address to verify.
     */
    public EmailVerificationService(String email) {
        this.email = email;
    }
    /**
     * Sends a verification code to the users email and prompts the user to enter the received code.
     * <p>
     * This method:
     * <ul>
     *     <li>Generates a random 6-digit code.</li>
     *     <li>Sends the code via email.</li>
     *     <li>Prompts the user to for input using {@link DialogUtil}</li>
     * </ul>
     */
    public void sendVerification() {
        Random rnd = new Random();
        code = rnd.nextInt(100000,999999);
        composeEmail(code);
        userCode = DialogUtil.getUserInputCode(email);
    }
    /**
     * Checks if the verification was successful or not.
     * @return {@code true} if the entered code matches the generated code, otherwise {@code false}.
     */
    public boolean isSuccessful() {
        return code == userCode;
    }
    /**
     * Composes and sends the verification email containing the code.
     *
     * @param code the verification code to send.
     * @throws RuntimeException if email sending fails.
     */
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
