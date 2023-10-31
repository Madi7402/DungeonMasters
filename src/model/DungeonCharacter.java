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
    }

    private static void attackBehavior(final int theMinDamage, final int theMaxDamage) {
        // TODO -JA: Perhaps attack behavior should be configurable on a per-class basis
        //           or otherwise be substitutable via a attackBehavior Factory
    }

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

    /**
     * Reduce health by provided amount
     * @param theDamage the amount of damage taken
     */
    protected void takeDamage(final int theDamage) {
        if (theDamage < 0) {
            throw new IllegalArgumentException("Damage value should never be negative");
        } else if (theDamage >= myHealthPoints) {
            myHealthPoints = 0;
            // notify of death
        } else {
            myHealthPoints -= theDamage;
        }
    }

    /**
     * Heal health by provided amount
     * @param theHealth the amount of health to heal
     * @return true if healing has occurred
     */
    protected boolean heal(final int theHealth) {
        if (theHealth < 0) {
            throw new IllegalArgumentException("Health value must not be negative to heal");
        }

        // TODO -JA: keep track of maximum health for the character and don't exceed that.

        myHealthPoints += theHealth;
        return true;
    }

    /**
     * Determine if an action was successful with provided probability.
     * @param theChance percetnage of being successful (e.g. 40.0 for a 40% chance)
     * @return true if probability was a success.
     */
    protected boolean randomChance(final double theChance) {
        return RANDOM_SOURCE.nextDouble() <= theChance/100;
    }

    /**
     * Get DungeonCharacter's random source generator.
     * @return random source for probability / damage / health calculations.
     */
    protected Random getRandomSource() {
        return RANDOM_SOURCE; // TODO -JA: is this getter a good idea?
    }
}