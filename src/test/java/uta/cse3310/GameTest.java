package uta.cse3310;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class GameTest {
    private Game game;
    private Statistics mockStats;

    @Before
    public void setUp() {
        mockStats = mock(Statistics.class);
        game = new Game(1, mockStats);
    }

    @Test
    public void testGameInitialization() {
        assertEquals(1, game.getGameId());
        assertFalse(game.isGameStarted());
        assertEquals(0, game.getNumberOfPlayers());
    }

    @Test
    public void testAddPlayer() {
        Player mockPlayer = mock(Player.class);
        game.addPlayer(PlayerType.PLAYER1, mockPlayer);
        assertEquals(1, game.getNumberOfPlayers());

        // Adding the same type of player should not increase the count
        game.addPlayer(PlayerType.PLAYER1, mockPlayer);
        assertEquals(1, game.getNumberOfPlayers());

        // Adding different players up to the limit of 4
        game.addPlayer(PlayerType.PLAYER2, mock(Player.class));
        game.addPlayer(PlayerType.PLAYER3, mock(Player.class));
        game.addPlayer(PlayerType.PLAYER4, mock(Player.class));
        assertEquals(4, game.getNumberOfPlayers());

        // Trying to add more should not work
        game.addPlayer(PlayerType.PLAYER1, mock(Player.class));
        assertEquals(4, game.getNumberOfPlayers());
    }

    @Test
    public void testStartGameWithInsufficientPlayers() {
        game.startGame();
        assertFalse(game.isGameStarted());

        // Add only one player and try starting the game
        game.addPlayer(PlayerType.PLAYER1, mock(Player.class));
        game.startGame();
        assertFalse(game.isGameStarted());
    }

    @Test
    public void testStartGameWithSufficientPlayers() {
        game.addPlayer(PlayerType.PLAYER1, mock(Player.class));
        game.addPlayer(PlayerType.PLAYER2, mock(Player.class));
        game.startGame();
        assertTrue(game.isGameStarted());
    }

    @Test
    public void testAdvanceTurnSkipsNoPlayer() {
        game.addPlayer(PlayerType.PLAYER1, mock(Player.class));
        game.addPlayer(PlayerType.PLAYER2, mock(Player.class));
        game.addPlayer(PlayerType.PLAYER3, mock(Player.class));
        game.startGame();

        assertEquals(PlayerType.PLAYER1, game.getCurrentTurn());
        game.advanceTurn();
        assertEquals(PlayerType.PLAYER2, game.getCurrentTurn());
        game.advanceTurn();
        assertEquals(PlayerType.PLAYER3, game.getCurrentTurn());
        game.advanceTurn();
        assertEquals(PlayerType.PLAYER1, game.getCurrentTurn());  // Ensure it cycles back to PLAYER1
    }



    @Test
    public void testNoPlayerNeverSelected() {
        game.addPlayer(PlayerType.PLAYER1, mock(Player.class));
        game.addPlayer(PlayerType.PLAYER2, mock(Player.class));
        game.startGame();
        for (int i = 0; i < 10; i++) { // Arbitrary number of advances to check cycling
            game.advanceTurn();
            assertNotEquals(PlayerType.NOPLAYER, game.getCurrentTurn());
        }
    }

}
