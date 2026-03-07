package org.webbrowser.accounts;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountWindow {
    private Stage stage;
    public AccountWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/account.fxml"));
        Scene scene = new Scene(loader.load(),400,400);
        stage = new Stage();
        stage.setTitle("Account manager");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

}
