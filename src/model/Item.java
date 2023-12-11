package model;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * An item within the dungeon that can be used by the player. Uses SQLite.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Item {
    /**
     * The Name (identifier) of the Item.
     */
    private String myName; // does this need to be final?
    /**
     * Text description of the Item.
     */
    private String myDesc; // final?
    /**
     * The ItemType of the Item
     */
    private final ItemType myType;

    private final double myPercentageChance;
    //** If it's unique, there's only one. Only Pillars are unique*/
    // myPercentageChance is ignored if it's unique.
    private final boolean isUnique;
    //** Only items you can remove from the room are equipable. (Never pits.) */
    private final boolean isEquipable;

    public boolean isEquipable() {
        return isEquipable;
    }

    /**
     * Create an Item given an ItemType.
     * @param theItemType the ItemType we are creating.
     */
    public Item(final ItemType theItemType) {
        myType = theItemType;
        double percentageChance = 0;
        boolean unique = false;
        boolean equipable = false;

        final SQLiteDataSource ds = new SQLiteDataSource(); // From example code
        ds.setUrl("jdbc:sqlite:database.sqlite.db");
        final String query = "SELECT * FROM item where name == '" + theItemType.toString().toLowerCase() + "'"; // TODO -JA: SQLi potential

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            final ResultSet rs = stmt.executeQuery(query);
            myName = rs.getString("friendly_name");
            myDesc = rs.getString("description"); // TODO -JA: Expose other fields
            percentageChance = rs.getDouble("percentage_chance"); // TODO -ME: add to database
            // myPercentageChance needs to be validated that it's between 0 and 1 (inclusive)
            unique = rs.getBoolean("is_unique"); // TODO -ME: add to database
            equipable = rs.getBoolean("is_equipable"); // TODO -ME: add to database
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        myPercentageChance = percentageChance;
        isUnique = unique;
        isEquipable = equipable;
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
