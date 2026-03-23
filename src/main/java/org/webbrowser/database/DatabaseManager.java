package org.webbrowser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class responsible for managing MySQL database connections.
 * <p>
 *     Uses environment variables to configure the connections:
 *     <ul>
 *         <li><b>REMOTE_DB_URL</b> - JDBC URL (e.g. jdbc:mysql://localhost:3306/MyDatabase).</li>
 *         <li><b>REMOTE_DB_USER</b> - Database username.</li>
 *         <li><b>REMOTE_DB_PASSWORD</b> - Database password.</li>
 *     </ul>
 *     Intended to be used with environment variables in IntelliJ.
 * </p>
 * <p>
 *     <b>Setup instructions:</b>
 *     <ol>
 *         <li>Create a <code>.env</code> file.</li>
 *         <li>Add the required variables (REMOTE_DB_URL, REMOTE_DB_USER, REMOTE_DB_PASSWORD).</li>
 *         <li>Link the .env file in the run configurations.</li>
 *
 *     </ol>
 * </p>
 * @author Axel
 * @since 2026
 */
public class DatabaseManager {
    /**
     * Database connection URL.
     */
    private static final String URL = System.getenv("REMOTE_DB_URL");
    /**
     * Database username.
     */
    private static final String USERNAME = System.getenv("REMOTE_DB_USER");
    /**
     * Database password.
     */
    private static final String PASSWORD = System.getenv("REMOTE_DB_PASSWORD");

    /**
     * Creates and returns a new database connection.
     * @return a {@link Connection} to the configured database.
     * @throws SQLException if a connection error occurs.
     * @throws IllegalStateException if environment variables aren't set.
     */
    public static Connection getConnection() throws SQLException {
       if(URL == null || USERNAME == null || PASSWORD == null) {
           throw new IllegalStateException("Database environment variables not set. Enable in run configurations.");
       }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
