package org.webbrowser.chat.network;

import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    private static final ServerManager instance = new ServerManager();

    private final List<Server> servers = new ArrayList<>();
    private ServerManager() {}

    public static ServerManager getInstance() {
        return instance;
    }
    public Server createServer(String host, int port, Runnable runnable) {
        Server server = new Server(host, port, runnable);
        this.servers.add(server);
        return server;
    }
    public void shutdownServers() {
        for(Server s: servers) {
            try {
                s.close();
            } catch (Exception e ) {
                continue;
            }

        }

    }
}
