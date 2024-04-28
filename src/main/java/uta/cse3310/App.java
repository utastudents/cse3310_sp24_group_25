package uta.cse3310;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App extends WebSocketServer {
    Vector<Game> ActiveGames = new Vector<>();
    HashMap<String, String> userdata = new HashMap<>();
    HashMap<WebSocket, String> userConnections = new HashMap<>();
    
    public App(int port) {
        super(new InetSocketAddress(port));
    }

    public App(InetSocketAddress address) {
        super(address);
    }

    public App(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");
        conn.send("Welcome! Please send your username.");
        broadcastUserList();
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn + " has closed");
        broadcastUserList();
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(conn + ": " + message);

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        if (jsonObject.has("type") && jsonObject.get("type").getAsString().equals("username")) {
            String Username = jsonObject.get("name").getAsString();
            userdata.put(conn.getRemoteSocketAddress().toString(), Username);
            System.out.println("Username received and stored: " + Username);
            broadcastUserList();
            conn.send(new Gson().toJson(new Message("userListUpdate", new ArrayList<>(userdata.values()))));
        } else {
            // Handle game-related messages here
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        System.out.println(conn + ": " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            // Specific websocket-related errors handled here
        }
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
    }

    private void broadcastUserList() {
        Gson gson = new GsonBuilder().create();
        String userListJson = gson.toJson(new ArrayList<>(userdata.values()));
        broadcast(userListJson);
    }

    public class Message {
        private String type;
        private ArrayList<String> users;

        public Message(String type, ArrayList<String> users) {
            this.type = type;
            this.users = users;
        }
    }

    public static void main(String[] args) {
        // Set up the HTTP server
        int port = 9080;
        HttpServer httpServer = new HttpServer(port, "./html");
        httpServer.start();
        System.out.println("HTTP Server started on port: " + port);

        // Create and start the WebSocket server
        port = 9880;
        App websocketServer = new App(port);
        websocketServer.start();
        System.out.println("WebSocket Server started on port: " + port);
    }
}