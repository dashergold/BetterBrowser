package org.webbrowser.browser.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.browser.controller.TabController;

import java.io.IOException;

public class BrowserService {
    private static final BrowserService instance = new BrowserService();
    private HistoryService historyService = HistoryService.getInstance();
    private TabPane tabPane;

    private BrowserService() {}

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }
    public void addTab() {
        Tab tab = createNewTab();
        tabPane.getTabs().addAll(tab,newTabButton());
    }
    public void addHistoryTab() {
        Tab historyTab = historyService.createHistoryTab();
        tabPane.getTabs().add(tabPane.getTabs().size() - 1,historyTab);
        tabPane.getSelectionModel().select(tabPane.getTabs().size() -2);

    }
    private Tab createNewTab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/tab.fxml"));
            Parent root = loader.load();

            TabController controller = loader.getController();
            Tab tab = new Tab("New tab");
            tab.setContent(root);
            controller.setTab(tab);

            return tab;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Tab newTabButton() {
        Tab addTab = new Tab("+");
        addTab.setClosable(false);
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab)-> {
            if(newTab == addTab) {
                tabPane.getTabs().add(tabPane.getTabs().size() -1, createNewTab());
                tabPane.getSelectionModel().select(tabPane.getTabs().size()-2);
            }
        });
        return addTab;
    }

    public static BrowserService getInstance() {
        return instance;
    }

}
