package model;

import java.util.Random;

/**
 * An abstract class that represents an entity in the dungeon that can engage in combat.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public abstract class DungeonCharacter {
    /**
     * Random source for our DungeonCharacter.
     */
    private static final Random RANDOM_SOURCE = new Random();
    /**
     * The statistics of the DungeonCharacter.
     */
    protected CharStats myStats; // TODO -JA: Do we want this to be private?
    /**
     * The name provided to the DungeonCharacter.
     */
    private final String myName;

    /**
     * The experience level of the DungeonCharacter.
     */
    private int myLevel;
    /**
     * The amount of health points the DungeonCharacter has (<b>remaining</b>).
     */
    private int myHealthPoints;

    DungeonCharacter(final String theName, final int theLevel) {
        myName = theName;
        myLevel = theLevel;
        myStats = new CharStats(this.getClass().getSimpleName().toLowerCase()); // TODO -JA: Currently SQL issue cases termination, catch/try here?
        myHealthPoints = myStats.startingHealth(); // TODO -JA: Do we just want to build this into CharStats?
    }

    private static void attackBehavior(final int theMinDamage, final int theMaxDamage) {
        // TODO -JA: Perhaps attack behavior should be configurable on a per-class basis
        //           or otherwise be substitutable via a attackBehavior Factory
    }

    // TODO -JA: Migrate these statistics setters/getters into dedicated stats object
    public String getMyName() {
        return myName;
    }

    public int getMyLevel() {
        return myLevel;
    }

    /**
     * Set myHealthPoints for this DungeonCharacter.
     * @param theHealthPoints a value above >= 0 representing the character's health points
     * @throws IllegalArgumentException if theHealthPoints < 0
     */
    public void setMyHealthPoints(final int theHealthPoints) {
        if (theHealthPoints < 0) {
            throw new IllegalArgumentException("Health points must not be less than 0");
        }
        this.myHealthPoints = theHealthPoints; // TODO -JA: notify observers of health change
    }

    /**
     * Attack selected DungeonCharacter based on min/max stats of character.
     * @param theTarget the Monster the Hero is attacking
     * @return true if the attack hit and was not blocked
     */
    public boolean attack(final DungeonCharacter theTarget) {
        if (randomChance(myStats.hitChance())) { // Attack
            // TODO -JA: notify observers that attack *hit*
            return theTarget.takeDamage(randomValue(myStats.minDamage(), myStats.maxDamage()));
        }
        return false; // TODO -JA: notify observers that attack *missed*
    }

    /**
     * Reduce health by provided amount.
     * @param theDamage the amount of damage taken
     * @return true if damage has occurred
     */
    protected abstract boolean takeDamage(final int theDamage);

    /**
     * Heal health by provided amount.
     * @param theHealth the amount of health to heal
     * @return true if healing has occurred
     * @throws IllegalArgumentException if theHealth is a negative value
     */
    protected boolean heal(final int theHealth) {
        if (theHealth < 0) {
            throw new IllegalArgumentException("Health value must not be negative to heal");
        }
        // TODO -JA: Should we be able to heal if we are dead (health == 0)?

        // TODO -JA: keep track of maximum health for the character and don't exceed that.
        myHealthPoints += theHealth;
        return true;
    }

    /**
     * Determine if an action was successful with provided probability.
     * @param theChance percentage of being successful (e.g. 40.0 for a 40% chance)
     * @return true if probability was a success.
     */
    protected boolean randomChance(final double theChance) {
        return RANDOM_SOURCE.nextDouble() <= theChance / 100;
    }

    /**
     * Calculate the random damage within range for attack based on character Stats.
     * @return amount of damage for attack
     */
    protected int randomValue(final int theMin, final int theMax) {
        return getRandomSource().nextInt(theMin, theMax);
    }

    /**
     * Get DungeonCharacter's random source generator.
     * @return random source for probability / damage / health calculations.
     */
    protected Random getRandomSource() {
        return RANDOM_SOURCE; // TODO -JA: is this getter a good idea?
    }

    /**
     * Get the current amount of health points of the character.
     * @return number of health points remaining.
     */
    public int getMyHealthPoints() {
        return myHealthPoints;
    }
}