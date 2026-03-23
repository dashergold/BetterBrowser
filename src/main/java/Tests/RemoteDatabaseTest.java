package Tests;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class RemoteDatabaseTest {
    private static final String URL = System.getenv("REMOTE_DB_URL");
    private static final String USER = System.getenv("REMOTE_DB_USER");
    private static final String PASSWORD = System.getenv("REMOTE_DB_PASSWORD");
    private static Connection conn;

    static void main(String[] args) {
       connect();
       int user = 2;
       String password = "SecretHashedPassword";
       //insertToDB(user, password);
        isValidPassword(user, password);
    }
    private static void isValidPassword(int user, String password) {
        try {
            String query = "SELECT * FROM TestTable WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                System.out.println(rs.getString("name"));
                if(BCrypt.checkpw(password,rs.getString("name"))) {
                    System.out.println("passwords match");
                }
                else {
                    System.out.println("passwords dont match");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertToDB(int user, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("Hashed password: " + hashedPassword);
        try {
            String query = "INSERT INTO TestTable (id, name) VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user);
            stmt.setString(2, hashedPassword);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
