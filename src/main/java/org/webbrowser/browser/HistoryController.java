package org.webbrowser.browser;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.HashMap;

public class HistoryController {

    private Connection connection;
    private HashMap<String, String> historyMap = new HashMap<>();

    @FXML
    private VBox tableColLeft;
    @FXML
    private VBox tableColRight;

    public void initialize() {
        connectToDB();
        createTableIfAbsent();
        loadHistory();
    }
    private void loadHistory() {



        try {
            String query = "SELECT date, url FROM historydata";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                String date = rs.getString("date");
                String url = rs.getString("url");
                historyMap.put(date,url);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        historyMap.forEach((l, r) -> {
            tableColLeft.getChildren().add(new Label(l));
            tableColRight.getChildren().add(new Label(r));

        });



    }

    private void createTableIfAbsent() {

        try {
            String query = "CREATE TABLE IF NOT EXISTS HistoryData (" +
                    "date VARCHAR(50)," +
                    "url VARCHAR(500))";
            Statement stmt;
            stmt = connection.createStatement();
            stmt.execute(query);
            System.out.println("method passed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void connectToDB() {
        /**
         * !!!FOR MYSQL IN INTELLIJ!!!
         * Create a .env file
         * Link the .env file through launcher configurations
         * Set DB_URL to the url to the database (e.g. jdbc:mysql://localhost:3306/MyDatabase)
         * Set DB_USER to the username of the MySQL database
         * set DB_PASSWORD to the password of the MySQL database
         */
        final String URL = System.getenv("DB_URL");
        final String USERNAME = System.getenv("DB_USER");
        final String PASSWORD = System.getenv("DB_PASSWORD");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Database Connection successful");
        }
        catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
