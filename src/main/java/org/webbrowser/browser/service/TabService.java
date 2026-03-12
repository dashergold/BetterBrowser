package org.webbrowser.browser.service;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class TabService {
    private final WebEngine engine;
    private final WebHistory history;

    public TabService(WebView webView) {
        this.engine = webView.getEngine();
        this.history = engine.getHistory();
    }

    public WebEngine getEngine() {
        return engine;
    }

    public WebHistory getHistory() {
        return history;
    }

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
    public void back() {
        history.go(-1);
    }
    public void forward() {
        history.go(1);
    }
    public void reload() {
        engine.reload();
    }


}
