package org.webbrowser.accounts.repository;

import org.webbrowser.accounts.Account;
import org.webbrowser.database.DatabaseManager;

import java.sql.*;

/**
 * @author Axel
 * @since 2026
 */
public class AccountRepository {
    public void createTableIfAbsent() throws SQLException {
        Connection connection = DatabaseManager.getConnection();

        String query = """
        CREATE TABLE IF NOT EXISTS accounts(
            email VARCHAR(100) PRIMARY KEY,
            username VARCHAR(100),
            password VARCHAR(100)
        )
        """;

        Statement stmt = connection.createStatement();
        stmt.execute(query);
    }
    public void save(Account account) throws SQLException{
        Connection connection = DatabaseManager.getConnection();

        String query = "INSERT INTO accounts (email, username, password) VALUES (?,?,?)";

        PreparedStatement pstmt = connection.prepareStatement(query);

        pstmt.setString(1,account.getEmail());
        pstmt.setString(2,account.getUsername());
        pstmt.setString(3,account.getPassword());

        pstmt.executeUpdate();
    }
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
    public void deleteByEmail(String email) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        String query = "DELETE FROM accounts WHERE email = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,email);
        pstmt.executeUpdate();

    }
}
