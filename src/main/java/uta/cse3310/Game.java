package uta.cse3310;
//Assigned to Muhammad Elzein
public class Game {




    PlayerType Players;
    public PlayerType CurrentTurn;
    public PlayerType winState;
    public PlayerType[] Button;
    // Buttons are indexed 0 to 8 in the code
    // 0 1 2
    // 3 4 5
    // 6 7 8

    public String[] Msg;
    public int GameId;
    public Statistics Stats;
    

    Game(Statistics s) {
        
    }

    public void PrintGame() {
    
    }

    public void SetBoard(PlayerType p, int[] b) {
        
    }

    public void StartGame() {
        
    }

    public boolean CheckDraw(PlayerType player) {
        return false;
    }

    // This function returns an index for each player
    // It does not depend on the representation of Enums
    public int PlayerToIdx(PlayerType P) {
        return 0;
    }

    public void Update(UserEvent U) {
        
    }

    public void Tick() {
        // this function can be called periodically if a
        // timer is needed.

    }
}
// In windows, shift-alt-F formats the source code
// In linux, it is ctrl-shift-I