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
     * @param theOffsetRow    The offset to add to the current row.
     * @param theOffsetColumn The offset to add to the current column.
     * @return A new set of coordinates with adjusted row and column values.
     */
    public Coordinates generate(final int theOffsetRow, final int theOffsetColumn) {
        return new Coordinates(level, row() + theOffsetRow, column() + theOffsetColumn);
    }
    /**
     * Compares this set of coordinates with another set of coordinates for sorting purposes.
     *
     * @param theO The other set of coordinates to compare with.
     * @return 0 if coordinates are equal, a positive value if this coordinates are greater,
     *         a negative value if this coordinates are smaller.
     */
    @Override
    public int compareTo(final Coordinates theO) {
        if (equals(theO)) {
            return 0;
        }
        if (level != theO.level) {
            return level - theO.level;
        }
        if (column != theO.column) {
            return column - theO.column;
        }
        return row - theO.row;
    }

    /**
     * Generates new coordinates based on the specified theDirection.
     *
     * @param theDirection The theDirection indicating the row and column offsets.
     * @return A new set of coordinates with adjusted row and column values.
     */
    public Coordinates generate(final Direction theDirection) {
        return generate(theDirection.getXOffset(), theDirection.getYOffset());
    }
}