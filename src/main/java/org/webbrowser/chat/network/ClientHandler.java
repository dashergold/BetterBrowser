package org.webbrowser.chat.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles communication with a single chat client connected to a {@link Server}.
 * <p>
 * This class handles:
 * <ul>
 *     <li>Reading incoming messages from the client.</li>
 *     <li>Sending messages to the client.</li>
 *     <li>Broadcasting client messages through the server.</li>
 *     <li>Maintaining message history for new clients.</li>
 * </ul>
 * <p>
 * Implements {@link Runnable} to be executed in a separate thread for each client.
 *
 * @author Axel
 * @since 2026
 */
public class ClientHandler implements Runnable{
    /**
     * The socket used for communicating with the server.
     */
    private Socket socket;
    /**
     * The server the client handler communicates with.
     */
    private Server server;
    /**
     * The input of the client handler.
     */
    private BufferedReader in;
    /**
     * The output of the client handler.
     */
    private PrintWriter out;
    /**
     * The state of the client handler.
     */
    private boolean running = true;

    /**
     * Creates a handler for a connected client.
     * @param socket the clients socket.
     * @param server the server the client is trying to connect to.
     */
    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Main loop for the client handler.
     * <p>
     * Reads incoming messages from the client, adds them to the servers history, and broadcasts it to all connected clients.
     *
     */
    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            for(String msg: server.getMessageHistory()) {
                out.println(msg);
            }
            String message;
            while(running && (message = in.readLine()) != null) {
                server.addMessage(message);
                server.broadcast(message);
            }
        } catch (IOException e) {
            if(running) {throw new RuntimeException(e);}
        } finally {
            close();
        }
    }

    /**
     * Sends a message to the connected client(s).
     * @param message the message to send.
     */
    public void sendMessage(String message) {
        if(out != null) {
            out.println(message);
        }
    }

    /**
     * Closes the client connection and associated streams.
     */
    public void close() {
        running = false;
        try {
            if(socket != null && !socket.isClosed()) {
                socket.close();
            }
            if(in != null) {
                in.close();
            }
            if(out != null) {
                out.close();
            }
        } catch (Exception _) {

        }
    }
}
