package uta.cse3310;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.*;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import uta.cse3310.App.Message;

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
        try {
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
            String messageType = jsonObject.has("type") ? jsonObject.get("type").getAsString() : null;
    
            if (messageType == null) {
                throw new IllegalArgumentException("Message type is required");
            }
    
            switch (messageType) {
                case "username":
                    handleUsername(conn, jsonObject);
                    break;
                case "play_game":
                    handleGameStart(conn, jsonObject);
                    break;
                    case "chat_message":
                String chat = jsonObject.get("text").getAsString();
                    broadcastChatMessage(chat);
                    break;
                default:
                    System.out.println("Received unknown message type: " + messageType);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Failed to handle message: " + e.getMessage());
            if (conn != null) {
                conn.send(new Gson().toJson(new Message("error", "Invalid message format")));
            }
        }
    }
    private void handleUsername(WebSocket conn, JsonObject jsonObject) {
        if (!jsonObject.has("name")) {
            conn.send(new Gson().toJson(new Message("error", "Username is required")));
            return;
        }
    
        String username = jsonObject.get("name").getAsString().trim();
    
        if (username.isEmpty()) {
            conn.send(new Gson().toJson(new Message("error", "Username cannot be empty")));
            return;
        }
    
        if (username.contains(" ") || username.length() > 20) {
            conn.send(new Gson().toJson(new Message("error", "Username must be one word and no more than 20 characters")));
            return;
        }
    
        // Check if the username is already taken by iterating over the existing user data
        if (userdata.values().stream().anyMatch(u -> u.equalsIgnoreCase(username))) {
            conn.send(new Gson().toJson(new Message("error", "Username already taken, please choose another")));
            return;
        }
    
        // Store the new username linked to the WebSocket's remote socket address
        String userKey = conn.getRemoteSocketAddress().toString();
        userdata.put(userKey, username);
        userConnections.put(conn, username);
    
        System.out.println("Username received and stored: " + username);
    
        // Notify the new user of successful registration
        conn.send(new Gson().toJson(new Message("info", "Username accepted: " + username)));
    
        // Update all clients with the new list of users
        broadcastUserList();
    }

     private void handleGameStart(WebSocket conn, JsonObject jsonObject) throws FileNotFoundException, IOException {
        String username = jsonObject.get("name").getAsString();
        if (ActiveGames.size() < 5) {
            Statistics gameStats = new Statistics();
            int newGameId = ActiveGames.size() + 1;
            Game game = new Game(newGameId, gameStats);
            ActiveGames.add(game);

            Player newPlayer = new Player(username, conn);
            game.addPlayer(PlayerType.PLAYER1, newPlayer);

            // Retrieve grid from WordGrid class
            WordGrid wordGrid = new WordGrid(1); // Example grid choice
            String gridData = wordGrid.getGridAsString(); // Convert grid to a single string if needed

            JsonObject gridMessage = new JsonObject();
            gridMessage.addProperty("type", "game_start");
            gridMessage.addProperty("grid", gridData);
            conn.send(new Gson().toJson(gridMessage));
        } else {
            conn.send(new Gson().toJson(new Message("error", "Maximum number of games reached.")));
        }
    }

    // Additional methods...




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
        String userListJson = gson.toJson(new Message("user_list_update", new ArrayList<>(userdata.values())));
        broadcast(userListJson); // This should broadcast to all connected users
    }


    public class Message {
        private String type;
        private List<String> users;  // Using List instead of ArrayList directly for flexibility

        // Constructor for messages with user lists
        public Message(String type, List<String> users) {
            this.type = type;
            this.users = users;
        }

        // Overloaded constructor for simple messages
        public Message(String type, String message) {
            this.type = type;
            this.users = new ArrayList<>();
            this.users.add(message);  // Wrap the single message in a list
        }
    }
    private void broadcastChatMessage(String message) {
        Gson gson = new GsonBuilder().create();
        String msgJson = gson.toJson(new Message("chat_message", message));
        broadcast(msgJson);  // This method sends the message to all connected clients
    }



    public static void main(String[] args) {
        // Set up the HTTP server
        int port = 9025;
        HttpServer httpServer = new HttpServer(port, "./html");
        httpServer.start();
        System.out.println("HTTP Server started on port: " + port);

        // Create and start the WebSocket server
        port = 9125;
        App websocketServer = new App(port);
        websocketServer.start();
        System.out.println("WebSocket Server started on port: " + port);
    }
}