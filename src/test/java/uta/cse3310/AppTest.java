package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.java_websocket.WebSocket;
import org.mockito.Mockito;
import com.google.gson.Gson;

public class AppTest extends TestCase {

    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testValidateUsername() {
        App app = new App(9002);
        Gson gson = new Gson();
        WebSocket webSocketMock = Mockito.mock(WebSocket.class);

        String username = "TestUser";
        Mockito.when(webSocketMock.getRemoteSocketAddress().toString()).thenReturn("192.168.0.1");

        app.onOpen(webSocketMock, null);
        app.onMessage(webSocketMock, "{\"type\":\"username\", \"name\":\"" + username + "\"}");

        assertTrue("Username should be valid and accepted", app.userdata.containsValue(username));
    }

    public void testHandleGameStart() {
        App app = new App(9002);
        Gson gson = new Gson();
        WebSocket webSocketMock = Mockito.mock(WebSocket.class);

        String username = "TestUser";
        Mockito.when(webSocketMock.getRemoteSocketAddress().toString()).thenReturn("192.168.0.1");

        app.onOpen(webSocketMock, null);
        app.onMessage(webSocketMock, "{\"type\":\"username\", \"name\":\"" + username + "\"}");
        app.onMessage(webSocketMock, "{\"type\":\"play_game\", \"name\":\"" + username + "\"}");

        assertTrue("Game should be started after valid request", app.ActiveGames.size() > 0);
    }
}