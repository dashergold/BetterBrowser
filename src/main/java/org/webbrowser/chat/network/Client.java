package org.webbrowser.chat.network;

import javafx.application.Platform;
import org.webbrowser.chat.service.ChatService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
    private static final Client instance = new Client();

    private String host;
    private int port;
    private boolean running = true;

    private PrintWriter out;
    private BufferedReader in;

    private Socket socket;

    private Client() {}
    public static Client getInstance() {
        return instance;
    }
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public void sendMessage(String message) {
        if(out != null) {out.println(message);}
    }

    @Override
    public void run() {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while(running && (message = in.readLine()) != null) {
                String finalMessage = message;
                Platform.runLater(() -> {
                    ChatService.getInstance().addMessage(finalMessage);
                });
            }
        } catch (UnknownHostException e) {
            if(running) {throw new RuntimeException(e);}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            close();
        }
    }
    public void close() {
        running = false;
        try {
            if(in != null) {
                in.close();
            }
            if(out != null) {
                out.close();
            }
            if(socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
