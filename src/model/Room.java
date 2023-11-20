package model;

import java.util.Random;

/**
 * A dungeon room within the maze that stores item information (...and monster and pillar info?)
 * @author ...
 */
public class Room {
    private boolean hasEntrance;
    private boolean hasExit;
    private boolean hasPillar; // keep here
    private boolean hasPit;
    private boolean hasHealingPotion;
    private boolean hasVisionPotion;
    private boolean hasDoorToNorth;
    private boolean hasDoorToSouth; //southDoor
    private boolean hasDoorToEast;
    private boolean hasDoorToWest;
    private boolean isVisited; // keep here
    private int xCoordinate;
    private int yCoordinate;
    private int level;

    public Room() {
    }

    // Constructor for an empty room
    public Room(boolean hasEntrance, boolean hasExit, boolean hasPillar, boolean hasPit,
                boolean hasHealingPotion, boolean hasVisionPotion, boolean hasDoorToNorth,
                boolean hasDoorToSouth, boolean hasDoorToEast, boolean hasDoorToWest,
                boolean isVisited, int xCoordinate, int yCoordinate, int level) {
        this.hasEntrance = hasEntrance;
        this.hasExit = hasExit;
        this.hasPillar = hasPillar;
        this.hasPit = hasPit;
        this.hasHealingPotion = hasHealingPotion;
        this.hasVisionPotion = hasVisionPotion;
        this.hasDoorToNorth = hasDoorToNorth;
        this.hasDoorToSouth = hasDoorToSouth;
        this.hasDoorToEast = hasDoorToEast;
        this.hasDoorToWest = hasDoorToWest;
        this.isVisited = isVisited;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.level = level;
    }

    // Generates a random room
    public static Room generateRandomRoom() {
        Random random = new Random();
        boolean hasEntrance = false;
        boolean hasExit = false;
        boolean hasPillar = false;
        boolean hasPit = random.nextDouble() < 0.1;  // 10% chance for a pit
        boolean hasHealingPotion = random.nextDouble() < 0.1;  // 10% chance for a healing potion
        boolean hasVisionPotion = random.nextDouble() < 0.1;  // 10% chance for a vision potion
        boolean hasDoorToNorth = random.nextDouble() < 0.5;
        boolean hasDoorToSouth = random.nextDouble() < 0.5;
        boolean hasDoorToEast = random.nextDouble() < 0.5;
        boolean hasDoorToWest = random.nextDouble() < 0.5;
        boolean isVisited = false;
        int xCoordinate = 0;
        int yCoordinate = 0;
        int level = 0;

        return new Room(hasEntrance, hasExit, hasPillar, hasPit, hasHealingPotion, hasVisionPotion,
                hasDoorToNorth, hasDoorToSouth, hasDoorToEast, hasDoorToWest, isVisited, xCoordinate,
                yCoordinate, level);
    }

    public void setHasEntrance(boolean hasEntrance) {
        this.hasEntrance = hasEntrance;
    }

    public void setHasExit(boolean hasExit) {
        this.hasExit = hasExit;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

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

    // Represents the room as a 2D graphical representation
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // top line
        if (hasDoorToNorth) {
            sb.append("*-*\n");
        } else {
            sb.append("***\n");
        }

        // middle line
        if (hasDoorToWest) {
            sb.append("|");
        } else {
            sb.append("*");
        }

        if (hasMultipleItems()) {
            sb.append("M");
        } else if (hasPillar) { // one pillar type per level
            if (level == 0) {
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
        } else if (hasEntrance) {
            sb.append("i");
        } else if (hasExit) {
            sb.append("O");
        } else if (hasHealingPotion) {
            sb.append("H");
        } else if (hasVisionPotion) {
            sb.append("V");
        } else {
            sb.append(" ");
        }

        if (hasDoorToEast) {
            sb.append("|\n");
        } else {
            sb.append("*\n");
        }

        // bottom line
        if (hasDoorToSouth) {
            sb.append("*-*\n");
        } else {
            sb.append("***\n");
        }

        return sb.toString();
    }
}