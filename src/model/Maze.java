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
    private final TreeMap<Coordinates, Room> myRooms = new TreeMap<>();
    /**
     * The width of the maze, representing the number of columns (or rooms).
     */
    private int myWidth = 5;
    /**
     * The height of the maze, representing the number of rows.
     */
    private int myHeight = 5;
    /**
     * The fixed number of levels in the maze.
     */
    private static final int LEVELS = 4;  // TODO -ME move to game (DungeonAdventure) and pass from there

    /**
     * Constructs a maze with the specified number of levels, width, and height.
     * Initializes the maze with an empty collection of rooms.
     * @param roomFactory The factory that makes rooms.
     * @param width The width of the maze (number of columns).
     * @param height The height of the maze (number of rows).
     */
    public Maze(AbstractRoomFactory roomFactory, int width, int height) {
        this.myWidth = width;
        this.myHeight = height;
        initializeMaze(roomFactory);
    }

    /**
     * Initializes a maze with the specified number of levels, width, and height.
     * Initializes and populates the maze, sets entrance and exit, and generates paths between rooms.
     */
    private void initializeMaze(AbstractRoomFactory roomFactory) {

        for (int level = 0; level < LEVELS; level++) {
            // Initialize maze with empty rooms for each level
            initializeMazeRooms(roomFactory, level);

            // Set entrance and exit for each level
            var entrance = setEntranceAndExit(level);

            var criteria = new ValidMazeCriteria();

            // Start maze traversal from the entrance for each level
            if (!TraverseTo(entrance, criteria)) {
                level--; // if it fails, keep the regeneration on this same level
            }
        }
    }

    private class ValidMazeCriteria {
        boolean isExitFound;
        boolean hasDeadEnd;
        public boolean isValid() {
            return (isExitFound && hasDeadEnd);
        }

        private void validateRoom(Room room) {

            if (!isExitFound && isExitRoom(room)) {    //maybe these are the problem...
                isExitFound = true;
            }

            if (!hasDeadEnd && isDeadEndRoom(room)) {
                hasDeadEnd = true;
                room.setPillar(true);
            }
        }
    }

    /**
     * Fills the maze with randomly generated rooms, creating a grid of rooms with specified levels, width, and height.
     * Each room is assigned random attributes such as portals, items, and positions.
     * The rooms are stored in a TreeMap organized by their coordinates.
     */
    private void initializeMazeRooms(AbstractRoomFactory roomFactory, int level) {
        // Loop through each level, row, and column to create rooms
        for (int row = 0; row < myWidth; row++) {
            for (int col = 0; col < myHeight; col++) {
                var coordinates = new Coordinates(level, row, col);
                myRooms.put(coordinates, roomFactory.createRoom(coordinates));
            }
        }
    }
    // TODO - test to make sure this works on reentry


    /**
     * Sets the entrance and exit portals for each level in the maze.
     * The entrance and exit are positioned based on the level, creating a path through the maze.
     * The coordinates of the entrance portals are returned.
     * Also, sets the corresponding portal type in the room objects.
     * @return An array containing the coordinates of the entrance portals.
     */
    private Coordinates setEntranceAndExit(int level) { // sets them per level
        Coordinates entrance, exit;

        if (level % 2 == 0) {
            // Even levels: bottom-left entrance to top-right exit
            entrance = new Coordinates(level, 0, myHeight - 1);
            exit = new Coordinates(level, myWidth - 1, 0);
        } else {
            // Odd levels: top-right entrance to bottom-left exit
            entrance = new Coordinates(level, myWidth - 1, 0);
            exit = new Coordinates(level, 0, myHeight - 1);
        }

        // TODO - delete any objects!!! (from the entrance and exit)

        // Set entrance and exit
        myRooms.get(entrance).setMyPortal(Portal.ENTRANCE);
        myRooms.get(exit).setMyPortal(Portal.EXIT);


        return entrance;
    }


    // Ensure that Maze is traversable from entrance to exit
    /**
     * Generates the maze starting from the specified coordinate.
     * Recursively explores directions from the coordinate, creating connections between rooms.
     * @param coordinate The starting coordinate for maze generation.
     */
    private boolean TraverseTo(Coordinates coordinate, ValidMazeCriteria criteria) {
        if (!isValidRoom(coordinate)) {
            System.out.println("Invalid Room: " + coordinate); // TODO - remove debugging when done
            return false; // TODO -ME make this throw (should never get invalid room)
        }

        var room = myRooms.get(coordinate);
        connectRooms(room);
        criteria.validateRoom(room);
        room.setIsVisited(true);

        if (isTraversalComplete()) {
            System.out.println("Traversal Complete"); // TODO - remove debugging when done
            return criteria.isValid();
        }

        // Iterate through each direction
        for (Room neighbor : room.getNeighbors()) { // get all the neighbors
            var nextCoordinate = neighbor.getCoordinate();

            // Check if the next room is within bounds and not visited
            if (!isVisited(nextCoordinate)) {
                System.out.println("Visiting: " + nextCoordinate); // TODO - remove debugging when done
                // Recursively generate the maze from the next room
                if (TraverseTo(nextCoordinate, criteria)) {
                    return true;
                }
            }
        }
        return false;
    }

    // if all rooms are visited, return true // if 1+ is not visited, return false
    private boolean isTraversalComplete() {
        boolean complete = myRooms.values().stream().allMatch(Room::isVisited);
        System.out.println("Traversal Complete: " + complete); // TODO - remove debugging when done
        return complete;
    }

    // all 4 directions
    private void connectRooms(Room room) {
        for (var direction : Direction.values()) {
            connectRooms(room, direction);
        }
    }

    private void connectRooms(Room room, Direction direction) {
        var coordinate = room.getCoordinate();
        var neighborCoordinate = coordinate.generate(direction);
        if (myRooms.containsKey(neighborCoordinate)) {
            var neighbor = myRooms.get(neighborCoordinate);
            room.trySetNeighbor(neighbor, direction);
        }
    }

    private boolean isDeadEndRoom(Room room) {
        return (room.getMyPortal() == Portal.NONE && room.getNeighbors().size() == 1);
    }

    private boolean isExitRoom(Room room) {
        return room.getMyPortal() == Portal.EXIT;
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
        return myRooms.containsKey(coordinate);
    }

    /**
     * Checks if a room at the specified coordinate has been visited.
     * @param coordinate The coordinates of the room.
     * @return True if the room has been visited; otherwise, false.
     */
    private boolean isVisited(Coordinates coordinate) {
        return myRooms.get(coordinate).isVisited();
    }

    /**
     * Return the room at the provided coordinates
     * @param theCoordinates the coordinates of the room to be provided.
     * @return the room at the given coordinates
     */
    public Room getRoom(Coordinates theCoordinates) {
        return myRooms.get(theCoordinates);
    }
}