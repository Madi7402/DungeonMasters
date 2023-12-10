package model;

import org.sqlite.SQLiteDataSource;
import res.SQLite;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static model.ItemType.HEALING_POTION;

/**
 * An item within the dungeon that can be used by the player. Uses SQLite.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Item implements Serializable {
    /** UID for Serialization */
    @Serial
    private static final long serialVersionUID = 0L; // Update on class changes (!)
    /**
     * The Name (identifier) of the Item.
     */
    private String myName;
    /**
     * Text description of the Item.
     */
    private String myDesc;
    /**
     * The image icon for the item.
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


    /**
     * Create an Item given an ItemType.
     * @param theItemType the ItemType we are creating.
     */
    public Item(final ItemType theItemType) {
        myType = theItemType;
        final String query = "SELECT * FROM item where name == '" + theItemType.toString().toLowerCase() + "'"; // TODO -JA: SQLi potential
        try (SQLite db = new SQLite(query)) {
            ResultSet rs = db.getMyResults();
            myName = rs.getString("friendly_name");
            myDesc = rs.getString("description");
            myIcon = rs.getString("icon");
            myHealthPoints = rs.getInt("health_points");
            myStatMultiplier = rs.getDouble("stat_multiplier");
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
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
