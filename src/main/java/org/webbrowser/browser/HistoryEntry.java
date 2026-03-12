package org.webbrowser.browser;

public class HistoryEntry {
    private final String date;
    private final String url;
    public HistoryEntry(String date, String url) {
        this.date = date;
        this.url = url;
    }
    public String getDate() {
        return date;
    }
    public String getUrl() {
        return url;
    }
}
