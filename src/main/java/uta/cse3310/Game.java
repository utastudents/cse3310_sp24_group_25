package uta.cse3310;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {

    private HashMap<PlayerType, Player> players; // Maps player types to player objects
    private PlayerType currentTurn; // Tracks whose turn it is
    private int gameId;
    private List<List<Character>> gameBoard; // Represents the game board as a list of lists of characters
    private boolean isGameOver;
    private Statistics stats; // Class to manage game statistics
    private boolean isGameStarted;

    public Game(int gameId, Statistics stats) {
        this.gameId = gameId;
        this.stats = stats;
        this.players = new HashMap<>();
        this.isGameOver = false;
        this.isGameStarted = false;
        initializeBoard();
    }

    // Initialize the game board with random letters or from a predefined set
    private void initializeBoard() {
        gameBoard = new ArrayList<>();
        // Example initialization, this should be replaced with actual logic to generate the board
        for (int i = 0; i < 10; i++) { // Assuming a 10x10 grid
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                row.add((char) ('A' + Math.random() * 26)); // Random letters
            }
            gameBoard.add(row);
        }
    }

    public void addPlayer(PlayerType playerType, Player player) {
        if (!players.containsKey(playerType) && players.size() < 4) { // Limiting to 4 players
            players.put(playerType, player);
        }
    }

    public void startGame() {
        if (players.size() >= 2) { // Minimum two players to start the game
            currentTurn = PlayerType.PLAYER1; // Explicitly start with PLAYER1 or choose based on some logic
            isGameStarted = true;
        } else {
            System.out.println("Not enough players to start the game.");
        }
    }


    public void update(UserEvent event) {
        if (!isGameOver) {
            // Example event handling: player tries to select a word on the board
            if (event.getType() == EventType.SELECT_WORD && currentTurn == event.getPlayerType()) {
                if (validateSelection(event.getWordCoordinates())) {
                    stats.updateScore(players.get(currentTurn), calculateScore(event.getWordCoordinates()));
                    advanceTurn();
                }
            }
        }
    }

    private boolean validateSelection(List<Integer> coordinates) {
        // Validation logic to check if the selected word is correct
        return true; // Placeholder
    }

    private int calculateScore(List<Integer> coordinates) {
        // Score calculation based on the word length or other criteria
        return coordinates.size(); // Placeholder
    }

    void advanceTurn() {
        // Increment the ordinal, wrapping around at the end of the enumeration.
        int nextOrdinal = (currentTurn.ordinal() + 1) % PlayerType.values().length;

        // Loop until a valid player is found or all possibilities are exhausted.
        int attempts = 0;
        while (!players.containsKey(PlayerType.values()[nextOrdinal]) && attempts < PlayerType.values().length) {
            nextOrdinal = (nextOrdinal + 1) % PlayerType.values().length;
            attempts++;
        }

        // Update the current turn only if a valid player was found.
        if (players.containsKey(PlayerType.values()[nextOrdinal])) {
            currentTurn = PlayerType.values()[nextOrdinal];
        } else {
            // Handle error state if no valid player is found - ideally this should not happen
            currentTurn = PlayerType.NOPLAYER;
        }
    }




    public PlayerType getCurrentTurn() {
        return currentTurn;
    }

    public void tick() {
        // This method could handle timed aspects of the game, like a countdown timer
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    // Utility method to format and print the game board, for debugging or game updates
    public void printGame() {
        for (List<Character> row : gameBoard) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    public int getGameId() {
        return gameId;
    }
    public int getNumberOfPlayers() {
        return players.size();
    }
    public boolean isGameStarted() {
        return isGameStarted;
    }

}
