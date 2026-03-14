package org.webbrowser.chat.network;

import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    private static final ServerManager instance = new ServerManager();

    private Server server;
    private ServerManager() {}

    public static ServerManager getInstance() {
        return instance;
    }
    public Server createServer(String host, int port, Runnable runnable) {
        Server server = new Server(host, port, runnable);
        this.server = server;
        return server;
    }
    public void shutdownServer() {

        try {
            server.close();
        } catch (Exception e ) {
            e.printStackTrace();
        }

    }
    public Server getServer() {
        return server;
    }
}
