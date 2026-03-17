package org.webbrowser.chat.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.chat.controller.ChatController;
import org.webbrowser.chat.controller.ChatServerCreationController;
import org.webbrowser.chat.network.Client;
import org.webbrowser.chat.network.Server;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for managing chat functionality, including UI handling, messaging, and client/server state.
 * <p>
 *     This class handles:
 *     <ul>
 *         <li>Opening and switching chat related views.</li>
 *         <li>Managing chat messages.</li>
 *         <li>Handling client and server connection.</li>
 *         <li>Maintaining running servers.</li>
 *     </ul>
 *     Implements a singleton to ensure a single shared instance across the application.
 * </p>
 * @author Axel
 * @since 2026
 */
public class ChatService {
    /**
     * A singleton, the single instance of this service.
     */
    private static final ChatService instance = new ChatService();
    /**
     * List of currently running servers.
     */
    private static List<Server> runningServers = new ArrayList<>();
    /**
     *  The current instance of {@link AccountService}.
     */
    private final AccountService accountService = AccountService.getInstance();
    /**
     * The current account.
     */
    private Account account;
    /**
     * Root pane where chat UI is rendered within.
     */
    private BorderPane rootPane;
    /**
     * List of chat messages.
     */
    private ObservableList<String> messages = FXCollections.observableArrayList();
    /**
     * Current active server instance.
     */
    private Server server;
    /**
     * Current active client instance.
     */
    private Client client;

    /**
     * Private constructor for singleton usage.
     */
    private ChatService() {}

    /**
     * Opens the appropriate chat view depending on the current state:
     * <ul>
     *     <li>Login prompt if user is not signed in.</li>
     *     <li>Chat view if the user is connected to a server.</li>
     *     <li>Server creation/join view otherwise.</li>
     * </ul>
     * @param rootPane the main UI container.
     */
    public void openChat(BorderPane rootPane) {
        this.rootPane = rootPane;
        try {
            //not signed in
            if (!account.isRegistered()) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/chat/chatPrompt.fxml"));
                Parent loginPrompt = loader.load();
                this.rootPane.setRight(loginPrompt);
                return;
            }
            //signed in
            if (client != null) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/chat/chat.fxml"));

                Parent chatView = loader.load();
                ChatController controller = loader.getController();

                controller.setClient(client);

                this.rootPane.setRight(chatView);
                return;
            }
            //default
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/webbrowser/browser/chat/chatServerCreation.fxml"));

            Parent creationView = loader.load();
            ChatServerCreationController controller = loader.getController();
            controller.setRootPane(rootPane);

            this.rootPane.setRight(creationView);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Appends a message to the message history.
     * @param message message to append.
     */
    public void addMessage(String message) {
        messages.add(message);
    }

    /**
     * Sends a message through the active client.
     * @param msg the message content.
     */
    public void sendMessage(String msg) {
        if(msg == null || msg.isEmpty()) {
            return;
        }
        String message = account.getUsername()+": "+msg+"\t\t"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        if(client != null) {
            client.sendMessage(message);
        }
    }

    /**
     * Disconnects the current client.
     */
    public void disconnectClient() {
        closeWindow();
        client.close();
        client = null;
        messages.clear();
    }

    /**
     * Closes the chat window.
     */
    public void closeWindow() {
        rootPane.setRight(null);
    }

    /**
     * Returns the chat messages.
     * @return list of messages.
     */
    public ObservableList<String> getMessages() {
        return messages;
    }

    /**
     * Returns the host and port the client is connected to.
     * @return formatted string of connection information.
     */
    public String getLabelText() {
        return "connected to: "+client.getHost()+", at port: "+client.getPort();
    }

    /**
     * Sets the active server.
     * @param server the server instance.
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * Sets the active client.
     * @param client the client instance.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Sets the active account.
     * @param account the account instance.
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Adds a server to the list of running servers.
     * @param server the server to add.
     */
    public static void addRunningServer(Server server) {
        runningServers.add(server);
    }

    /**
     * Returns all running servers.
     * @return list of servers.
     */
    public static List<Server> getRunningServers() {
        return runningServers;
    }

    /**
     * Getter for the singleton instance.
     * @return the current instance.
     */
    public static ChatService getInstance() {
        return instance;
    }
}
