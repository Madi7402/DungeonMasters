package model;

import java.util.Collection;
import java.util.EnumSet;
import java.util.TreeMap;

/**
 * Represents a dungeon room within the maze, storing information about items and pillars.
 * Each room has coordinates in the maze, doors in various directions, and optional features
 * such as pits, potions, pillars, and portals.
 *
 * @author Jonathan Abrams, Martha Emerson, Madison Pope
 */
public class Room {
    /**
     * A mapping of directions to neighboring rooms.
     * This TreeMap represents the connections to adjacent rooms in different directions.
     * The keys are Direction enum values, and the values are corresponding neighboring Room objects.
     */
    private final TreeMap<Direction, Room> myNeighbors = new TreeMap<>();
    /**
     * A mapping of item types to items present in the room.
     * This TreeMap represents the items located in the room, where keys are ItemType enum values,
     * and values are corresponding Item objects.
     */
    private final TreeMap<ItemType, Item> myItems = new TreeMap<>();
    /**
     * The portal type associated with the room.
     * It can be one of the following: NONE, ENTRANCE, or EXIT.
     */
    private Portal myPortal;
    /**
     * The set of directions where the room has doors.
     * These directions represent possible connections to adjacent rooms.
     */
    private EnumSet<Direction> myDoors;
    /**
     * Indicates whether the room has a pillar.
     * A pillar is a special item that...
     */
    private boolean hasPillar; // will go away, only need method
    /**
     * Indicates whether the room has a pit.
     * A pit is a dangerous element that...
     */
    private boolean hasPit; // will go away, only need method
    /**
     * Indicates whether the room has a healing potion.
     * A healing potion is an item that...
     */
    private boolean hasHealingPotion; // will go away, only need method
    /**
     * Indicates whether the room has a vision potion.
     * A vision potion is an item that...
     */
    private boolean hasVisionPotion; // will go away, only need method
    /**
     * Indicates whether the room has been visited by the backtracking algorithm.
     */
    private boolean isVisited; // keep here
    /**
     * The coordinates of the room in the maze.
     * Coordinates include the level, row, and column of the room.
     */
    private Coordinates myCoordinates; // x, y, and z (levels)

    /**
     * Default constructor for an empty room.
     */
    public Room() {

    }

    /**
     * Constructor for a room with specific features.
     *
     * @param hasPit            Whether the room has a pit.
     * @param hasHealingPotion Whether the room has a healing potion.
     * @param hasVisionPotion   Whether the room has a vision potion.
     * @param myDoors             The doors in various directions.
     * @param coordinates       The coordinates of the room.
     */
    public Room(boolean hasPit, boolean hasHealingPotion, boolean hasVisionPotion,
                EnumSet<Direction> myDoors, Coordinates coordinates) {
        this.myPortal = Portal.NONE;
        this.isVisited = false;
        this.hasPillar = false;
        this.hasPit = hasPit;
        this.hasHealingPotion = hasHealingPotion;
        this.hasVisionPotion = hasVisionPotion;
        this.myDoors = myDoors;
        this.myCoordinates = coordinates;
    }

    /**
     * Sets the portal type for the room.
     *
     * @param myPortal The portal type for the room.
     */
    public void setMyPortal(Portal myPortal) {
        this.myPortal = myPortal;
    }

    /**
     * Retrieves the portal type associated with the room.
     *
     * @return The portal type of the room.
     */
    public Portal getMyPortal() {
        return myPortal;
    }

    /**
     * Checks if the room has been visited.
     *
     * @return True if the room has been visited, false otherwise.
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * Sets the visitation status of the room.
     *
     * @param isVisited The visitation status to set.
     */
    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    /**
     * Attempts to set a neighbor in a specified direction.
     *
     * @param neighbor   The neighboring room.
     * @param direction  The direction of the neighbor.
     * @return True if the neighbor was successfully set, false otherwise.
     */
    public boolean trySetNeighbor(Room neighbor, Direction direction) {
        if (!myDoors.contains(direction)) {
            return false;
        }

        if (!neighbor.myCoordinates.equals(myCoordinates.generate(direction))) {
            return false; // should this throw??
        }

        if (!neighbor.myDoors.contains(direction.getOppositeDirection())) {
            myDoors.remove(direction);
            return false;
        }

        return true; // neighbor door matches yours
    }

