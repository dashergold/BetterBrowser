package Tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountQueryTest {



    private static final String URL = System.getenv("DB_URL");
    private static final String USERNAME = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    private static Connection connection;
    static void main(String[] args) {
        connect();
        //System.out.println(AccountController.isRegisteredAccount(connection, "usermail@host.com"));
    }

    private static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("connected to database");


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


