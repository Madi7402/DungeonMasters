package model;

/**
 * Creates the maze of rooms within the dungeon.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Dungeon {
    private Maze myMaze;
    private Room myCurrentRoom;
    private Coordinates myCurrentCoordinates;

    private static final int WIDTH = 5;
    private static final int HEIGHT = 5;

    public Dungeon() {
        var myRoomFactory = new RandomRoomFactory();
        myMaze = new Maze(myRoomFactory, WIDTH, HEIGHT);
        myCurrentCoordinates = new Coordinates(0, 0, 0);
        myCurrentRoom = myMaze.getRoom(myCurrentCoordinates);
    }

    public String getMyCurrentRoom() {
        return myCurrentRoom.toString();
    }

    public void goDirection(Direction theDirection) {
        Coordinates newCoord = new Coordinates(myCurrentCoordinates.level(), myCurrentCoordinates.row()+theDirection.getYOffset()
                , myCurrentCoordinates.column()+theDirection.getXOffset());
        if (myMaze.getRoom(newCoord) != null) {
            myCurrentRoom = myMaze.getRoom(newCoord);
        }
    }
}
