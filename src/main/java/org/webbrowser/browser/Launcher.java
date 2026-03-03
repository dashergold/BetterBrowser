package org.webbrowser.browser;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        HistoryController.connectToDB();
        Application.launch(BrowserApplication.class, args);

    }
}
