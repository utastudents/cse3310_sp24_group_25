package uta.cse3310;

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
        String messageType = jsonObject.get("type").getAsString();

        switch (messageType) {
            case "username":
                String username = jsonObject.get("name").getAsString();
                userdata.put(conn.getRemoteSocketAddress().toString(), username);
                System.out.println("Username received and stored: " + username);
                // Remove the individual send as the broadcast will include the new user as well
                broadcastUserList(); // This will send the updated list to all users
                break;
            case "play_game":
                handleGameStart(conn, jsonObject);
                break;
            // Additional cases for other message types like 'game_action', 'chat_message', etc.
            default:
                System.out.println("Received unknown message type: " + messageType);
                break;
        }
    }

    private void handleGameStart(WebSocket conn, JsonObject jsonObject) {
        String username = jsonObject.get("name").getAsString();  // Assuming the username is part of the message
        if (ActiveGames.size() < 5) {
            Statistics gameStats = new Statistics();
            int newGameId = ActiveGames.size() + 1;
            Game game = new Game(newGameId, gameStats);
            ActiveGames.add(game);

            Player newPlayer = new Player(username, conn);  // Create a new player with the extracted name and WebSocket connection
            game.addPlayer(PlayerType.PLAYER1, newPlayer);  // Assume PlayerType.PLAYER1 is correct, adjust as necessary

            // Notify player that the game has started
            conn.send(new Gson().toJson(new Message("game_start", "Your game has started.")));
        } else {
            // Notify player that no more games can be started
            conn.send(new Gson().toJson(new Message("error", "Maximum number of games reached.")));
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