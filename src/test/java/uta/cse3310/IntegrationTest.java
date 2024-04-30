package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.mockito.Mockito;

public class IntegrationTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public IntegrationTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(IntegrationTest.class);
    }

    /**
     * Test a single game by simulating interactions and verifying the outcomes.
     * @param G Game to test
     */
    public void singleGame(Game G) {
        // Assume that Game has methods like `addPlayer` and `startGame`
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getName()).thenReturn("Test Player");

        G.addPlayer(PlayerType.PLAYER1, player);
        G.startGame();

        // Assuming Game has a method to check if it's started correctly
        assertTrue("Game should be started", G.isGameStarted());
        assertEquals("Game should have exactly one player", 1, G.getNumberOfPlayers());
    }

    public void testOneGame() {
        Game G = new Game(0, new Statistics());
        singleGame(G);
    }

    public void testTwoGames() {
        Game G0 = new Game(0, new Statistics());
        Game G1 = new Game(1, new Statistics());
        singleGame(G0);
        singleGame(G1);
    }
}