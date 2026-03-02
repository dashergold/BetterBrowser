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
       createNewTab();
       System.out.println("program running");
   }
   public void createNewTab() {
       try {
           System.out.println("trying to load fxml");
           FXMLLoader loader = new FXMLLoader(getClass().getResource("tab.fxml"));
           Parent root =  loader.load();
           System.out.println("Fxml loaded");

           TabController controller = loader.getController();

           Tab tab = new Tab("New tab");
           tab.setContent(root);

           tabPane.getTabs().add(tab);
           tabPane.getSelectionModel().select(tab);
       }
       catch(IOException e) {
           e.printStackTrace();
       }

   }

}
