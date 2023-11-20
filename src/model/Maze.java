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
                    rooms[level][row][col] = Room.generateRandomRoom();
                }
            }
        }
    }

    private void setEntranceAndExit() {
        // Set entrance and exit for each level
        for (int level = 0; level < LEVELS; level++) {
            int entranceX, entranceY, exitX, exitY;

            if (level % 2 == 0) {
                // Even levels: bottom-left entrance to top-right exit
                entranceX = 0;
                entranceY = height - 1;
                exitX = width - 1;
                exitY = 0;
            } else {
                // Odd levels: top-right entrance to bottom-left exit
                entranceX = width - 1;
                entranceY = 0;
                exitX = 0;
                exitY = height - 1;
            }

            // Set entrance and exit
            rooms[level][entranceX][entranceY].setHasEntrance(true);
            rooms[level][exitX][exitY].setHasExit(true);
        }
    }

    public void generateMaze() {
        int entranceX = 0;
        int entranceY = 0;

        // Initialize maze with empty rooms for each level
        initializeMaze();

        // Set entrance and exit for each level
        setEntranceAndExit();

        // Start maze generation from the entrance for each level
        for (int level = 0; level < LEVELS; level++) {
            generateFrom(entranceX, entranceY, level);
        }
    }

    private void generateFrom(int x, int y, int level) {

        // Get a randomized list of possible directions (North, South, East, West)
        List<Direction> directions = getRandomizedDirections();

        // Iterate through each direction
        for (Direction direction : directions) {
            int nextX = x + direction.getXOffset();
            int nextY = y + direction.getYOffset();

            // Check if the next room is within bounds and not visited
            if (isValidRoom(nextX, nextY, level) && !isVisited(nextX, nextY, level)) {
                // Recursively generate the maze from the next room
                generateFrom(nextX, nextY, level);
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
    private boolean isValidRoom(int x, int y, int level) {
        return x >= 0 && x < width && y >= 0 && y < height && level >= 0 && level < LEVELS;
    }

    // Helper method to check if a room has been visited
    private boolean isVisited(int x, int y, int level) {
        return rooms[level][x][y].isVisited();
    }
}


