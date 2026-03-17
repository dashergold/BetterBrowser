package org.webbrowser.chat.network;

import javafx.application.Platform;
import org.webbrowser.chat.service.ChatService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Represents a chat client connecting to a chat server.
 * <p>
 * This class supports:
 * <ul>
 *     <li>Connecting to a server at a specific host and port.</li>
 *     <li>Sending messages to the server.</li>
 *     <li>Receiving messages and updating the UI thread.</li>
 *     <li>Shutdown of the connection.</li>
 * </ul>
 * <p>
 * Implements {@link Runnable} to run the client loop in a separate thread.
 * Uses {@link Platform#runLater(Runnable)} as to not interfere with JavaFX UI.
 *
 * @author Axel
 * @since 2026
 */
public class Client implements Runnable{
    /**
     * A singleton to get the current instance of this class for a window.
     */
    private static final Client instance = new Client();
    /**
     * The host the client connects to.
     */
    private String host;
    /**
     * The port the client connects to.
     */
    private int port;
    /**
     * The state of the client.
     */
    private boolean running = true;
    /**
     * The output of the client.
     */
    private PrintWriter out;
    /**
     * The input to the client.
     */
    private BufferedReader in;
    /**
     * The TCP socket the client uses to communicate with a server.
     */
    private Socket socket;

    /**
     * Private constructor for singleton usage.
     */
    private Client() {}

    /**
     * Creates a new client for connecting to a specific host and port.
     * @param host the server host IP address.
     * @param port  the server port.
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * The main client loop.
     * <p>
     * Connects to the server and listens for messages. Incoming messages are posted to the JavaFX UI thread via {@link Platform#runLater(Runnable)}.
     *
     */
    @Override
    public void run() {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while(running && (message = in.readLine()) != null) {
                String finalMessage = message;
                Platform.runLater(() -> {
                    ChatService.getInstance().addMessage(finalMessage);
                });
            }
        } catch (UnknownHostException e) {
            if(running) {throw new RuntimeException(e);}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            close();
        }
    }

    /**
     * Sends a message to the server.
     * @param message the message to send.
     */
    public void sendMessage(String message) {
        if(out != null) {out.println(message);}
    }

    /**
     * Closes the client connection and input/output streams.
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the server host.
     * @return the server host.
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns the server port.
     * @return the server port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Getter for the singleton instance.
     * @return the current instance.
     */
    public static Client getInstance() {
        return instance;
    }
}
