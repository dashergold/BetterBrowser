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
 * @author Axel
 * @since 2026
 */
public class SignUpController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private final AccountService accountService = AccountService.getInstance();

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
    @FXML
    private void goToLogin() throws IOException {
        AccountWindow.switchScene("/org/webbrowser/browser/accountSignIn.fxml");
    }
}
