package org.webbrowser.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Utility class for handling dialog interactions with the user.
 * <p>
 *     Provides methods for:
 *     <ul>
 *         <li>Retrieving a code entered by the user.</li>
 *         <li>Warning the user with a confirmation dialog.</li>
 *     </ul>
 * </p>
 * @author Axel
 * @since 2026
 */
public class DialogUtil {
    /**
     * Prompts the user to send a verification code sent to their email.
     *
     * @param email the email address the code was sent to.
     * @return The code entered as an integer, or {@code 0} if there is no result.
     */
    public static int getUserInputCode(String email) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text input dialog");
        dialog.setHeaderText("enter code sent to " +email);
        dialog.setContentText("code: ");
        dialog.setGraphic(null);
        dialog.getDialogPane().getStylesheets().add(DialogUtil.class.getResource("/org/webbrowser/browser/style/style.css").toExternalForm());

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            return Integer.parseInt(result.get());
        }
        return 0;
    }

    /**
     * Displays a confirmation dialog before deleting an {@link org.webbrowser.accounts.Account}.
     * @param username the username of the account to delete.
     * @return {@code true} if the user confirms the action, otherwise {@code false}.
     */
    public static boolean warnUser(String username) {
        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setTitle("Warning");
        warning.setHeaderText("You are about to delete the account " + username+".");
        warning.setContentText("This action is irreversible. Do you wish to proceed?");
        warning.setGraphic(null);
        warning.getDialogPane().getStylesheets().add(DialogUtil.class.getResource("/org/webbrowser/browser/style/style.css").toExternalForm());

        Optional<ButtonType> result = warning.showAndWait();
        return (result.isPresent() && result.get() == ButtonType.OK);
    }
}
