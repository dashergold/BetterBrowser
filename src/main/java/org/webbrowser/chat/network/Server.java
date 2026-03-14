package org.webbrowser.chat.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{
    private final List<String> messageHistory = new ArrayList<>();
    private String host;
    private int port;
    private Runnable onReady;

    private ServerSocket serverSocket;

    private boolean running = true;

    private static List<ClientHandler> clients = new ArrayList<>();

    public Server(String host, int port, Runnable onReady)  {
        this.host = host;
        this.port = port;
        this.onReady = onReady;
    }
    public synchronized void addMessage(String message) {
        messageHistory.add(message);
    }
    public synchronized List<String> getMessageHistory(){
        return new ArrayList<>(messageHistory);
    }
    public void broadcast(String message) {
        for(ClientHandler c: clients) {
            c.sendMessage(message);
        }
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(port, 50, InetAddress.getByName(host));

            if(onReady != null) {
                onReady.run();
            }

            while(running) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, this);

                clients.add(handler);
                Thread th = new Thread(handler);
                th.setDaemon(true);
                th.start();
            }


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IO error: "+ e.getMessage());
        } finally {
            close();
        }

    }
    public void close() {

        try {
            if(serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            for(ClientHandler c: clients) {
                c.close();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        running = false;
    }


}
