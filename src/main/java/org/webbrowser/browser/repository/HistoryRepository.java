package org.webbrowser.browser.repository;

import javafx.collections.ObservableList;
import org.webbrowser.accounts.Account;
import org.webbrowser.browser.HistoryEntry;
import org.webbrowser.database.DatabaseManager;

import java.sql.*;

public class HistoryRepository {
    public void createTableIfAbsent() throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        String query = """
        CREATE TABLE IF NOT EXISTS historydata(
            email TEXT,
            date TEXT,
            url TEXT
        )
        """;

        Statement stmt = connection.createStatement();
        stmt.execute(query);

    }

    public ObservableList<HistoryEntry> getHistory(Account account) throws SQLException{
        createTableIfAbsent();
        Connection connection = DatabaseManager.getConnection();
        ObservableList<HistoryEntry> entries = javafx.collections.FXCollections.observableArrayList();

        String query = "SELECT date, url FROM historydata WHERE email = ? ORDER BY date DESC";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,account.getEmail());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String date = rs.getString("date");
            String url = rs.getString("url");
            entries.add(new HistoryEntry(date, url));
        }
        return entries;
    }

    public void deleteHistory(Account account) throws SQLException{
       createTableIfAbsent();
       Connection connection = DatabaseManager.getConnection();

       String query = "DELETE FROM historydata WHERE email = ?";
       PreparedStatement pstmt = connection.prepareStatement(query);
       pstmt.setString(1, account.getEmail());
       pstmt.executeUpdate();
    }

    public void append(String email, String date, String url) throws SQLException{
        createTableIfAbsent();
        Connection connection = DatabaseManager.getConnection();

        String query = "INSERT INTO historydata (email, date, url) VALUES (?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,email);
        pstmt.setString(2,date);
        pstmt.setString(3,url);
        pstmt.executeUpdate();
    }
}
