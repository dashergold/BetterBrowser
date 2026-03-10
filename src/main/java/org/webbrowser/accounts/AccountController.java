package org.webbrowser.accounts;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import org.webbrowser.settings.ConfigManager;
import org.webbrowser.settings.SettingsController;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import java.util.Random;


//TODO THIS AND HISTORY SQL SHOULD BE MOVED TO A SEPARATE FILE E.G. "SQLHANDLER"
public class AccountController {
    private static Connection connection;
    private static Account account;
    @FXML
    private Label usernameLabel;


    public void initialize() {
        usernameLabel.setText(account.getUsername());
        System.out.println("account manager initialized");
        createTableIfAbsent();



    }




    public void createTableIfAbsent() {
        try {
            String query = "CREATE TABLE IF NOT EXISTS accounts(" +
                    "email VARCHAR(100) PRIMARY KEY," +
                    "username VARCHAR(100)," +
                    "password VARCHAR(100))";
            Statement stmt = connection.createStatement();
            stmt.execute(query);
            System.out.println("account sql method passed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void connectToDB(){
        final String URL = System.getenv("DB_URL");
        final String USERNAME = System.getenv("DB_USER");
        final String PASSWORD = System.getenv("DB_PASSWORD");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("account Database Connection successful");
        }
        catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static Account getValidAccount(String email) {
        try{
            String query = "SELECT username, password FROM accounts WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                String u = rs.getString("username");
                String p = rs.getString("password");

                Account account = new Account(u,email, p);
                return account;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean isRegisteredAccount(String email) {
        try {
            String query = "SELECT 1 FROM accounts WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1,email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            return false;
        }
    }
    public static Account handleAccountFromConfig(String email) {
        if(!isRegisteredAccount(email)) {
            account = new Account();

            System.out.println(account.toString());
            SettingsController.setAccount(account);
            AccountWindow.setAccount(account);
            return account;

        } else {

            account = getValidAccount(email);
            System.out.println(account.toString());
            SettingsController.setAccount(account);
            AccountWindow.setAccount(account);
            return account;
        }
    }
    public static String signIn(String email, String password) {
        if(!isRegisteredAccount(email)) {
            return email + " is not a registered account";
        }
        account = getValidAccount(email);
        if(!account.getPassword().equals(password)) {
            return "incorrect password";
        }
        ConfigManager.editAccountConfig(account);
        SettingsController.setAccount(account);
        AccountWindow.setAccount(account);

        return "sign in successful";

    }
    public static String signUp(String username, String email, String password) throws IOException{
        if(!isRegisteredAccount(email)) {

            EmailVerificationHandler.setEmailAddress(email);
            Random rnd = new Random();
            int code = rnd.nextInt(111111,999999);
            EmailVerificationHandler.setCode(code);
            EmailVerificationHandler.sendVerificationEmail();

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Text input dialog");
            dialog.setHeaderText("enter code sent to " +email);
            dialog.setContentText("code: ");
            dialog.setGraphic(null);

            Optional<String> result = dialog.showAndWait();
            int userCode = 0;
            if(result.isPresent()) {
               userCode = Integer.parseInt(result.get());
            }
            if(userCode != code) {
                return "Sign up failed, wrong code was entered";
            } else {
                registerAccount(username, email, password);
                return "Sign up successful";
            }

        }
        return email + " is already registered";
    }
    private static void registerAccount(String username, String email, String password) {
        try {
            String query = "INSERT INTO accounts (email, username, password) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,email);
            pstmt.setString(2,username);
            pstmt.setString(3,password);
            pstmt.executeUpdate();
            System.out.println("account: "+username+ " was entered into the database");

            account = new Account(username,email,password);
            ConfigManager.editAccountConfig(account);
            SettingsController.setAccount(account);
            AccountWindow.setAccount(account);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void signOut() {
        account = new Account();
        ConfigManager.editAccountConfig(account);
        SettingsController.setAccount(account);
        AccountWindow.setAccount(account);
    }
    @FXML
    private void deleteAccount() {
        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setTitle("Warning!");
        warning.setHeaderText("You are about to delete the account "+account.getUsername()+ ".");
        warning.setContentText("This action is irreversible. Do you wish to proceed?");
        warning.setGraphic(null);

        Optional<ButtonType> result = warning.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            try {
                String query = "DELETE FROM accounts WHERE email = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1,account.getEmail());
                pstmt.executeUpdate();
                System.out.println("account: "+account.getUsername()+ " was deleted from the database");
                account = new Account();
                ConfigManager.editAccountConfig(account);
                SettingsController.setAccount(account);
                AccountWindow.setAccount(account);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
