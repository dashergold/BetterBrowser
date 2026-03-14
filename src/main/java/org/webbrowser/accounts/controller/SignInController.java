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
public class SignInController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private final AccountService accountService = AccountService.getInstance();



    @FXML
    private void handleLogin() {
        try {
            Account account = accountService.login(
                  emailField.getText(),
                  passwordField.getText()
            );
            errorLabel.setText("Login successful");
        } catch( Exception e) {
            errorLabel.setText(e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void goToRegister() throws IOException {
        AccountWindow.switchScene("/org/webbrowser/browser/account/accountSignUp.fxml");
    }
}
