package org.webbrowser.browser;

import javafx.application.Application;

/**
 * @author Axel
 * @since 2026
 */
public class Launcher {
    static void main(String[] args) {
        HistoryController.connectToDB();
        Application.launch(BrowserApplication.class, args);

    }
}
