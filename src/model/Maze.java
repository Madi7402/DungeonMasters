package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Maze {
    private Room[][][] rooms;
    private int width;
    private int height;
    private static final int LEVELS = 4;  // Fixed number of levels


    public Maze(int width, int height, int levels) {
        this.width = width;
        this.height = height;
        this.rooms = new Room[LEVELS][width][height];
    }

    private void initializeMaze() {
        // Loop through each level, row, and column to create rooms
        for (int level = 0; level < LEVELS; level++) {
            for (int row = 0; row < width; row++) {
                for (int col = 0; col < height; col++) {
                    // Generate a random room and assign it to the maze
                    var coordinates = new Coordinates(level, row, col);
                    rooms[level][row][col] = Room.generateRandomRoom(coordinates);
                }
            }
        }
    }

    private Coordinates[] setEntranceAndExit() {
        var entranceCoordinates = new Coordinates[LEVELS];

        // Set entrance and exit for each level
        for (int level = 0; level < LEVELS; level++) {
            int entranceRow, entranceCol, exitRow, exitCol;

            if (level % 2 == 0) {
                // Even levels: bottom-left entrance to top-right exit
                entranceRow = 0;
                entranceCol = height - 1;
                exitRow = width - 1;
                exitCol = 0;
            } else {
                // Odd levels: top-right entrance to bottom-left exit
                entranceRow = width - 1;
                entranceCol = 0;
                exitRow = 0;
                exitCol = height - 1;
            }

            // delete any objects!!! (from the entrance and exit)

            entranceCoordinates[level] = new Coordinates(level, entranceRow, entranceCol);

            // Set entrance and exit
            rooms[level][entranceRow][entranceCol].setPortal(Portal.ENTRANCE);
            rooms[level][exitRow][exitCol].setPortal(Portal.EXIT);
        }

        return entranceCoordinates;
    }

    public void generateMaze() {
        // Initialize maze with empty rooms for each level
        initializeMaze();

        // Set entrance and exit for each level
        var entranceCoordinates = setEntranceAndExit();

        // Start maze generation from the entrance for each level
        for (var entrance : entranceCoordinates) {
            generateFrom(entrance);
        }
    }

    private void generateFrom(Coordinates coordinate) {
        var level = coordinate.level();

        // Get a randomized list of possible directions (North, South, East, West)
        List<Direction> directions = getRandomizedDirections();

        // Iterate through each direction
        for (Direction direction : directions) {
            var nextCoordinate = coordinate.generate(direction.getXOffset(), direction.getYOffset());

            // Check if the next room is within bounds and not visited
            if (isValidRoom(nextCoordinate) && !isVisited(nextCoordinate)) {
                // Recursively generate the maze from the next room
                generateFrom(nextCoordinate); // TO DO
            }
        }
    }

    // Helper method to get a randomized list of directions
    private List<Direction> getRandomizedDirections() {
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        return directions;
    }

    // Helper method to check if a room is within bounds
    private boolean isValidRoom(Coordinates coordinate) {
        var row = coordinate.row();
        var col = coordinate.column();
        var level = coordinate.level();

        return row >= 0 && row < width && col >= 0 && col < height && level >= 0 && level < LEVELS;
    }

    // Helper method to check if a room has been visited
    private boolean isVisited(Coordinates coordinate) {
        return rooms[coordinate.level()][coordinate.row()][coordinate.column()].isVisited();
    }
}


