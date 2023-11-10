package model;

import java.util.Random;

/**
 * An abstract class that represents an entity in the dungeon that can engage in combat.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public abstract class DungeonCharacter {
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
    /**
     * The attack speed of the DungeonCharacter.<hr/>
     * In a fight: the character with the highest attack speed attacks first.
     */
    private int myAttackSpeed;
    /**
     * The probability that an attack will be successful in inflicting damage.
     */
    private double myHitChance;
    /**
     * The least amount of damage a successful attack will cause.
     */
    private int myMinDamage;
    /**
     * The greatest amount of damage a successful attack will cause.
     */
    private int myMaxDamage;
    /**
     * Minimum amount to heal (FOR MONSTER).
     */
    private int myMinHeal;
    /**
     * Maximum amount to heal (FOR MONSTER).
     */
    private int myMaxHeal;
    /**
     * Chance to heal (FOR MONSTER).
     */
    private double myHealChance;
    /**
     * Random source for our DungeonCharacter.
     */
    private static final Random RANDOM_SOURCE = new Random(); // TODO -JA: Probably should use a getter instead.

    DungeonCharacter(final String theName, final int theLevel) {
        myName = theName;
        myLevel = theLevel;
        // TODO -JA: Create a specification outlying appropriate values for DungeonCharacter
        //           statistics and damage characteristics.

        // TODO -JA: retrieve statistics from SQLite database for each particular child class
        myHealthPoints = 100;
        myAttackSpeed = 20;
        myHitChance = 75.0;
        myMinDamage = 25;
        myMaxDamage = 35;
        myMinHeal = 5;
        myMaxHeal = 15;
        myHealChance = 10;
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

    public int getMyHealthPoints() {
        return myHealthPoints;
    }

    public int getMyAttackSpeed() {
        return myAttackSpeed;
    }

    public double getMyHitChance() {
        return myHitChance;
    }

    public int getMyMinDamage() {
        return myMinDamage;
    }

    public int getMyMaxDamage() {
        return myMaxDamage;
    }

    public int getMyMaxHeal() {
        return myMaxHeal;
    }

    public int getMyMinHeal() {
        return myMinHeal;
    }

    public double getMyHealChance() {
        return myHealChance;
    }

    /**
     * Set myHealthPoints for this DungeonCharacter
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
        if (randomChance(getMyHitChance())) { // Attack
            // TODO -JA: notify observers that attack *hit*
            return theTarget.takeDamage(randomValue(getMyMinDamage(), getMyMaxDamage()));
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
        return RANDOM_SOURCE.nextDouble() <= theChance/100;
    }

    /**
     * Calculate the random damage within range for attack based on character Stats
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
}