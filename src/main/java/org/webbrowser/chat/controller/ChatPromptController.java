package org.webbrowser.chat.controller;

import javafx.fxml.FXML;
import org.webbrowser.accounts.AccountWindow;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.browser.controller.BrowserController;
import org.webbrowser.browser.service.BrowserService;
import org.webbrowser.chat.service.ChatService;

import java.io.IOException;

/**
 * Controller for the chat prompt window.
 * <p>
 * Provides actions to:
 * <ul>
 *     <li>Open the account sign in window.</li>
 *     <li>Close the chat prompt.</li>
 * </ul>
 *
 * @author Axel
 * @since 2026
 */
public class ChatPromptController {
    /**
     * Service responsible for chat management.
     */
    private ChatService chatService = ChatService.getInstance();

    /**
     * Opens the account sign in window.
     */
    @FXML
    private void signIn() {
        try {
            new AccountWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the window.
     */
    @FXML
    private void close() {
        chatService.closeWindow();
    }
}
