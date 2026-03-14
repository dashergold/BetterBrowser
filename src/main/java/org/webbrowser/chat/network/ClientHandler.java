package org.webbrowser.chat.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket socket;
    private Server server;
    private PrintWriter out;
    private BufferedReader in;
    private boolean running = true;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void sendMessage(String message) {
        if(out != null) {
            out.println(message);
        }
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String message;
            while(running && (message = in.readLine()) != null) {
                server.broadcast(message);
            }
        } catch (IOException e) {
            if(running) {throw new RuntimeException(e);}
        } finally {
            close();
        }
    }
    public void close() {
        running = false;
        try {
            if(socket != null && !socket.isClosed()) {
                socket.close();
            }
            if(in != null) {
                in.close();
            }
            if(out != null) {
                out.close();
            }
        } catch (Exception e) {

        }
    }
}
