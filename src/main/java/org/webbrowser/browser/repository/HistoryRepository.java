package org.webbrowser.browser.repository;

import javafx.collections.ObservableList;
import org.webbrowser.accounts.Account;
import org.webbrowser.browser.HistoryEntry;
import org.webbrowser.database.DatabaseManager;

import java.sql.*;

/**
 * Repository responsible for managing browser history.
 * <p>
 * This class handles database operations related to {@link HistoryEntry}, including creating the history table, retrieving history entries, inserting new records, and deleting history.
 * @author Axel
 * @since 2026
 */
public class HistoryRepository {
    /**
     * Creates the history table if it doesn't already exist.
     * @throws SQLException if a database access error occurs.
     */
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

    /**
     * Retrieves browsing history for a given account.
     * @param account the account whose history should be retrieved.
     * @return an {@link ObservableList} of {@link HistoryEntry}.
     * @throws SQLException if a database access error occurs.
     */
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

    /**
     * Appends a new history entry to the database.
     * @param email the account email.
     * @param date the visit date and time.
     * @param url the URL visited.
     * @throws SQLException if a database error occurs.
     */
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

    /**
     * Deletes all browsing history for a given account.
     * @param account the account whose history should be deleted.
     * @throws SQLException if a database access error occurs.
     */
    public void deleteHistory(Account account) throws SQLException{
       createTableIfAbsent();
       Connection connection = DatabaseManager.getConnection();

       String query = "DELETE FROM historydata WHERE email = ?";
       PreparedStatement pstmt = connection.prepareStatement(query);
       pstmt.setString(1, account.getEmail());
       pstmt.executeUpdate();
    }
}
