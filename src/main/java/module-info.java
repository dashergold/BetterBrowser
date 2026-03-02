module org.webbrowser.browser {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.web;

    opens org.webbrowser.browser to javafx.fxml;
    exports org.webbrowser.browser;
}