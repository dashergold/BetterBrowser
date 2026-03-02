package org.webbrowser.browser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;

public class BrowserController {
   @FXML
    private TabPane tabPane;
   @FXML
    public void initialize() {
       Tab initalTab = createNewTab();
       tabPane.getTabs().add(initalTab);
       tabPane.getTabs().add(newTabButton());
       tabPane.getSelectionModel().select(initalTab);
       System.out.println("program running");
   }
   public Tab createNewTab() {
       try {
           System.out.println("trying to load fxml");
           FXMLLoader loader = new FXMLLoader(getClass().getResource("tab.fxml"));
           Parent root =  loader.load();
           System.out.println("Fxml loaded");

           TabController controller = loader.getController();


           Tab tab = new Tab("New tab");
           tab.setContent(root);
           controller.setTab(tab);

           return tab;
       }

       catch(IOException e) {
           e.printStackTrace();
       }
    return null;
   }
   private Tab newTabButton() {
       Tab addTab = new Tab("+");
       addTab.setClosable(false);
       tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
           if(newTab == addTab) {
               tabPane.getTabs().add(tabPane.getTabs().size() - 1, createNewTab());
               tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
           }
       });
       return addTab;
   }

}
