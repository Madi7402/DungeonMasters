package model;

/**
 * Represents cardinal directions (North, South, East, West).
 * Each direction has associated x and y offsets indicating movement on a 2D grid.
 * The x and y offsets allow for easy navigation on a 2D grid based on the direction.
 *
 * @author Jonathan Abrams, Martha Emerson, Madison Pope
 */
public enum Direction {
    NORTH(0, -1), // Move north (up)
    SOUTH(0, 1),  // Move south (down)
    EAST(1, 0),   // Move east (right)
    WEST(-1, 0);  // Move west (left)

    /**
     * The x offset indicating movement in the horizontal direction.
     */
    private final int myXOffset;

    /**
     * The y offset indicating movement in the vertical direction.
     */
    private final int myYOffset;

    /**
     * Constructs a direction with the specified x and y offsets.
     *
     * @param theXOffset The x offset indicating movement in the horizontal direction.
     * @param theYOffset The y offset indicating movement in the vertical direction.
     */
    Direction(final int theXOffset, final int theYOffset) {
        this.myXOffset = theXOffset;
        this.myYOffset = theYOffset;
    }

    /**
     * Gets the x offset indicating movement in the horizontal direction.
     *
     * @return The x offset.
     */
    public int getXOffset() {
        return myXOffset;
    }

    /**
     * Gets the y offset indicating movement in the vertical direction.
     *
     * @return The y offset.
     */
    public int getYOffset() {
        return myYOffset;
    }

    /**
     * Gets the opposite direction of the current direction.
     * For example, if the current direction is NORTH, the opposite direction will be SOUTH.
     *
     * @return The opposite direction.
     * @throws IllegalStateException if the current direction is unexpected or invalid.
     */
    public Direction getOppositeDirection() {
        switch(this) {
            case NORTH -> {
                return SOUTH;
            }
            case SOUTH -> {
                return NORTH;
            }
            case EAST -> {
                return WEST;
            }
            case WEST -> {
                return EAST;
            }
            default -> throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}