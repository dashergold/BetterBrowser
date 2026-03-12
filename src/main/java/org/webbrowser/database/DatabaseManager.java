package org.webbrowser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    /*
    !!!FOR MYSQL IN INTELLIJ!!!
    Create a .env file
    Link the .env file through launcher configurations
    Set DB_URL to the url to the database (e.g. jdbc:mysql://localhost:3306/MyDatabase)
    Set DB_USER to the username of the MySQL database
    set DB_PASSWORD to the password of the MySQL database
    */
    private static final String URL = System.getenv("DB_URL");
    private static final String USERNAME = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
