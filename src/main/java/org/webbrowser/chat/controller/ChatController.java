package org.webbrowser.chat.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.webbrowser.chat.network.Client;
import org.webbrowser.chat.network.ClientHandler;
import org.webbrowser.chat.service.ChatService;

public class ChatController {
    @FXML
    private TextArea typingField;

    @FXML
    private TextArea messagesField;

    private Client client;

    public void initialize() {
        ChatService.getInstance().getMessages().addListener((javafx.collections.ListChangeListener<String>) change -> {
            while(change.next()) {
                if(change.wasAdded()) {
                    for(String msg: change.getAddedSubList()) {
                        messagesField.appendText(msg+"\n");

                    }
                }
            }
        });
    }
    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void sendMessage() {
        String msg = typingField.getText();
        if(msg == null || msg.isEmpty()) {
            return;
        }
        client.sendMessage(msg);
        typingField.clear();
    }
}
