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
 * Controller responsible for handling the account sign up view.
 * <p>
 * This controller collects the user input, creates a new {@link Account}, attempts to registers it through the {@link AccountService}, and displays feedback to the user.
 * @author Axel
 * @since 2026
 */
public class SignUpController {
    /**
     * Service responsible for registration and management.
     */
    private final AccountService accountService = AccountService.getInstance();
    /**
     * Text field where the user enters their username.
     */
    @FXML
    private TextField usernameField;
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
     * Handles the sign up process when the user presses the "Sign up" button.
     * <p>
     * Creates a new {@link Account} using the input fields and attempts to register it through {@link AccountService#register(Account)}. {@link SignUpController#errorLabel} displays the results to the user.
     * @throws IOException if a scene change occurs and the FXML can't be loaded.
     */
    @FXML
    private void handleSignup() throws IOException{
        try {
            Account account = new Account(
                    usernameField.getText(),
                    emailField.getText(),
                    passwordField.getText()
            );
            accountService.register(account);
            errorLabel.setText("Signup successful");
        } catch(Exception e) {
            errorLabel.setText(e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * Swaps the scene to the sign in view.
     * @throws IOException if the FXML scene cannot be loaded.
     */
    @FXML
    private void goToLogin() throws IOException {
        AccountWindow.switchScene("/org/webbrowser/browser/account/accountSignIn.fxml");
    }
}
