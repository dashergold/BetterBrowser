package org.webbrowser.accounts.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.AccountWindow;
import org.webbrowser.accounts.service.AccountService;

import java.io.IOException;
/**
 * Controller responsible for handling the sign in view.
 * <p>
 * This controller processes the user login attempts,
 * validates the credentials through {@link AccountService},
 * and displays feedback messages to the user.
 * @author Axel
 * @since 2026
 */
public class SignInController {
    /**
     * Service responsible for authentication and account operations.
     */
    private final AccountService accountService = AccountService.getInstance();
    /**
     * Text field where the user enters their email.
     */
    @FXML
    private TextField emailField;
    /**
     * Password field where the user enters their password.
     */
    @FXML
    private PasswordField passwordField;
    /**
     * Label for displaying the outcome of the login operation.
     */
    @FXML
    private Label errorLabel;

    /**
     * Handles the login attempt.
     * <p>
     * Retrieves the entered email and password, then attempts to authenticate the user through {@link AccountService#login(String, String)}.
     * If authentication fails,
     * {@link SignInController#errorLabel} is set to the exception message.
     */
    @FXML
    private void handleLogin() {
        try {
            accountService.login(emailField.getText(), passwordField.getText());

            errorLabel.setText("Login successful");
        } catch( Exception e) {
            errorLabel.setText(e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * Swaps the scene to the signup view.
     * @throws IOException if the FXML scene cannot be loaded.
     */
    @FXML
    private void goToRegister() throws IOException {
        AccountWindow.switchScene("/org/webbrowser/browser/account/accountSignUp.fxml");
    }
}
