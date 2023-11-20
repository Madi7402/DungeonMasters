package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class Maze {
    private TreeMap<Coordinates, Room> rooms;
    private int width = 5;
    private int height = 5;
    private static final int LEVELS = 4;  // Fixed number of levels - can move to game and pass from there
                                            // game decides a.k.a. DungeonAdventure
    public Maze(int levels, int width, int height) {
        this.width = width;
        this.height = height;
        this.rooms = new TreeMap<>();
    }

    private void initializeMaze() {
        // Loop through each level, row, and column to create rooms
        for (int level = 0; level < LEVELS; level++) {
            for (int row = 0; row < width; row++) {
                for (int col = 0; col < height; col++) {
                    // Generate a random room and assign it to the maze
                    var coordinates = new Coordinates(level, row, col);
                    rooms.put(coordinates, Room.generateRandomRoom(coordinates));
                }
            }
        }
    }

    private Coordinates[] setEntranceAndExit() {
        var entranceCoordinates = new Coordinates[LEVELS];

        // Set entrance and exit for each level
        for (int level = 0; level < LEVELS; level++) {
            Coordinates entrance, exit;

            if (level % 2 == 0) {
                // Even levels: bottom-left entrance to top-right exit
                entrance = new Coordinates(level, 0, height - 1);
                exit = new Coordinates(level, width - 1, 0);
            } else {
                // Odd levels: top-right entrance to bottom-left exit
                entrance = new Coordinates(level, width - 1, 0);
                exit = new Coordinates(level, 0, height - 1);
            }

            // delete any objects!!! (from the entrance and exit)
            entranceCoordinates[level] = entrance;

            // Set entrance and exit
            rooms.get(entrance).setPortal(Portal.ENTRANCE);
            rooms.get(exit).setPortal(Portal.EXIT);
        }

        return entranceCoordinates;
    }

    public static Maze generateMaze(int levels, int width, int height) { // should this be public static or private and called from constructor, decide!
        Maze maze = new Maze(levels, width, height);

        // Initialize maze with empty rooms for each level
        maze.initializeMaze();

        // Set entrance and exit for each level
        var entranceCoordinates = maze.setEntranceAndExit();

        // Start maze generation from the entrance for each level
        for (var entrance : entranceCoordinates) {
            maze.generateFrom(entrance);
        }
        return maze;
    }

    private void generateFrom(Coordinates coordinate) {

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
        return rooms.get(coordinate).isVisited();
    }
}


