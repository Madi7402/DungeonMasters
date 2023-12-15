package controller;

public interface PropertyChangeEnableDungeon extends PropertyChangeEnable {
    /**
     * The Hero has moved in the Maze
     */
    String NAVIGATED = "dNav";
    /**
     * The Hero tried to move, but couldn't (no Door, in fight, etc)
     */
    String NAV_FAIL = "dNavFail";
    /**
     * The room navigated to has a pit (TAKE DAMAGE!)
     */
    String HIT_PIT = "dPit";
    /**
     * There was a monster in the room (START A FIGHT)
     */
    String FIGHT = "dFight";

    /**
     * You won the game!
     */
    String GAME_WIN = "dWin";
}
