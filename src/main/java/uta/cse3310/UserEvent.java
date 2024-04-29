package uta.cse3310;

import java.util.List;

public class UserEvent {
    private EventType type;
    private PlayerType playerType;
    private List<Integer> wordCoordinates;

    public UserEvent(EventType type, PlayerType playerType, List<Integer> wordCoordinates) {
        this.type = type;
        this.playerType = playerType;
        this.wordCoordinates = wordCoordinates;
    }

    public EventType getType() {
        return type;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public List<Integer> getWordCoordinates() {
        return wordCoordinates;
    }
}

