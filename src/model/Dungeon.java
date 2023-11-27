package model;

/**
 * Creates the maze of rooms within the dungeon.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Dungeon {
    private Maze myMaze;
    private Room myCurrentRoom;
    private Coordinates myCurrentCoordinates;

    public Dungeon() {
        myMaze = new Maze(true);
        myCurrentCoordinates = new Coordinates(0, 0, 0);
        myCurrentRoom = myMaze.getRoom(myCurrentCoordinates);
    }

    public String getMyCurrentRoom() {
        return myCurrentRoom.toString();
    }

    public void goLeft() {
        Direction direction = Direction.WEST;
        Coordinates newCoord = new Coordinates(myCurrentCoordinates.level(), myCurrentCoordinates.row()+direction.getYOffset()
                , myCurrentCoordinates.column()+direction.getXOffset());
        if (myMaze.getRoom(newCoord) != null) {
            myCurrentRoom = myMaze.getRoom(newCoord);
        }
    }

    public void goRight() {
        Direction direction = Direction.EAST;
        Coordinates newCoord = new Coordinates(myCurrentCoordinates.level(), myCurrentCoordinates.row()+direction.getYOffset()
                , myCurrentCoordinates.column()+direction.getXOffset());
        if (myMaze.getRoom(newCoord) != null) {
            myCurrentRoom = myMaze.getRoom(newCoord);
        }
    }

    public void goDown() {
        Direction direction = Direction.SOUTH;
        Coordinates newCoord = new Coordinates(myCurrentCoordinates.level(), myCurrentCoordinates.row()+direction.getYOffset()
                , myCurrentCoordinates.column()+direction.getXOffset());
        if (myMaze.getRoom(newCoord) != null) {
            myCurrentRoom = myMaze.getRoom(newCoord);
        }
    }

    public void goUp() {
        Direction direction = Direction.NORTH;
        Coordinates newCoord = new Coordinates(myCurrentCoordinates.level(), myCurrentCoordinates.row()+direction.getYOffset()
                , myCurrentCoordinates.column()+direction.getXOffset());
        if (myMaze.getRoom(newCoord) != null) {
            myCurrentRoom = myMaze.getRoom(newCoord);
        }
    }
}
