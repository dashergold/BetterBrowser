package org.webbrowser.browser;

/**
 * Represents a history entry to be stored in a {@link javafx.scene.control.TableView}.
 * <p>
 * This class holds information such as date and url.
 */
public class HistoryEntry {
    /**
     * The entries date.
     */
    private final String date;
    /**
     * the entries URL.
     */
    private final String url;

    /**
     * Creates a history entry.
      * @param date the entry date.
     * @param url the entry URL.
     */
    public HistoryEntry(String date, String url) {
        this.date = date;
        this.url = url;
    }

    /**
     * Returns the date.
     * @return the date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the URL.
     * @return the URL.
     */
    public String getUrl() {
        return url;
    }
}
