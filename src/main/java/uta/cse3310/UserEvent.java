package uta.cse3310;
// User events are sent from the webpage to the server

public class UserEvent {
    int GameId; // the game ID on the server
    PlayerType PlayerIdx; // either PLayer 1, 2, 3, or 4
    //Word Selected

    UserEvent() {

    }

    UserEvent(int _GameId, PlayerType _PlayerIdx) {
        GameId = _GameId;
        PlayerIdx = _PlayerIdx;
        //word selected
    }
}
