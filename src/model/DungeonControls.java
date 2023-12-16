package model;

public interface DungeonControls {
    /**
     * Enter room to the left
     * @return false if unable to navigate left
     */
    boolean left();
    /**
     * Enter room to the right
     * @return false if unable to navigate right
     */
    boolean right();
    /**
     * Enter room above
     * @return false if unable to navigate above
     */
    boolean up();
    /**
     * Enter room below
     * @return false if unable to navigate below
     */
    boolean down();
}
