package model;

/**
 * Contain various statistics and parameters for DungeonCharacters.
 */
public class CharStats {
    /**
     * The initial health of the character.
     */
    private int myStartingHealth;
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
     * Maximum amount to heal (FOR MONSTER / Priestess).
     */
    private int myMaxHeal;
    /**
     * Chance to heal (FOR MONSTER / Priestess).
     */
    private double myHealChance;

    public CharStats() {
        // TODO -JA: Create a specification outlying appropriate values for DungeonCharacter
        //           statistics and damage characteristics.

        // TODO -JA: retrieve statistics from SQLite database for each particular child class
        myStartingHealth = 100;
        myAttackSpeed = 20;
        myHitChance = 75.0;
        myMinDamage = 25;
        myMaxDamage = 35;
        myMinHeal = 5;
        myMaxHeal = 15;
        myHealChance = 10;
    }

    public int startingHealth() {
        return myStartingHealth;
    }

    public int attackSpeed() {
        return myAttackSpeed;
    }

    public double hitChance() {
        return myHitChance;
    }

    public int minDamage() {
        return myMinDamage;
    }

    public int maxDamage() {
        return myMaxDamage;
    }

    public int minHeal() {
        return myMinHeal;
    }

    public int maxHeal() {
        return myMaxHeal;
    }

    public double healChance() {
        return myHealChance;
    }
}
