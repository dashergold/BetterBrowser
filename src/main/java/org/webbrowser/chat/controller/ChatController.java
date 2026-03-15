package org.webbrowser.chat.controller;

import javafx.collections.ListChangeListener;
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

    private javafx.collections.ListChangeListener<String> messageListener;




    private Client client;

    public void initialize() {
        messagesField.clear();
        ChatService chatService = ChatService.getInstance();
        connectionLabel.setText(chatService.getLabelText());
        for(String msg: chatService.getMessages()) {
            messagesField.appendText(msg+"\n");
        }
        messageListener = this::handleMessageChange;
        chatService.getMessages().addListener(messageListener);
    }
    public void setClient(Client client) {
        this.client = client;
    }

    private void handleMessageChange(javafx.collections.ListChangeListener.Change<? extends String> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for (String msg : change.getAddedSubList()) {
                    messagesField.appendText(msg + "\n");
                }
            }
        }
    }

    @FXML
    private void sendMessage() {
        String msg = typingField.getText();
        if(msg == null || msg.isEmpty()) {
            return;
        }
        chatService.sendMessage(msg);

        typingField.clear();
    }
    @FXML
    private void close() {
        chatService.closeWindow();
    }
    @FXML
    private void disconnectClient() {
        chatService.getMessages().removeListener(messageListener);
        chatService.disconnectClient();
    }
}
