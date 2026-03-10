package org.webbrowser.accounts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignInController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;



    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String result = AccountController.signIn(email, password);
        errorLabel.setText(result);
    }
    @FXML
    private void goToRegister() throws IOException {
        AccountWindow.switchScene("/org/webbrowser/browser/accountSignUp.fxml");
    }
}
