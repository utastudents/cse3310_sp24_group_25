package uta.cse3310;

import org.java_websocket.WebSocket;

public class Player {
    private String name;
    private WebSocket connection; // Add a WebSocket field to store the connection

    // Update the constructor to accept both name and connection
    public Player(String name, WebSocket connection) {
        this.name = name;
        this.connection = connection;
    }

    public String getName() {
        return name;
    }

    public WebSocket getConnection() {
        return connection;
    }
}
