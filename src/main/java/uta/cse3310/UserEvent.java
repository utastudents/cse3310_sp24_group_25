package uta.cse3310;
// User events are sent from the webpage to the server
//XSolis

public class UserEvent {
    int GameId; // the game ID on the server
    PlayerType PlayerIdx; // either PLayer 1, 2, 3, or 4
    WordSelection selection = new WordSelection(0,0,0,0,0,0,0,0);//Word Selected


    UserEvent(int _GameId, PlayerType _PlayerIdx, WordSelection _selection) {
        GameId = _GameId;
        PlayerIdx = _PlayerIdx;
        selection = _selection; 
    }
}
