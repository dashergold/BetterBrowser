package org.webbrowser.chat.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.webbrowser.browser.controller.BrowserController;
import org.webbrowser.chat.network.Client;
import org.webbrowser.chat.network.ClientHandler;
import org.webbrowser.chat.network.Server;
import org.webbrowser.chat.network.ServerManager;
import org.webbrowser.chat.service.ChatService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for creating or joining a chat server.
 * <p>
 * Provides actions to:
 * <ul>
 *     <li>Join an existing server.</li>
 *     <li>Create a new server.</li>
 * </ul>
 * <p>
 *
 * Author: Axel
 * @since 2026
 */
public class ChatServerCreationController {
    /**
     * Service responsible for managing chat functionality.
     */
    private final ChatService chatService = ChatService.getInstance();
    /**
     * Label to display warnings or errors caused by attempting to create a server.
     */
    @FXML
    private Label warningLabel;
    /**
     * Text field where the user enters the IP address to join a server.
     */
    @FXML
    private TextField joinIpField;
    /**
     * Text field where the user enters the port to join a server.
     */
    @FXML
    private TextField joinPortField;
    /**
     * Text field where the user enters the IP address to create a server.
     */
    @FXML
    private TextField createIpField;
    /**
     * Text field where the user enters the port to create a server.
     */
    @FXML
    private TextField createPortField;
    /**
     * The current client.
     */
    private Client client;
    /**
     * The root pane where the chat server creation window will be placed.
     */
    private BorderPane rootPane;

    /**
     * Sets the root pane for opening chat windows.
     * @param rootPane the root {@link BorderPane}.
     */
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    /**
     * Joins an existing server with the IP address and the port entered by the user. Starts a {@link Client} thread and opens the chat window.
     */
    @FXML
    private void joinServer() {
        String ip = joinIpField.getText();
        int port = Integer.parseInt(joinPortField.getText());

        client = new Client(ip, port);
        Thread cThread = new Thread(client);
        cThread.setDaemon(true);
        cThread.start();
        chatService.setClient(client);
        chatService.openChat(rootPane);
    }

    /**
     * Creates a new server with the IP address and port entered by the user. Ensures that the address is not already in use, starts the server and client threads, and opens the chat window.
     *
     */
    @FXML
    private void createServer() {
        String ip = createIpField.getText();
        int port = Integer.parseInt(createPortField.getText());

        if(!ChatService.getRunningServers().isEmpty()) {
            for(Server s: ChatService.getRunningServers()) {
                if(s.getHost().equals(ip) && s.getPort() == port) {
                    warningLabel.setText("Address already in use");
                    return;
                }
            }
        }
        client = new Client(ip,port);
        chatService.setClient(client);
        Server server = ServerManager.getInstance().createServer(ip, port, () -> {

            Thread cThread = new Thread(client);
            cThread.setDaemon(true);
            cThread.start();

        });
        ChatService.addRunningServer(server);


        Thread sThread = new Thread(server);
        sThread.setDaemon(true);
        sThread.start();

        chatService.setServer(server);

        chatService.openChat(rootPane);

    }

    /**
     * Closes the chat creation window.
     */
    @FXML
    private void close() {
        chatService.closeWindow();
    }
}
