package model;

import controller.PropertyChange;
import controller.PropertyChangeEnableDungeon;

import java.beans.PropertyChangeSupport;

/**
 * Creates the maze of rooms within the dungeon.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Dungeon extends PropertyChange implements PropertyChangeEnableDungeon {
    private final Maze myMaze;
    private Room myCurrentRoom;
    private Coordinates myCurrentCoordinates;
    /** Keep Track of our Observers and fire events. */
    private final PropertyChangeSupport myPcs;

    public Dungeon() {
        myPcs = new PropertyChangeSupport(this);
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
            fireEvent(NAVIGATED);
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
