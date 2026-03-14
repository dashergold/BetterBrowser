package org.webbrowser.chat.controller;

import javafx.fxml.FXML;
import org.webbrowser.accounts.AccountWindow;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.browser.controller.BrowserController;
import org.webbrowser.browser.service.BrowserService;

import java.io.IOException;

public class ChatPromptController {

    @FXML
    private void signIn() {
        try {
            new AccountWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
