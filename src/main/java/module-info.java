module org.webbrowser.browser {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.web;
    requires java.sql;
    requires org.jdom2;
    requires jakarta.activation;
    requires jakarta.mail;

    opens org.webbrowser.browser to javafx.fxml;
    opens org.webbrowser.settings to javafx.fxml;
    opens org.webbrowser.accounts to javafx.fxml;

    exports org.webbrowser.browser;
    exports org.webbrowser.settings;
    exports org.webbrowser.accounts;
    exports Tests;
}
