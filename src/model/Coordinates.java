package model;

public record Coordinates(int level, int row, int column) implements Comparable<Coordinates> {
    public Coordinates generate(int offsetRow, int offsetColumn) {
        return new Coordinates(level, row() + offsetRow, column() + offsetColumn);
    }

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
}
