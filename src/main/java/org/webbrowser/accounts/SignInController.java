package org.webbrowser.accounts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignInController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;



    @FXML
    private void handleLogin() {
    }
    @FXML
    private void goToRegister() throws IOException {
        AccountWindow.switchScene("/org/webbrowser/browser/accountSignUp.fxml");
    }
}
