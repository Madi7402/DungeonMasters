package model;

public interface DungeonControls {
    /**
     * Enter room to the left
     * @return false if unable to navigate left
     */
    public boolean left();
    /**
     * Enter room to the right
     * @return false if unable to navigate right
     */
    public boolean right();
    /**
     * Enter room above
     * @return false if unable to navigate above
     */
    public boolean up();
    /**
     * Enter room below
     * @return false if unable to navigate below
     */
    public boolean down();
}
