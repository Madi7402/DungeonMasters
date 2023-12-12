package controller;

public interface PropertyChangeEnableDungeon extends PropertyChangeEnable {
    /**
     * The Hero has moved in the Maze
     */
    String NAVIGATED = "dNav";
    String HIT_PIT = "dPit";
    String FIGHT = "dFight";
}
