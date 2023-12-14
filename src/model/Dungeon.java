package model;

import controller.PropertyChange;
import controller.PropertyChangeEnableDungeon;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * Creates the maze of rooms within the dungeon.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Dungeon extends PropertyChange implements PropertyChangeEnableDungeon, Serializable {
    private static final long serialVersionUID = 1L; // Update on class changes (!)
    private final Maze myMaze;
    private Room myCurrentRoom;
    private Coordinates myCurrentCoordinates;
    /** Keep Track of our Observers and fire events. */
    private final PropertyChangeSupport myPcs;


    public Dungeon(final int theWidth, final int theHeight) {
        if (theWidth < 4 || theHeight < 4) {
            throw new IllegalArgumentException("Map must be at least 4x4");
        }
        myPcs = new PropertyChangeSupport(this);
        var myRoomFactory = new RandomRoomFactory();
        myMaze = new Maze(myRoomFactory, theWidth, theHeight);
        myCurrentRoom = myMaze.getStartingRoom();
        myCurrentCoordinates = myCurrentRoom.getCoordinate();
        myCurrentRoom.setIsVisited(true);
    }

    public String getMyCurrentRoom() {
        return myCurrentRoom.toString();
    }

    public Maze getMyMaze() {
        return myMaze;
    }

    public Coordinates getMyCurrentCoordinates() {
        return myCurrentCoordinates;
    }

    public void goDirection(Direction theDirection) {
        Coordinates newCoord = new Coordinates(myCurrentCoordinates.level(), myCurrentCoordinates.row()+theDirection.getYOffset()
                , myCurrentCoordinates.column()+theDirection.getXOffset());
        if (myMaze.getRoom(newCoord) != null) {
            myCurrentRoom = myMaze.getRoom(newCoord);
        }
    }
}
