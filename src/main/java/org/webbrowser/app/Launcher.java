package org.webbrowser.app;

import javafx.application.Application;
import org.webbrowser.browser.BrowserApplication;
import org.webbrowser.browser.controller.HistoryController;

/**
 * @author Axel
 * @since 2026
 */
public class Launcher {
    static void main(String[] args) {


        Application.launch(BrowserApplication.class, args);

    }
}
