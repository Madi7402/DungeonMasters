package model;

/**
 * A dungeon character that cannot be played by the player, spawns in the dungeon. Uses SQLite.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Monster extends DungeonCharacter {
    /**
     * Minimum amount to heal.
     */
    private int myMinHeal;
    /**
     * Maximum amount to heal
     */
    private int myMaxHeal;
    /**
     * Probability of healing after taking damage.
     */
    private double myHealChance;

    Monster(final int theMinHeal, final int theMaxHeal) {
        super("Monster", 1); // TODO -JA: set monster name
        myMinHeal = theMinHeal;
        myMaxHeal = theMaxHeal;
        myHealChance = 10.0; // TODO -JA: Decide reasonable values
    }

    /**
     * Attack the Hero.
     */
    public void attack() {
        // TODO -JA: Attack logic here
    }

    public void heal() {
        // TODO -JA: Heal logic here
        // Select random value between Min/Max heal
        // Add value to HealthPoints upto the maximum HealthPoints for the monster
        // Or we could cap to a certain percentage (such as only healing to 80% of max health)
    }

    @Override
    public String toString() {
        return "Level " + super.getMyLevel() + "Character: " + this.getClass().getName()
                + " Health: " + super.getMyHealthPoints() + " Heal Chance: " + myHealChance;
    }
}
