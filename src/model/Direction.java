package model;

public enum Direction {
    NORTH(0, -1), // Move north (up)
    SOUTH(0, 1),  // Move south (down)
    EAST(1, 0),   // Move east (right)
    WEST(-1, 0);  // Move west (left)

    private final int xOffset;
    private final int yOffset;

    Direction(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }
}

