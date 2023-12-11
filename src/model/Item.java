package model;

import org.sqlite.SQLiteDataSource;
import res.SQLite;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * An item within the dungeon that can be used by the player. Uses SQLite.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Item implements Serializable {
    /** UID for Serialization */
    @Serial
    private static final long serialVersionUID = 1L; // Update on class changes (!)
    /**
     * The Name (identifier) of the Item.
     */
    private String myName; // does this need to be final?
    /**
     * Text description of the Item.
     */
    private String myDesc; // final?
    /**
     * The icon related to this Item
     */
    private String myIcon;
    /**
     * How many points a potion should heal.
     */
    private int myHealthPoints;
    /**
     * A stats multiplier for boosters.
     */
    private double myStatMultiplier;
    /**
     * The ItemType of the Item
     */
    private final ItemType myType;

    private double myPercentageChance;
    //** If it's unique, there's only one. Only Pillars are unique*/
    // myPercentageChance is ignored if it's unique.
    private boolean isUnique;
    //** Only items you can remove from the room are equipable. (Never pits.) */
    private boolean isEquipable;

    public boolean isEquipable() {
        return isEquipable;
    }
    /**
     * Create an Item given an ItemType.
     * @param theItemType the ItemType we are creating.
     */
    public Item(final ItemType theItemType) {
        myType = theItemType;

        final SQLiteDataSource ds = new SQLiteDataSource(); // From example code
        ds.setUrl("jdbc:sqlite:database.sqlite.db");
        final String query = "SELECT * FROM item where name == '" + theItemType.toString().toLowerCase() + "'"; // TODO -JA: SQLi potential
        try (SQLite db = new SQLite(query)) {
            ResultSet rs = db.getMyResults();
            myName = rs.getString("friendly_name");
            myDesc = rs.getString("description");
            myIcon = rs.getString("icon");
            myHealthPoints = rs.getInt("health_points");
            myStatMultiplier = rs.getDouble("stat_multiplier");
            // myPercentageChance needs to be validated that it's between 0 and 1 (inclusive)
            myPercentageChance = rs.getDouble("percentage_chance");
            isUnique = rs.getBoolean("is_unique");
            isEquipable = rs.getBoolean("is_equipable");
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // TODO -JA: declare fields here instead so they can be final
    }

    /**
     * Get the friendly name of the Item.
     * @return the friendly name (e.g. "Pillar of Abstraction")
     */
    public String getName() {
        return myName;
    }

    /**
     * Get the Item's description.
     * @return the description (e.g. "Stay Hidden.")
     */
    public String getDescription() {
        return myDesc;
    }

    /**
     * Get the Item's image icon
     * @return a path to the image
     */
    public String getMyIcon() { // TODO -JA: Perhaps return a path instead?
        return myIcon; // TODO -JA: what about null?
    }

    /**
     * Get the Item's health points
     * @return how many points to heal
     */
    public int getMyHealthPoints() {
        return myHealthPoints;
    }

    /**
     * Get the stats multiple of the Item
     * @return multiple of stats when item is used (i.e. a 1.2x boost to stats)
     */
    public double getMyStatMultiplier() {
        return myStatMultiplier;
    }

    /**
     * Get the ItemType of the Item
     * @return the ItemType associated with this Item
     */
    public ItemType getType() {
        return myType;
    }

    @Override
    public String toString() {
        return myName;
    }
}
