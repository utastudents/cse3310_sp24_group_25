package uta.cse3310;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private Long runningTime;
    private Integer gamesInProgress;
    private Map<Player, Integer> playerScores; // Map to store scores for each player

    public Statistics() {
        this.runningTime = 0L;
        this.gamesInProgress = 0;
        this.playerScores = new HashMap<>();
    }

    public Long getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(Long runningTime) {
        this.runningTime = runningTime;
    }

    public Integer getGamesInProgress() {
        return gamesInProgress;
    }

    public void setGamesInProgress(Integer gamesInProgress) {
        this.gamesInProgress = gamesInProgress;
    }

    // Method to update the score for a player
    public void updateScore(Player player, int scoreToAdd) {
        this.playerScores.merge(player, scoreToAdd, Integer::sum); // Use merge to handle null keys safely
    }

    // Method to get the score for a player
    public Integer getScore(Player player) {
        return this.playerScores.getOrDefault(player, 0);
    }
}
