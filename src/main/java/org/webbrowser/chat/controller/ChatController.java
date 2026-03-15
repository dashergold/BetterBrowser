package org.webbrowser.chat.controller;

import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.webbrowser.chat.network.Client;
import org.webbrowser.chat.network.ClientHandler;
import org.webbrowser.chat.service.ChatService;

public class ChatController {
    @FXML
    private TextArea typingField;

    @FXML
    private TextArea messagesField;

    @FXML
    private Label connectionLabel;

    private ChatService chatService = ChatService.getInstance();



    private Client client;

    public void initialize() {
        ChatService chatService = ChatService.getInstance();
        connectionLabel.setText(chatService.getLabelText());
        for(String msg: chatService.getMessages()) {
            messagesField.appendText(msg+"\n");
        }

        chatService.getMessages().addListener((javafx.collections.ListChangeListener<String>) change -> {
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
    @FXML
    private void close() {
        chatService.closeWindow();
    }
    @FXML
    private void disconnectClient() {
        chatService.disconnectClient();
    }
}
