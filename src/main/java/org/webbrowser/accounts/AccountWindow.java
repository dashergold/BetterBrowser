package org.webbrowser.accounts;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountWindow {
    private static Stage stage;
    private static Account account;
    public AccountWindow() throws IOException {
        FXMLLoader loader;
        if(account.isRegistered()) {
            loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/account.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/accountSignIn.fxml"));
        }
        Scene scene = new Scene(loader.load(),400,400);
        stage = new Stage();
        stage.setTitle("Account manager");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public static void setAccount(Account account) {
        AccountWindow.account = account;
    }
    public static void switchScene(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(AccountWindow.class.getResource(fxml));
        Scene scene = new Scene(loader.load(),400,400);
        stage.setScene(scene);
    }


}
