package model;

import java.io.Serial;
import java.io.Serializable;
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
public class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Update on class changes (!)
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
     * The monster type associated with the room (if it has a monster).
     * It can be one of the following: NONE, GREMLIN, OGRE, SKELETON
     */
    private MonsterType myMonsterType;
    /**
     * Indicates whether the room has a pillar.
     * A pillar is a special item that the hero must collect to win the game.
     * There are four pillars, which represent the pillars of OOP.
     */
    private boolean hasPillar;
    /**
     * Indicates whether the room has a pit.
     * A pit is a dangerous element that causes the hero to lose health points.
     */
    private boolean hasPit;
    /**
     * Indicates whether the room has a healing potion.
     * A healing potion is an item that allows the hero to regain health points.
     */
    private boolean hasHealingPotion;
    /**
     * Indicates whether the room has a vision potion.
     * A vision potion is an item that allows the hero to see all adjacent rooms.
     */
    private boolean hasVisionPotion;
    /**
     * Indicates whether the room has been visited by the backtracking algorithm.
     */
    private boolean isVisited;
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
     * @param theDoors             The doors in various directions.
     * @param theCoordinates       The coordinates of the room.
     */
    public Room(final boolean hasPit, final boolean hasHealingPotion, final boolean hasVisionPotion,
                final MonsterType theMonsterType, final EnumSet<Direction> theDoors,
                final Coordinates theCoordinates) {
        this.myPortal = Portal.NONE;
        this.isVisited = false;
        this.hasPillar = false;
        this.hasPit = hasPit;
        this.hasHealingPotion = hasHealingPotion;
        if (hasHealingPotion) {
            addItem(new Item(ItemType.HEALING_POTION));
        }
        this.hasVisionPotion = hasVisionPotion;
        if (hasVisionPotion) {
            addItem(new Item(ItemType.VISION_POTION));
        }
        this.myMonsterType = theMonsterType;
        this.myDoors = theDoors;
        this.myCoordinates = theCoordinates;
    }

    /**
     * Retrieves the portal type associated with the room.
     *
     * @return The portal type of the room.
     */
    public Portal getPortal() {
        return myPortal;
    }

    /**
     * Sets the portal type for the room.
     *
     * @param thePortal The portal type for the room.
     */
    public void setPortal(final Portal thePortal) {
        this.myPortal = thePortal;
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
     * @param theVisited The visitation status to set.
     */
    public void setVisited(final boolean theVisited) {
        this.isVisited = theVisited;
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
     * Retrieves the directions in which the room has doors.
     *
     * @return An EnumSet containing the directions in which the room has doors.
     */
    public EnumSet<Direction> getDoors() {
        return myDoors;
    }

    /**
     * Retrieves the type of monster present in the room.
     *
     * @return The type of monster as a MonsterType enum value.
     */
    public MonsterType getMyMonsterType() {
        return myMonsterType;
    }

    /**
     * Sets the type of monster for the room.
     *
     * @param theMonsterType The MonsterType to be set for the room.
     */
    public void setMonsterType(final MonsterType theMonsterType) {
        this.myMonsterType = theMonsterType;
    }

    /**
     * Checks whether the room contains a monster.
     *
     * @return true if the room has a monster, otherwise return false.
     */
    public boolean hasMonster() {
        return myMonsterType != MonsterType.NONE;
    }

    /**
     * Checks whether the room contains a pillar.
     *
     * @return true if the room has a pillar, otherwise return false.
     */
    public boolean hasPillar() {
        return hasPillar;
    }

    /**
     * Checks whether the room contains a healing potion.
     *
     * @return true if the room has a healing potion, otherwise return false.
     */
    public boolean hasHealingPotion() {
        return hasHealingPotion;
    }

    /**
     * Checks whether the room contains a vision potion.
     *
     * @return true if the room has a vision potion, otherwise return false.
     */
    public boolean hasVisionPotion() {
        return hasVisionPotion;
    }

    /**
     * Checks whether the room contains a pit.
     *
     * @return true if the room has a pit, otherwise return false.
     */
    public boolean hasPit() {
        return hasPit;
    }

    /**
     * Sets the presence of a pillar in the room.
     *
     * @param thePillar True if the room has a pillar, false otherwise.
     */
    public void setPillar(final boolean thePillar) {
        this.hasPillar = thePillar;
    }

    /**
     * Attempts to set a neighbor in a specified direction.
     *
     * @param theNeighbor   The neighboring room.
     * @param theDirection  The direction of the neighbor.
     * @return True if the neighbor was successfully set, false otherwise.
     */
    public boolean trySetNeighbor(final Room theNeighbor, final Direction theDirection) {
        if (!myDoors.contains(theDirection)) {
            return false;
        }

        if (!theNeighbor.myDoors.contains(theDirection.getOppositeDirection())) {
            myDoors.remove(theDirection);
            return false;
        }

        myNeighbors.put(theDirection, theNeighbor);
        theNeighbor.myNeighbors.put(theDirection.getOppositeDirection(), this);
        return true; // neighbor door matches yours
    }

    /**
     * Removes any doors that exist in the specified direction.
     *
     * @param theDirection The direction in which the door should be removed.
     */
    public void tryRemoveDoor(Direction theDirection) {
        myDoors.remove(theDirection);
    }

    /**
     * Adds an item to the room.
     *
     * @param theItem The item to be added.
     */
    public void addItem(final Item theItem) {
        myItems.put(theItem.getType(), theItem);
    }


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
     * Generates the top part of the graphical representation of the room, including doors.
     *
     * @return A string representing the top part of the room.
     */
    String printTopOfRoom() {
        StringBuilder sb = new StringBuilder();

        if (myDoors.contains(Direction.NORTH)) {
            sb.append("*-*");
        } else {
            sb.append("***");
        }

        return sb.toString();
    }

    /**
     * Generates the middle part of the graphical representation of the room, including items and features.
     *
     * @return A string representing the middle part of the room.
     */
    String printMiddleOfRoom() {
        StringBuilder sb = new StringBuilder();

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
            sb.append("|");
        } else {
            sb.append("*");
        }

        return sb.toString();
    }

    /**
     * Generates the bottom part of the graphical representation of the room, including doors.
     *
     * @return A string representing the bottom part of the room.
     */
    String printBottomOfRoom() {
        StringBuilder sb = new StringBuilder();

        if (myDoors.contains(Direction.SOUTH)) {
            sb.append("*-*");
        } else {
            sb.append("***");
        }

        return sb.toString();
    }

    /**
     * Represents the room as a 2D graphical representation.
     *
     * @return A string representing the graphical layout of the room.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(printTopOfRoom()).append('\n');
        sb.append(printMiddleOfRoom()).append('\n');
        sb.append(printBottomOfRoom()).append('\n');

        return sb.toString();
    }
}