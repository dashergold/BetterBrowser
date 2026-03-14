package org.webbrowser.chat.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.chat.controller.ChatServerCreationController;

import java.io.IOException;

public class ChatService {

    private static final ChatService instance = new ChatService();
    private final AccountService accountService = AccountService.getInstance();
    private Account account;
    private BorderPane rootPane;
    private ObservableList<String> messages = FXCollections.observableArrayList();

    private ChatService() {}





    public void openChat(BorderPane rootPane) {
        this.rootPane = rootPane;
        if(!account.isRegistered()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/chat/chatPrompt.fxml"));
                Parent loginPrompt = loader.load();
                this.rootPane.setRight(loginPrompt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/chat/chatServerCreation.fxml"));
                Parent creationView = loader.load();
                ChatServerCreationController controller = loader.getController();
                controller.setRootPane(rootPane);
                this.rootPane.setRight(creationView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
}
