package org.webbrowser.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class DialogUtil {
    public static int getUserInputCode(String email) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text input dialog");
        dialog.setHeaderText("enter code sent to " +email);
        dialog.setContentText("code: ");
        dialog.setGraphic(null);

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            return Integer.parseInt(result.get());
        }
        return 0;
    }

    public static boolean warnUser(String username) {
        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setTitle("Warning");
        warning.setHeaderText("You are about to delete the account " + username+".");
        warning.setContentText("This action is irreversible. Do you wish to proceed?");
        warning.setGraphic(null);

        Optional<ButtonType> result = warning.showAndWait();
        return (result.isPresent() && result.get() == ButtonType.OK);
    }
}
