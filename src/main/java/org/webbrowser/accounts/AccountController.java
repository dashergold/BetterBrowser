package org.webbrowser.accounts;

import org.webbrowser.settings.ConfigManager;
import org.webbrowser.settings.SettingsController;

import java.io.IOException;
import java.sql.*;


//TODO THIS AND HISTORY SQL SHOULD BE MOVED TO A SEPARATE FILE E.G. "SQLHANDLER"
public class AccountController {
    private static Connection connection;
    private static Account account;


    public void initialize() {
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
    public void signOut() {
        account = new Account();
        ConfigManager.editAccountConfig(account);
        SettingsController.setAccount(account);
        AccountWindow.setAccount(account);
    }


}
