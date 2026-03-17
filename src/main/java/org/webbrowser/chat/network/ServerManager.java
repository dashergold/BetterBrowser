package org.webbrowser.chat.network;

/**
 * Manages the lifecycle of a {@link Server} instance.
 * <p>
 *     This class handles:
 *     <ul>
 *         <li>Creating a server.</li>
 *         <li>Providing access to the current server.</li>
 *         <li>Shutting down the server safely.</li>
 *     </ul>
 * </p>
 * <p>
 *     Implements a singleton to ensure only one server manager exists.
 * </p>
 * @author Axel
 * @since 2026
 */
public class ServerManager {
    /**
     * A singleton, the single instance of the manager.
     */
    private static final ServerManager instance = new ServerManager();
    /**
     * The currently active server instance.
     */
    private Server server;

    /**
     * Private constructor for singleton usage.
     */
    private ServerManager() {}

    /**
     * Creates a new {@link Server} instance.
     * <p>
     *     The created server is stored in an instance variable.
     * </p>
     * @param host the server host IP address.
     * @param port the server port.
     * @param runnable a callback executed when the server is ready.
     * @return the created{@link Server}.
     */
    public Server createServer(String host, int port, Runnable runnable) {
        Server server = new Server(host, port, runnable);
        this.server = server;
        return server;
    }

    /**
     * Safely shuts down the current server.
     */
    public void shutdownServer() {
        try {
            server.close();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the current active server instance.
     * @return the {@link Server}, or null if no server has been created.
     */
    public Server getServer() {
        return server;
    }

    /**
     * Getter for the singleton instance.
     * @return the current instance.
     */
    public static ServerManager getInstance() {
        return instance;
    }
}
