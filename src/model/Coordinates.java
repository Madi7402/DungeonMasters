package model;

/**
 * Represents a set of coordinates in a 3D space, specifying a level, row, and column.
 * Coordinates are immutable and can be generated based on an offset from the current coordinates.
 * Implements Comparable to allow for sorting based on level, column, and row.
 *
 * @author Jonathan Abrams, Martha Emerson, Madison Pope
 */
public record Coordinates(int level, int row, int column) implements Comparable<Coordinates> {
    /**
     * Generates new coordinates based on the specified row and column offsets.
     *
     * @param offsetRow    The offset to add to the current row.
     * @param offsetColumn The offset to add to the current column.
     * @return A new set of coordinates with adjusted row and column values.
     */
    public Coordinates generate(int offsetRow, int offsetColumn) {
        return new Coordinates(level, row() + offsetRow, column() + offsetColumn);
    }
    /**
     * Compares this set of coordinates with another set of coordinates for sorting purposes.
     *
     * @param o The other set of coordinates to compare with.
     * @return 0 if coordinates are equal, a positive value if this coordinates are greater,
     *         a negative value if this coordinates are smaller.
     */
    @Override
    public int compareTo(Coordinates o) {
        if (equals(o)) {
            return 0;
        }
        if (level != o.level) {
            return level - o.level;
        }
        if (column != o.column) {
            return column - o.column;
        }
        return row - o.row;
    }

    public Coordinates generate(Direction direction) {
        return generate(direction.getXOffset(), direction.getYOffset());
    }
}