package org.webbrowser.accounts.repository;

import org.webbrowser.accounts.Account;
import org.webbrowser.database.DatabaseManager;

import java.sql.*;

/**
 * Repository responsible for handling SQL-queries for {@link org.webbrowser.accounts.service.AccountService}.
 * <p>
 * This class contains methods for creating a database tables, performing get and set tasks, and deleting entries from the database storing account data.
 * @author Axel
 * @since 2026
 */
public class AccountRepository {
    /**
     * Creates the account table if it doesn't already exist.
     * @throws SQLException if a database access error occurs.
     */
    public void createTableIfAbsent() throws SQLException {
        Connection connection = DatabaseManager.getConnection();

        String query = """
        CREATE TABLE IF NOT EXISTS accounts(
            email VARCHAR(100) PRIMARY KEY,
            username TEXT,
            password TEXT
        )
        """;

        Statement stmt = connection.createStatement();
        stmt.execute(query);
    }
    /**
     * Stores a new {@link Account} in the database.
     * @param account the account to be stored.
     * @throws SQLException if a database access error occurs.
     */
    public void save(Account account) throws SQLException{
        Connection connection = DatabaseManager.getConnection();

        String query = "INSERT INTO accounts (email, username, password) VALUES (?,?,?)";

        PreparedStatement pstmt = connection.prepareStatement(query);

        pstmt.setString(1,account.getEmail());
        pstmt.setString(2,account.getUsername());
        pstmt.setString(3,account.getPassword());

        pstmt.executeUpdate();
    }
    /**
     * Finds an {@link Account} by its email.
     *
     * @param email the email to search for.
     * @return the matching account, or an empty {@link Account} if not found.
     * @throws SQLException if a database access error occurs.
     */
    public Account findByEmail(String email) throws SQLException{
        Connection connection = DatabaseManager.getConnection();

        String query = "SELECT * FROM accounts WHERE email = ?";

        PreparedStatement pstmt = connection.prepareStatement(query);

        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();

        if(rs.next()) {
            return new Account(
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password")
            );
        }
        return new Account();
    }
    /**
     * Deletes an account by its email.
     * @param email the email (primary key) of the account to delete.
     * @throws SQLException if a database access error occurs.
     */
    public void deleteByEmail(String email) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        String query = "DELETE FROM accounts WHERE email = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,email);
        pstmt.executeUpdate();
    }
}
