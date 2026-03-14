package org.webbrowser.chat.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.webbrowser.browser.controller.BrowserController;
import org.webbrowser.chat.network.Client;
import org.webbrowser.chat.network.ClientHandler;
import org.webbrowser.chat.network.Server;
import org.webbrowser.chat.network.ServerManager;
import org.webbrowser.chat.service.ChatService;

import java.io.IOException;

public class ChatServerCreationController {
    private final ChatService chatService = ChatService.getInstance();
    @FXML
    private TextField joinIpField;
    @FXML
    private TextField joinPortField;
    @FXML
    private TextField createIpField;
    @FXML
    private TextField createPortField;

    private Client client;

    private BorderPane rootPane;




    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    @FXML
    private void joinServer() {
        String ip = joinIpField.getText();
        int port = Integer.parseInt(joinPortField.getText());

        client = new Client(ip, port);
        Thread cThread = new Thread(client);
        cThread.setDaemon(true);
        cThread.start();
        chatService.setClient(client);
        chatService.openChat(rootPane);

    }
    @FXML
    private void createServer() {
        String ip = createIpField.getText();
        int port = Integer.parseInt(createPortField.getText());

        Server server = ServerManager.getInstance().createServer(ip, port, () -> {

            client = new Client(ip, port);
            chatService.setClient(client);
            Thread cThread = new Thread(client);
            cThread.setDaemon(true);
            cThread.start();
        });


        Thread sThread = new Thread(server);
        sThread.setDaemon(true);
        sThread.start();

        chatService.setServer(server);

        chatService.openChat(rootPane);

    }


}
