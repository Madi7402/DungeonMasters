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
    private String myName;
    /**
     * Text description of the Item.
     */
    private String myDesc;

    /**
     * Create an Item given an ItemType.
     * @param theItem the ItemType we are creating.
     */
    public Item(final ItemType theItem) {
        final SQLiteDataSource ds = new SQLiteDataSource(); // From example code
        ds.setUrl("jdbc:sqlite:database.sqlite.db");
        final String query = "SELECT * FROM item where name == '" + theItem.toString().toLowerCase() + "'"; // TODO -JA: SQLi potential

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            final ResultSet rs = stmt.executeQuery(query);
            myName = rs.getString("friendly_name");
            myDesc = rs.getString("description"); // TODO -JA: Expose other fields
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

    @Override
    public String toString() {
        return myName + ": \"" + myDesc + "\"";
    }
}