    /**
     * Checks if the room has multiple items (pits, potions, pillars).
     *
     * @return True if the room has multiple items, false otherwise.
     */
    public boolean hasMultipleItems() {
        int itemCount = 0;

        if (hasPit) {
            itemCount++;
        }
        if (hasHealingPotion) {
            itemCount++;
        }
        if (hasVisionPotion) {
            itemCount++;
        }
        if (hasPillar) {
            itemCount++;
        }

        return itemCount > 1;
    }

    /**
     * Represents the room as a 2D graphical representation.
     *
     * @return A string representing the graphical layout of the room.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // top line
        if (myDoors.contains(Direction.NORTH)) {
            sb.append("*-*\n");
        } else {
            sb.append("***\n");
        }

        // middle line
        if (myDoors.contains(Direction.WEST)) {
            sb.append("|");
        } else {
            sb.append("*");
        }

        if (hasMultipleItems()) {
            sb.append("M");
        } else if (hasPillar) { // one pillar type per level
            var level = myCoordinates.level();

            if (level == 0) { // fix magic numbers
                sb.append("A");
            } else if (level == 1) {
                sb.append("E");
            } else if (level == 2) {
                sb.append("I");
            } else if (level == 3) {
                sb.append("P");
            } else {
                sb.append("?");
            }
        } else if (hasPit) {
            sb.append("X");
        } else if (myPortal.equals(Portal.ENTRANCE)) {
            sb.append("i");
        } else if (myPortal.equals(Portal.EXIT)) {
            sb.append("O");
        } else if (hasHealingPotion) {
            sb.append("H");
        } else if (hasVisionPotion) {
            sb.append("V");
        } else {
            sb.append(" ");
        }

        if (myDoors.contains(Direction.EAST)) {
            sb.append("|\n");
        } else {
            sb.append("*\n");
        }

        // bottom line
        if (myDoors.contains(Direction.SOUTH)) {
            sb.append("*-*\n");
        } else {
            sb.append("***\n");
        }

        return sb.toString();
    }

    /**
     * Retrieves the coordinate of a room in the maze.
     *
     * @return The coordinate of the room.
     */
    public Coordinates getCoordinate() {
        return this.myCoordinates;
    }

    /**
     * Retrieves the neighbors of the room.
     *
     * @return A collection of neighboring rooms.
     */
    public Collection<Room> getNeighbors() {
        return this.myNeighbors.values();
    }

    /**
     * Sets the presence of a pillar in the room.
     *
     * @param hasPillar True if the room has a pillar, false otherwise.
     */
    public void setPillar(boolean hasPillar) {
        this.hasPillar = hasPillar; // might need to add Item instead TO DO !!!
    }


    /**
     * Adds an item to the room.
     *
     * @param item The item to be added.
     */
    public void addItem(Item item) {
        myItems.put(item.getType(), item);
    }
        // use this in place of set pit, set pillar, etc. for anything that's an item

    /**
     * Removes all equipable items from the room and returns them.
     *
     * @return A collection of equipable items.
     */
    public Collection<Item> takeEquipableItems() {
        var equipableItems = myItems.values().stream().filter(Item::isEquipable).toList();

        for (var equipableItem : equipableItems) {
            myItems.remove(equipableItem.getType());
        }
        return equipableItems;
    }

    // TODO -ME get rid of all the Item setters (pillar, potion, pit)
    // get rid of the related fields
    // keep has functions, check item tree instead of fields
    // update toString to use these functions for the printing
    // map means one pit, one pillar, and one potion of each type
    // don't touch Portal!!!
    // and then update Maze to use Items instead of booleans
}