package org.webbrowser.browser.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import org.webbrowser.browser.controller.ConsoleController;

import java.io.IOException;

/**
 * Service responsible for managing a single browser tabs WebView.
 * <p>
 * Provides methods for:
 * <ul>
 *     <li>Loading URLs.</li>
 *     <li>Navigation (back, forward, reload).</li>
 *     <li>Accessing the WebEngine and WebHistory.</li>
 * </ul>
 *
 * @author Axel
 * @since 2026
 */
public class TabService {
    /**
     * The engine for rendering pages.
     */
    private final WebEngine engine;
    /**
     * The history for the tab.
     */
    private final WebHistory history;

    /**
     * Creates a {@link TabService} for the given {@link WebView}.
     * @param webView the web view associated with this tab.
     */
    public TabService(WebView webView) {
        this.engine = webView.getEngine();
        this.history = engine.getHistory();
    }

    /**
     * Loads a URL or search query in the {@link WebEngine}.
     * <p>
     * Automatically adds "https://" in front of the input if it doesn't start with "http".
     * @param input the URL or the search query to load.
     */
    public void loadURL(String input) {
        String url = input.trim();
        if(!url.startsWith("http")) {
            url = "https://" + url;
        }
        try {
            engine.load(url);
        } catch (Exception e ) {
            System.out.println("Error occurred when fetching url " + url);
        }
    }

    /**
     * Navigates one step forward in this tabs search history.
     */
    public void back() {
        history.go(-1);
    }

    /**
     * Navigates one step backwards in this tabs search history.
     */
    public void forward() {
        history.go(1);
    }

    /**
     * Reloads the current page in this tab.
     */
    public void reload() {
        engine.reload();
    }

    /**
     * Sets the bottom of the tab to the console window.
     * @param tabRootPane The tabs root pane.
     */
    public void openDeveloperConsole(BorderPane tabRootPane) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/browser/console.fxml"));
        Parent console = null;
        try {
            console = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        tabRootPane.setBottom(console);
        ConsoleController controller = loader.getController();
        controller.setRootPane(tabRootPane);
        try {
            controller.setHtmlArea(engine.getLocation());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the {@link WebEngine} of this tab.
     * @return the web engine.
     */
    public WebEngine getEngine() {
        return engine;
    }

    /**
     * Returns the {@link WebHistory} of this tab.
     * @return the history.
     */
    public WebHistory getHistory() {
        return history;
    }
}
