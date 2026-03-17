package org.webbrowser.app;

import javafx.application.Application;
import org.webbrowser.browser.BrowserApplication;

/**
 * Entry point for launching the web browser application.
 * <p>
 * This class bootstraps the JavaFX runtime by delegating execution to {@link BrowserApplication}.
 * @author Axel
 * @since 2026
 */
public class Launcher {
    /**
     * Main method used to launch the JavaFX application.
     * @param args arguments passed to the application if launched by command line.
     */
    public static void main(String[] args) {
        Application.launch(BrowserApplication.class, args);
    }
}
