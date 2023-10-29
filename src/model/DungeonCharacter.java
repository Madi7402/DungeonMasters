package model;

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
}