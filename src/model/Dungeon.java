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
    }

    public String getMyCurrentRoom() {
        return myCurrentRoom.toString();
    }

    public boolean goDirection(Direction theDirection) {
        Coordinates newCoord = new Coordinates(myCurrentCoordinates.level(), myCurrentCoordinates.row()+theDirection.getYOffset()
                , myCurrentCoordinates.column()+theDirection.getXOffset());
        if (myMaze.getRoom(newCoord) != null) {
            myCurrentRoom = myMaze.getRoom(newCoord);
            myCurrentCoordinates = newCoord;
            return true;
        }
        return false;
    }

    public Coordinates getMyCurrentCoordinates() {
        return myCurrentCoordinates;
    }
}
