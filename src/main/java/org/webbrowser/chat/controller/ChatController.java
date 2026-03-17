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

/**
 * Controller for the chat UI.
 * <p>
 * This class handles:
 * <ul>
 *     <li>Displaying messages in the chat window.</li>
 *     <li>Typing and sending messages.</li>
 *     <li>Listening for new messages.</li>
 *     <li>Disconnecting the client after clicking the disconnect button</li>
 * </ul>
 * @author Axel
 * @since 2026
 */
public class ChatController {
    /**
     * Service responsible for managing the chats functionality.
     */
    private final ChatService chatService = ChatService.getInstance();
    /**
     * Interactable text area where a user enters their message.
     */
    @FXML
    private TextArea typingField;
    /**
     * Non-interactable text area where chat messages are displayed.
     */
    @FXML
    private TextArea messagesField;
    /**
     * Label for displaying information about the current server.
     */
    @FXML
    private Label connectionLabel;
    /**
     * Listener listening for changes in a list of messages.
     */
    private javafx.collections.ListChangeListener<String> messageListener;
    /**
     * The current client.
     */
    private Client client;

    /**
     * Initializes the chat window.
     * <p>
     * Clears the messages field and populates it with existing messages.
     * Sets the connection label text and attaches a listener to update messages in real time.
     */
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

    /**
     * Assigns the chat client for this controller.
     * @param client the {@link Client} to use.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Sends the messages typed in the {@link ChatController#typingField} via {@link ChatService}.
     */
    @FXML
    private void sendMessage() {
        String msg = typingField.getText();
        if(msg == null || msg.isEmpty()) {
            return;
        }
        chatService.sendMessage(msg);

        typingField.clear();
    }

    /**
     * Closes the chat window.
     */
    @FXML
    private void close() {
        chatService.closeWindow();
    }

    /**
     * Disconnects the client from the chat.
     */
    @FXML
    private void disconnectClient() {
        chatService.getMessages().removeListener(messageListener);
        chatService.disconnectClient();
    }

    /**
     * handles updates to the message list and appends newly added messages to the messages field.
     * @param change the list change event.
     */
    private void handleMessageChange(javafx.collections.ListChangeListener.Change<? extends String> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for (String msg : change.getAddedSubList()) {
                    messagesField.appendText(msg + "\n");
                }
            }
        }
    }
}
