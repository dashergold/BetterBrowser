package org.webbrowser.browser;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.HashMap;
/**
 * @author Axel
 * @since 2026
 */
public class HistoryController {

    private static Connection connection;

    @FXML
    private TableView<HistoryEntry> historyTable;
    @FXML
    private TableColumn<HistoryEntry, String> dateCol;
    @FXML
    private TableColumn<HistoryEntry, String> urlCol;



    public static void appendToDB(String date, String url) {
        try {
            String query = "INSERT INTO historydata (date, url) VALUES (?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1,date);
            stmt.setString(2,url);
            stmt.executeUpdate();
            System.out.println(date + " and " + url + " was entered into the database");

        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        createTableIfAbsent();

        dateCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDate())
        );
        urlCol.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUrl())
        );

        loadHistory();

    }
    private void loadHistory() {
        ObservableList<HistoryEntry> entries = javafx.collections.FXCollections.observableArrayList();

        try {
            String query = "SELECT date, url FROM historydata ORDER BY date DESC";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                String date = rs.getString("date");
                String url = rs.getString("url");
                entries.add(new HistoryEntry(date,url));
            }
            historyTable.setItems(entries);
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void deleteHistory() {

        try {
            String query = "DELETE FROM historydata";
            Statement stmt = connection.createStatement();
            stmt.execute(query);
            historyTable.getItems().clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void createTableIfAbsent() {

        try {
            String query = "CREATE TABLE IF NOT EXISTS HistoryData (" +
                    "date VARCHAR(50)," +
                    "url VARCHAR(500))";
            Statement stmt = connection.createStatement();
            stmt.execute(query);
            System.out.println("method passed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void connectToDB() {
        /*
          !!!FOR MYSQL IN INTELLIJ!!!
          Create a .env file
          Link the .env file through launcher configurations
          Set DB_URL to the url to the database (e.g. jdbc:mysql://localhost:3306/MyDatabase)
          Set DB_USER to the username of the MySQL database
          set DB_PASSWORD to the password of the MySQL database
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
    private class HistoryEntry {
        private final String date;
        private final String url;
        public HistoryEntry(String date, String url) {
            this.date = date;
            this.url = url;
        }
        public String getDate() {
            return date;
        }
        public String getUrl() {
            return url;
        }
    }

}
