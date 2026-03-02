package Tests;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class TabPaneTest extends Application {
    static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();

        tabPane.getTabs().add(newTabButton(tabPane));

        stage.setScene(new Scene(tabPane));
        stage.show();
    }
    private Tab newTabButton(TabPane tabPane) {
        Tab addTab = new Tab("+");
        addTab.setClosable(false);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if(newTab == addTab) {
                tabPane.getTabs().add(tabPane.getTabs().size() - 1, new Tab("New Tab"));
                tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
            }
        });
        return addTab;
    }
}
