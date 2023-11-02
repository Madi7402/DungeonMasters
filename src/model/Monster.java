package model;

/**
 * A dungeon character that cannot be played by the player, spawns in the dungeon. Uses SQLite.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Monster extends DungeonCharacter {
    Monster() {
        super("Monster", 1); // TODO -JA: set monster name
    }

    /**
     * Attack the Hero.
     */
    public void attack() {
        // TODO -JA: Attack logic here
    }

    /**
     * Heal Monster by defined amount.
     */
    public void heal() {
        // TODO -JA: Heal logic here
        // Select random value between Min/Max heal
        // Add value to HealthPoints upto the maximum HealthPoints for the monster
        // Or we could cap to a certain percentage (such as only healing to 80% of max health)
    }

    /**
     * Provide various information about the Monster.
     * @return a String containing useful information about the Monster
     */
    @Override
    public String toString() {
        return "Level " + super.getMyLevel() + " Character: " + this.getClass().getName()
                + " Health: " + super.getMyHealthPoints() + " Heal Chance: " + super.getMyHealChance();
    }
}
