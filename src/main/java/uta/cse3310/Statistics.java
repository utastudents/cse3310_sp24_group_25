package uta.cse3310;

public class Statistics {
    // this class stores global statistics of the
    // program
    private Long RunningTime;
    private Integer GamesInProgress;

    public Statistics() {
        RunningTime = 0L;
        GamesInProgress = 0;
    }

    public Long getRunningTime() {
        return RunningTime;
    }

    public void setRunningTime(Long runningTime) {
        RunningTime = runningTime;
    }

    public Integer getGamesInProgress() {
        return GamesInProgress;
    }

    public void setGamesInProgress(Integer gamesInProgress) {
        GamesInProgress = gamesInProgress;
    }

}
