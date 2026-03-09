package org.webbrowser.accounts;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleSignup() {

    }
    @FXML
    private void goToLogin() throws IOException {
        AccountWindow.switchScene("/org/webbrowser/browser/accountSignIn.fxml");
    }
}
