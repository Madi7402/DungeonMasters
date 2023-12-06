package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * A randomly generated, four-level Maze within a dungeon.
 * The maze is composed of rooms, and each room has various attributes such as portals, items, and positions.
 * The maze is created with a fixed number of levels, width, and height.
 *
 * @author Jonathan Abrams, Martha Emerson, Madison Pope
 */
public class Maze {
    /**
     * The collection of rooms in the maze, organized by their coordinates.
     */
    private TreeMap<Coordinates, Room> rooms = new TreeMap<>();
    /**
     * The width of the maze, representing the number of columns (or rooms).
     */
    private int width = 5;
    /**
     * The height of the maze, representing the number of rows.
     */
    private int height = 5;
    /**
     * The fixed number of levels in the maze.
     */
    private static final int LEVELS = 4;  // Fixed number of levels - can move to game and pass from there
                                          // game decides a.k.a. DungeonAdventure


    // Test constructor for Maze development until maze generation is finished
// TODO: TESTING CONSTRUCTOR REMOVE ME WHEN DONE
    public Maze(boolean theTesting) {
        for (int l = 0; l < LEVELS; l++) {
            System.out.println("Gen Level: " + l);
            for (int i = -1; i < width; i++) {
                for (int j = -1; j < height; j++) {
                    Coordinates coord = new Coordinates(l, j, i);
                    Room genRoom = Room.generateRandomRoom(coord);
                    rooms.put(coord, genRoom);
                }
            }
        }
        System.out.println();
    }

    /**
     * Constructs a maze with the specified number of levels, width, and height.
     * Initializes the maze with an empty collection of rooms.
     * @param levels The number of levels in the maze.
     * @param width The width of the maze (number of columns).
     * @param height The height of the maze (number of rows).
     */
    public Maze(int levels, int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Fills the maze with randomly generated rooms, creating a grid of rooms with specified levels, width, and height.
     * Each room is assigned random attributes such as portals, items, and positions.
     * The rooms are stored in a TreeMap organized by their coordinates.
     */
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


    /**
     * Sets the entrance and exit portals for each level in the maze.
     * The entrance and exit are positioned based on the level, creating a path through the maze.
     * The coordinates of the entrance portals are returned.
     * Also, sets the corresponding portal type in the room objects.
     * @return An array containing the coordinates of the entrance portals.
     */
    private Coordinates[] setEntranceAndExit() {
        var entranceCoordinates = new Coordinates[LEVELS];

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

            // TODO - delete any objects!!! (from the entrance and exit)
            entranceCoordinates[level] = entrance;

            // Set entrance and exit
            rooms.get(entrance).setPortal(Portal.ENTRANCE);
            rooms.get(exit).setPortal(Portal.EXIT);
        }

        return entranceCoordinates;
    }

    /**
     * Generates a maze with the specified number of levels, width, and height.
     * Initializes and populates the maze, sets entrance and exit, and generates paths between rooms.
     * @param levels The number of levels in the maze.
     * @param width The width of the maze (number of columns).
     * @param height The height of the maze (number of rows).
     * @return A generated maze with rooms and connections.
     */
    public static Maze generateMaze(int levels, int width, int height) { // should this be public static, or private and called from constructor
        Maze maze = new Maze(levels, width, height);                     // TODO - decide!!

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

    // Ensure that Maze is traversable from entrance to exit - IN PROGRESS
    /**
     * Generates the maze starting from the specified coordinate.
     * Recursively explores directions from the coordinate, creating connections between rooms.
     * @param coordinate The starting coordinate for maze generation.
     */
    private void generateFrom(Coordinates coordinate) {

        // Get a randomized list of possible directions (North, South, East, West)
        List<Direction> directions = getRandomizedDirections();

        // Iterate through each direction
        for (Direction direction : directions) {
            var nextCoordinate = coordinate.generate(direction.getXOffset(), direction.getYOffset());

            // Check if the next room is within bounds and not visited
            if (isValidRoom(nextCoordinate) && !isVisited(nextCoordinate)) {
                // Recursively generate the maze from the next room
                generateFrom(nextCoordinate); // TODO
            }
        }
    }

    /**
     * Retrieves a randomized list of directions (North, South, East, West).
     * @return A randomized list of directions.
     */
    private List<Direction> getRandomizedDirections() {
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        return directions;
    }

    /**
     * Checks if a room at the specified coordinate is within the bounds of the maze.
     * @param coordinate The coordinates of the room.
     * @return True if the room is within bounds; otherwise, false.
     */
    private boolean isValidRoom(Coordinates coordinate) {
        var row = coordinate.row();
        var col = coordinate.column();
        var level = coordinate.level();

        return row >= 0 && row < width && col >= 0 && col < height && level >= 0 && level < LEVELS;
    }

    /**
     * Checks if a room at the specified coordinate has been visited.
     * @param coordinate The coordinates of the room.
     * @return True if the room has been visited; otherwise, false.
     */
    private boolean isVisited(Coordinates coordinate) {
        return rooms.get(coordinate).isVisited();
    }

    /**
     * Return the room at the provided coordinates
     * @param theCoordinates the coordinates of the room to be provided.
     * @return the room at the given coordinates
     */
    public Room getRoom(Coordinates theCoordinates) {
        return rooms.get(theCoordinates);
    }
}