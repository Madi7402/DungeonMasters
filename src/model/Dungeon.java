package model;

/**
 * Creates the maze of rooms within the dungeon.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Dungeon {
    private final Maze myMaze;
    private Room myCurrentRoom;
    private Coordinates myCurrentCoordinates;

    public Dungeon() {
//        RandomRoomFactory rf = new RandomRoomFactory(); // TODO -JA: Use real maze
        myMaze = new Maze(true); // TODO -JA: Use real maze for constructing dungeon
        myCurrentCoordinates = new Coordinates(0, 0, 0);
        myCurrentRoom = myMaze.getRoom(myCurrentCoordinates);
        myCurrentRoom.setIsVisited(true);
    }

    public Room getMyCurrentRoom() {
        return myCurrentRoom;
    }

    public boolean goDirection(Direction theDirection) {
        Coordinates newCoord = new Coordinates(myCurrentCoordinates.level(), myCurrentCoordinates.row()+theDirection.getYOffset()
                , myCurrentCoordinates.column()+theDirection.getXOffset());
        if (myMaze.getRoom(newCoord) != null) {
            myCurrentRoom = myMaze.getRoom(newCoord);
            myCurrentRoom.setIsVisited(true);
            myCurrentCoordinates = newCoord;
            return true;
        }
        return false;
    }

    public Maze getMyMaze() {
        return myMaze;
    }

    public Coordinates getMyCurrentCoordinates() {
        return myCurrentCoordinates;
    }
}
