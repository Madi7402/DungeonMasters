package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * A randomly generated, four-level Maze within a dungeon.
 * The maze is composed of rooms, and each room has various attributes such as portals, items, and positions.
 * The maze is created with a fixed number of levels, width, and height.
 *
 * @author Jonathan Abrams, Martha Emerson, Madison Pope
 */
public class Maze implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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
    private static final int LEVELS = 4;

    /**
     * Constructs a maze with the specified number of levels, width, and height.
     * Initializes the maze with an empty collection of rooms.
     * @param theRoomFactory The factory that makes rooms.
     * @param theWidth The width of the maze (number of columns).
     * @param theHeight The height of the maze (number of rows).
     */
    public Maze(final AbstractRoomFactory theRoomFactory, final int theWidth, int theHeight) {
        this.myWidth = theWidth;
        this.myHeight = theHeight;
        initializeMaze(theRoomFactory);
    }

    public Room getStartingRoom() {
        for (var room : myRooms.values()) {
            if (room.getPortal().equals(Portal.ENTRANCE)) {
                return room;
            }
        }
        throw new IllegalStateException("Maze has no entrance!");
    }

    /**
     * Initializes the maze with rooms, entrance and exit portals, and ensures traversability across levels.
     * Iterates through each level, initializing rooms, setting portals, and validating the maze.
     *
     * @param theRoomFactory The factory that makes rooms.
     */
    private void initializeMaze(final AbstractRoomFactory theRoomFactory) {

        for (int level = 0; level < LEVELS; level++) {
            // Initialize maze with empty rooms for each level
            initializeMazeRooms(theRoomFactory, level);

            // Set entrance and exit for each level
            var entrance = setEntranceAndExit(level);

            var criteria = new ValidMazeCriteria();

            // Start maze traversal from the entrance for each level
            if (!TraverseTo(entrance, criteria)) {
                level--; // if it fails, keep the regeneration on this same level
            }
        }

        // Set all rooms not visited so view can keep rooms invisible until visited by player
        for (Map.Entry<Coordinates, Room> entry : myRooms.entrySet()) {
            Room room = entry.getValue();
            room.setVisited(false);
        }
    }

    /**
     * A helper class to validate maze criteria during the traversal process.
     * Criteria include finding the exit and detecting dead-end rooms.
     */
    private class ValidMazeCriteria {
        /**
         * Indicates whether the exit has been found during maze traversal.
         */
        boolean isExitFound;
        /**
         * Indicates whether a dead-end room has been encountered during maze traversal.
         */
        boolean hasDeadEnd;
        /**
         * Checks if the maze traversal meets the specified criteria.
         *
         * @return True if the criteria are met; otherwise, false.
         */
        public boolean isValid() {
            return (isExitFound && hasDeadEnd);
        }

        /**
         * Validates the specified room against the criteria.
         * Marks dead-end rooms with pillars and identifies the exit room.
         *
         * @param theRoom The room to be validated.
         */
        private void validateRoom(final Room theRoom) {

            if (!isExitFound && isExitRoom(theRoom)) {
                isExitFound = true;
                // TODO - add extra scary monster to guard exit
            }

            if (!hasDeadEnd && isDeadEndRoom(theRoom)) {
                hasDeadEnd = true;
                theRoom.setPillar(true);
                // TODO - add extra scary monster to guard pillar
            }
        }
    }

    /**
     * Initializes the maze with rooms for a specific level.
     * Populates the maze by creating rooms at each coordinate for the given level.
     *
     * @param theRoomFactory The factory that makes rooms.
     * @param theLevel       The level for which rooms are to be initialized.
     */
    private void initializeMazeRooms(final AbstractRoomFactory theRoomFactory, final int theLevel) {
        // Loop through each level, row, and column to create rooms
        for (int row = 0; row < myWidth; row++) {
            for (int col = 0; col < myHeight; col++) {
                var coordinates = new Coordinates(theLevel, row, col);
                myRooms.put(coordinates, theRoomFactory.createRoom(coordinates));
            }
        }
    }

    /**
     * Sets the entrance and exit portals for each level in the maze.
     * The entrance and exit are positioned based on the level, creating a path through the maze.
     * Also, sets the corresponding portal type in the room objects.
     *
     * @param theLevel The level for which entrance and exit portals are to be set.
     * @return The coordinates of the entrance portal.
     */
    private Coordinates setEntranceAndExit(final int theLevel) { // sets them per level
        Coordinates entrance, exit;

        if (theLevel % 2 == 0) {
            // Even levels: bottom-left entrance to top-right exit
            entrance = new Coordinates(theLevel, 0, myHeight - 1);
            exit = new Coordinates(theLevel, myWidth - 1, 0);
        } else {
            // Odd levels: top-right entrance to bottom-left exit
            entrance = new Coordinates(theLevel, myWidth - 1, 0);
            exit = new Coordinates(theLevel, 0, myHeight - 1);
        }

        // TODO - delete any objects!!! (from the entrance and exit)

        // Set entrance and exit
        myRooms.get(entrance).setPortal(Portal.ENTRANCE);
        myRooms.get(exit).setPortal(Portal.EXIT);


        return entrance;
    }

                // Ensure that Maze is traversable from entrance to exit
    /**
     * Recursively generates the maze starting from the specified coordinate.
     * Explores directions from the coordinate, creates connections between rooms, and validates maze criteria.
     *
     * @param theCoordinate The starting coordinate for maze generation.
     * @param theCriteria   The criteria used to validate the maze during traversal.
     * @return True if traversal is successful according to the specified criteria; otherwise, false.
     */
    private boolean TraverseTo(final Coordinates theCoordinate, final ValidMazeCriteria theCriteria) {
        if (!isValidRoom(theCoordinate)) {
            throw new IllegalArgumentException("Invalid room: " + theCoordinate);
        }

        var room = myRooms.get(theCoordinate);
        connectRooms(room);
        theCriteria.validateRoom(room);
        room.setVisited(true);

        if (isTraversalComplete()) {
            return theCriteria.isValid();
        }

        // Iterate through each direction
        for (Room neighbor : room.getNeighbors()) { // get all the neighbors
            var nextCoordinate = neighbor.getCoordinate();

            // Check if the next room is within bounds and not visited
            if (!isVisited(nextCoordinate)) {
                // Recursively generate the maze from the next room
                if (TraverseTo(nextCoordinate, theCriteria)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if traversal of the maze is complete by verifying if all rooms have been visited.
     *
     * @return True if all rooms are visited; otherwise, false.
     */
    private boolean isTraversalComplete() {
        return myRooms.values().stream().allMatch(Room::isVisited);
    }

    /**
     * Connects the given room to its neighboring rooms in all four directions.
     * For each direction, the method attempts to establish a connection between the room and its neighbor.
     *
     * @param theRoom The room for which connections are to be established.
     */
    private void connectRooms(final Room theRoom) {
        for (var direction : Direction.values()) { // all 4 directions
            connectRooms(theRoom, direction);
        }
    }

    /**
     * Connects the given room to its neighbor in the specified direction.
     * If a neighboring room exists in the specified direction, a connection is established.
     *
     * @param theRoom      The room for which a connection is to be established.
     * @param theDirection The direction in which the connection is to be established.
     */
    private void connectRooms(final Room theRoom, final Direction theDirection) {
        var coordinate = theRoom.getCoordinate();
        var neighborCoordinate = coordinate.generate(theDirection);
        if (myRooms.containsKey(neighborCoordinate)) {
            var neighbor = myRooms.get(neighborCoordinate);
            theRoom.trySetNeighbor(neighbor, theDirection);
        } else {
        theRoom.tryRemoveDoor(theDirection);
        }
    }

    /**
     * Checks if the provided room is a dead-end room.
     * A dead-end room has no portal and only one neighbor.
     *
     * @param theRoom The room to be checked.
     * @return True if the room is a dead-end; otherwise, false.
     */
    private boolean isDeadEndRoom(final Room theRoom) {
        return (theRoom.getPortal() == Portal.NONE && theRoom.getNeighbors().size() == 1);
    }

    /**
     * Checks if the provided room is an exit room.
     *
     * @param theRoom The room to be checked.
     * @return True if the room is an exit room; otherwise, false.
     */
    private boolean isExitRoom(final Room theRoom) {
        return theRoom.getPortal() == Portal.EXIT;
    }

    /**
     * Checks if a room at the specified coordinate is within the bounds of the maze.
     * @param theCoordinate The coordinates of the room.
     * @return True if the room is within bounds; otherwise, false.
     */
    private boolean isValidRoom(final Coordinates theCoordinate) {
        return myRooms.containsKey(theCoordinate);
    }

    /**
     * Checks if a room at the specified coordinate has been visited.
     * @param theCoordinate The coordinates of the room.
     * @return True if the room has been visited; otherwise, false.
     */
    private boolean isVisited(final Coordinates theCoordinate) {
        return myRooms.get(theCoordinate).isVisited();
    }

    /**
     * Return the room at the provided coordinates
     * @param theCoordinates the coordinates of the room to be provided.
     * @return the room at the given coordinates
     */
    public Room getRoom(final Coordinates theCoordinates) {
        return myRooms.get(theCoordinates);
    }

    public Room getRoom(final Coordinates theCoordinates, final Direction theDirection) {
        if (theCoordinates == null) {
            return null;
        }
        return myRooms.get(new Coordinates(theCoordinates.level(),
                theCoordinates.row()+theDirection.getYOffset(),
                theCoordinates.column()+theDirection.getXOffset()));
    }

    private void setRoomsVisible(final List<Coordinates> theCoordinates) {
        for (Coordinates coord : theCoordinates ) {
            if (isValidRoom(coord)) {
                getRoom(coord).setVisited(true);
            }
        }
    }

    /**
     * Set the surrounding rooms to visible.
     * @param theCoordinates the center point of the rooms
     * @param radius how far to reach (i.e. 1 is the 8 rooms surrounding the center point
     */
    public void setSurroundingRoomsVisible(final Coordinates theCoordinates, final int radius) {
        List<Coordinates> coordinatesList = new ArrayList<>();
        for (int xOffset = -radius; xOffset <= radius; xOffset++) {
            for (int yOffset = -radius; yOffset <= radius; yOffset++) {
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }
                coordinatesList.add(theCoordinates.generate(xOffset, yOffset));
            }
        }
        setRoomsVisible(coordinatesList);
    }

    /**
     * Returns a string representation of the maze, including details for each level, row, and column.
     * The representation includes room details obtained from the toString() method of each room in the maze.
     * Each level is separated by a newline, and rooms are arranged in rows and columns.
     *
     * @return A string representation of the maze.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int level = 0; level <= 3; level++) {
            sb.append("Level %d\n".formatted(level + 1));
            for (int row = 0; row < myHeight; row++) {
                for (int col = 0; col < myWidth; col++) {
                    Coordinates coordinates = new Coordinates(level, row, col);
                    Room room = myRooms.get(coordinates);
                    sb.append(room.toString()); // TODO - update the string to print 3 levels separately
                }
                sb.append('\n');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}