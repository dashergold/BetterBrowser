package org.webbrowser.chat.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.browser.service.BrowserService;
import org.webbrowser.chat.controller.ChatController;
import org.webbrowser.chat.controller.ChatServerCreationController;
import org.webbrowser.chat.network.Client;
import org.webbrowser.chat.network.Server;
import org.webbrowser.chat.network.ServerManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatService {

    private static final ChatService instance = new ChatService();
    private final AccountService accountService = AccountService.getInstance();
    private Account account;
    private BorderPane rootPane;
    private ObservableList<String> messages = FXCollections.observableArrayList();
    private Server server;
    private Client client;

    private static List<Server> runningServers = new ArrayList<>();



    private ChatService() {}

    public void setServer(Server server) {
        this.server = server;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public static void addRunningServer(Server server) {
        runningServers.add(server);
    }
    public static List<Server> getRunningServers() {
        return runningServers;
    }



    public void openChat(BorderPane rootPane) {
        this.rootPane = rootPane;
        try {

            if (!account.isRegistered()) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/chat/chatPrompt.fxml"));
                Parent loginPrompt = loader.load();
                this.rootPane.setRight(loginPrompt);
                return;
            }

            if (client != null) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/chat/chat.fxml"));

                Parent chatView = loader.load();
                ChatController controller = loader.getController();

                controller.setClient(client);

                this.rootPane.setRight(chatView);
                return;
            }

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
    public void setAccount(Account account) {
        this.account = account;
    }

    public ObservableList<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public static ChatService getInstance() {
        return instance;
    }
    public void closeWindow() {
        rootPane.setRight(null);
    }

    public String getLabelText() {
        return "connected to: "+client.getHost()+", at port: "+client.getPort();
    }

    public void disconnectClient() {
        closeWindow();
        client.close();

        client = null;
        messages.clear();

    }

    public void sendMessage(String msg) {
        if(msg == null || msg.isEmpty()) {
            return;
        }
        String message = account.getUsername()+": "+msg+"\t\t"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        if(client != null) {
            client.sendMessage(message);
        }
    }
}
