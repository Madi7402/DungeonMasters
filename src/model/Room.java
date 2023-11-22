package model;

import java.util.EnumSet;
import java.util.Random;

/**
 * A dungeon Room within the Maze that stores information about Items and Pillars.
 * @author Jonathan Abrams, Martha Emerson, Madison Pope
 */
public class Room {
    private Portal portal;
    private EnumSet<Direction> doors;
    private boolean hasPillar; // keep here
    private boolean hasPit;
    private boolean hasHealingPotion;
    private boolean hasVisionPotion;
    private boolean isVisited; // keep here
    private Coordinates coordinates; // x, y, and z (levels)

    // Default constructor
    public Room() {

    }

    // Constructor for an empty room
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

    // Generates a random room
    public static Room generateRandomRoom(Coordinates coordinates) {
        Random random = new Random();

        boolean hasPit = random.nextDouble() < 0.1;  // 10% chance for a pit
        boolean hasHealingPotion = random.nextDouble() < 0.1;  // 10% chance for a healing potion
        boolean hasVisionPotion = random.nextDouble() < 0.1;  // 10% chance for a vision potion
                                                            // TO DO - fix magic numbers
        var doors = generateRandomDoors(random);

        return new Room(hasPit, hasHealingPotion, hasVisionPotion, doors, coordinates);
    }

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

    public void setPortal(Portal portal) {
        this.portal = portal;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

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

    // Represents the room as a 2D graphical representation
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