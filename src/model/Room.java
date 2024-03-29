package model;

import java.util.EnumSet;
import java.util.Random;

/**
 * Represents a dungeon room within the maze, storing information about items and pillars.
 * Each room has coordinates in the maze, doors in various directions, and optional features
 * such as pits, potions, pillars, and portals.
 *
 * @author Jonathan Abrams, Martha Emerson, Madison Pope
 */
public class Room {
    /**
     * The portal type associated with the room.
     * It can be one of the following: NONE, ENTRANCE, or EXIT.
     */
    private Portal portal;
    /**
     * The set of directions where the room has doors.
     * These directions represent possible connections to adjacent rooms.
     */
    private EnumSet<Direction> doors;
    /**
     * Indicates whether the room has a pillar.
     * A pillar is a special item that...
     */
    private boolean hasPillar; // keep here
    /**
     * Indicates whether the room has a pit.
     * A pit is a dangerous element that...
     */
    private boolean hasPit;
    /**
     * Indicates whether the room has a healing potion.
     * A healing potion is an item that...
     */
    private boolean hasHealingPotion;
    /**
     * Indicates whether the room has a vision potion.
     * A vision potion is an item that...
     */
    private boolean hasVisionPotion;
    /**
     * Indicates whether the room has been visited by the backtracking algorithm.
     */
    private boolean isVisited; // keep here
    /**
     * The coordinates of the room in the maze.
     * Coordinates include the level, row, and column of the room.
     */
    private Coordinates coordinates; // x, y, and z (levels)

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
     * @param doors             The doors in various directions.
     * @param coordinates       The coordinates of the room.
     */
    public Room(boolean hasPit, boolean hasHealingPotion, boolean hasVisionPotion,
                EnumSet<Direction> doors, Coordinates coordinates) {
        this.portal = Portal.NONE;
        this.isVisited = false;
        this.hasPillar = false;
        this.hasPit = hasPit;
        this.hasHealingPotion = hasHealingPotion;
        this.hasVisionPotion = hasVisionPotion;
        this.doors = doors;
        this.coordinates = coordinates;
    }

    /**
     * Generates a random room with random features.
     *
     * @param coordinates The coordinates for the generated room.
     * @return A randomly generated room.
     */
    public static Room generateRandomRoom(Coordinates coordinates) {
        Random random = new Random();

        boolean hasPit = random.nextDouble() < 0.1;  // 10% chance for a pit
        boolean hasHealingPotion = random.nextDouble() < 0.1;  // 10% chance for a healing potion
        boolean hasVisionPotion = random.nextDouble() < 0.1;  // 10% chance for a vision potion
                                                            // TO DO - fix magic numbers
        var doors = generateRandomDoors(random);

        return new Room(hasPit, hasHealingPotion, hasVisionPotion, doors, coordinates);
    }

    /**
     * Generates a random set of doors (directions) for a room.
     * The likelihood of having a door in each direction is determined by a random number generator.
     *
     * @param random The random number generator used for door generation.
     * @return An EnumSet containing randomly generated doors.
     */
    private static EnumSet<Direction> generateRandomDoors(Random random) {
        var doors = EnumSet.noneOf(Direction.class);

        if (random.nextDouble() < 0.5) {
            doors.add(Direction.NORTH);
        }
        if (random.nextDouble() < 0.5) {
            doors.add(Direction.SOUTH);
        }
        if (random.nextDouble() < 0.5) {
            doors.add(Direction.EAST);
        }
        if (random.nextDouble() < 0.5) {
            doors.add(Direction.WEST);
        }
        return doors;
    }

    /**
     * Sets the portal type for the room.
     *
     * @param portal The portal type for the room.
     */
    public void setPortal(Portal portal) {
        this.portal = portal;
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
     * Checks if the room has multiple items (pits, potions, pillars).
     *
     * @return True if the room has multiple items, false otherwise.
     */
    public boolean hasMultipleItems() {
        int itemCount = 0;

        if (hasPit) { // could maybe batch into enum set of items... TO DO ?
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
        if (doors.contains(Direction.NORTH)) {
            sb.append("*-*\n");
        } else {
            sb.append("***\n");
        }

        // middle line
        if (doors.contains(Direction.WEST)) {
            sb.append("|");
        } else {
            sb.append("*");
        }

        if (hasMultipleItems()) {
            sb.append("M");
        } else if (hasPillar) { // one pillar type per level
            var level = coordinates.level();

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
        } else if (portal.equals(Portal.ENTRANCE)) {
            sb.append("i");
        } else if (portal.equals(Portal.EXIT)) {
            sb.append("O");
        } else if (hasHealingPotion) {
            sb.append("H");
        } else if (hasVisionPotion) {
            sb.append("V");
        } else {
            sb.append(" ");
        }

        if (doors.contains(Direction.EAST)) {
            sb.append("|\n");
        } else {
            sb.append("*\n");
        }

        // bottom line
        if (doors.contains(Direction.SOUTH)) {
            sb.append("*-*\n");
        } else {
            sb.append("***\n");
        }

        return sb.toString();
    }
}