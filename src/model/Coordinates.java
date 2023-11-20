package model;

public record Coordinates(int level, int row, int column) {
    public Coordinates generate(int offsetRow, int offsetColumn) {
        return new Coordinates(level, row() + offsetRow, column() + offsetColumn);
    }
}
