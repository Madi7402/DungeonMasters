package model;

import res.SQLite;

import java.io.Serial;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contain various statistics and parameters for DungeonCharacters.
 */
public class CharStats implements Serializable {
    /** UID for Serialization. */
    @Serial
    private static final long serialVersionUID = 1L; // Update on class changes?
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

    /**
     * Chance to block attack (FOR HERO).
     */
    private double myBlockChance;

    /**
     * CharStats reads, and contains, various statics about DungeonCharacters provided by
     * the SQLite Database.
     * @param theClassName the simplified class name of the character (e.g. "Ogre")
     */
    public CharStats(final String theClassName) {
        // TODO -JA: Create a specification outlying appropriate values for DungeonCharacter
        //           statistics and damage characteristics.
        final String query = "SELECT * FROM character where name == '" + theClassName.toLowerCase() + "'";
        try (SQLite db = new SQLite(query)) {
            ResultSet rs = db.getMyResults();
            myStartingHealth = rs.getInt("health_points");
            myAttackSpeed = rs.getInt("attack_speed");
            myMinDamage = rs.getInt("min_damage");
            myMaxDamage = rs.getInt("max_damage");
            myMinHeal = rs.getInt("min_heal");
            myMaxHeal = rs.getInt("max_heal");
            myHitChance = rs.getDouble("hit_chance");
            myHealChance = rs.getDouble("heal_chance");
            myBlockChance = rs.getDouble("block_chance");
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Get the Character's starting ("Maximum") heath.
     * @return the starting health for the Character
     */
    public int startingHealth() {
        return myStartingHealth;
    }

    /**
     * Get the Character's Attack Speed.
     * @return the character's Attack Speed
     */
    public int attackSpeed() {
        return myAttackSpeed;
    }

    /**
     * Get the Character's Hit Chance.
     * @return the probability of the hit
     */
    public double hitChance() {
        return myHitChance;
    }

    /**
     * Get the Character's Minimum Damage.
     * @return the least amount of damage the character will deal in an attack
     */
    public int minDamage() {
        return myMinDamage;
    }

    /**
     * Get the Character's Maximum Damage.
     * @return the most amount of damage the character will deal in an attack
     */
    public int maxDamage() {
        return myMaxDamage;
    }

    /**
     * Get the Character's Minimum Healing.
     * @return the least amount of points the character will heal
     */
    public int minHeal() {
        return myMinHeal;
    }

    /**
     * Get the Character's Maximum Healing.
     * @return the most amount of points the character will heal
     */
    public int maxHeal() {
        return myMaxHeal;
    }

    /**
     * Get the Character's Chance to Heal.
     * @return the probably that the Character will heal
     */
    public double healChance() {
        return myHealChance;
    }

    /**
     * Get the Character's Chance to Block.
     * @return the probability that the character will block an attack
     */
    public double blockChance() {
        return myBlockChance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Max Health: " + myStartingHealth + "\n");
        sb.append("Attack Speed: " + myAttackSpeed + "\n");
        sb.append("Hit Chance: " + myHitChance);
        return sb.toString();
    }
}
