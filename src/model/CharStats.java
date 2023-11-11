package model;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    /**
     * Chance to block attack (FOR HERO).
     */
    private double myBlockChance;

    public CharStats(final String theClassName) {
        // TODO -JA: Create a specification outlying appropriate values for DungeonCharacter
        //           statistics and damage characteristics.
        final SQLiteDataSource ds = new SQLiteDataSource(); // From example code
        ds.setUrl("jdbc:sqlite:database.sqlite.db");

        final String query = "SELECT * FROM character where name == '" + theClassName + "'"; // TODO -JA: SQLi potential

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement(); ) {

            final ResultSet rs = stmt.executeQuery(query);

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

    public double blockChance() {
        return myBlockChance;
    }
}
