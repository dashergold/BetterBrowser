package org.webbrowser.chat.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a chat server that accepts multiple client connections.
 * <p>
 * This class handles:
 * <ul>
 *     <li>Listen for incoming client connections on a specified host and port.</li>
 *     <li>Maintain a history of chat messages.</li>
 *     <li>Broadcast messages to all connected clients.</li>
 *     <li>Manage client handler threads.</li>
 * </ul>
 * Implements {@link Runnable} to run in a dedicated server thread.
 * </p>
 *
 * @author Axel
 * @since 2026
 */
public class Server implements Runnable{
    /**
     * List of the messages that have been sent.
     */
    private final List<String> messageHistory = new ArrayList<>();
    /**
     * The IP address the server is listening on.
     */
    private String host;
    /**
     * The port the server is listening on.
     */
    private int port;
    /**
     * A callback {@link Runnable} that is executed once the server socket has been successfully created and is ready to accept client connections.
     */
    private Runnable onReady;
    /**
     * The socket the server is using.
     */
    private ServerSocket serverSocket;
    /**
     * The state of the server.
     */
    private boolean running = true;
    /**
     * A list of the clients connected to the server.
     */
    private static List<ClientHandler> clients = new ArrayList<>();

    /**
     * Creates a new server on the specified host and port.
     * @param host the server host IP address.
     * @param port the server port.
     * @param onReady a callback executed once the server socket is ready.
     */
    public Server(String host, int port, Runnable onReady)  {
        this.host = host;
        this.port = port;
        this.onReady = onReady;
    }

    /**
     * Adds a message to the servers history.
     * @param message the message to add.
     */
    public synchronized void addMessage(String message) {
        messageHistory.add(message);
    }

    /**
     * Returns the servers message history.
     * @return a list of messages.
     */
    public synchronized List<String> getMessageHistory(){
        return new ArrayList<>(messageHistory);
    }

    /**
     * Broadcasts a message to all connected clients.
     * @param message the message to broadcast.
     */
    public void broadcast(String message) {
        for(ClientHandler c: clients) {
            c.sendMessage(message);
        }
    }

    /**
     * The main server loop.
     * <p>
     *     Listens for incoming connections and spawns a {@link ClientHandler} thread for each client.
     *
     * </p>
     */
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port, 50, InetAddress.getByName(host));

            if(onReady != null) {
                onReady.run();
            }

            while(running) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, this);

                clients.add(handler);
                Thread th = new Thread(handler);
                th.setDaemon(true);
                th.start();
            }


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IO error: "+ e.getMessage());
        } finally {
            close();
        }
    }

    /**
     * Closes the server and all connected clients.
     */
    public void close() {

        try {
            if(serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            for(ClientHandler c: clients) {
                c.close();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        running = false;
    }

    /**
     * Returns the host the server is listening on.
     * @return the server host.
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns the port the server is listening on.
     * @return the server port.
     */
    public int getPort() {
        return port;
    }
}
