package model;

import controller.PropertyChange;
import controller.PropertyChangeEnableDungeon;

import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Creates the maze of rooms within the dungeon.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Dungeon extends PropertyChange implements PropertyChangeEnableDungeon, Serializable {
    @Serial
    private static final long serialVersionUID = 2L; // Update on class changes (!)
    /**
     * The maze containing the rooms in our Dungeon
     */
    private final Maze myMaze;
    /**
     * The hero in the Dungeon.
     */
    private final Hero myHero;
    /**
     * The current room we're in
     */
    private Room myCurrentRoom;
    /**
     * The coordinates of the current room.
     */
    private Coordinates myCurrentCoordinates;
    /** Keep Track of our Observers and fire events. */
    private final PropertyChangeSupport myPcs;


    public Dungeon(final Hero theHero, final int theWidth, final int theHeight) {
        if (theWidth < 4 || theHeight < 4) {
            throw new IllegalArgumentException("Map must be at least 4x4");
        }
        myHero = Objects.requireNonNull(theHero);
        myPcs = new PropertyChangeSupport(this);
        var myRoomFactory = new RandomRoomFactory();
        myMaze = new Maze(myRoomFactory, theWidth, theHeight);
        myCurrentRoom = myMaze.getStartingRoom();
        myCurrentCoordinates = myCurrentRoom.getCoordinate();
        myCurrentRoom.setIsVisited(true);
    }

    public Room getMyCurrentRoom() {
        return myCurrentRoom;
    }

    public Maze getMyMaze() {
        return myMaze;
    }

    public Coordinates getMyCurrentCoordinates() {
        return myCurrentCoordinates;
    }

    public boolean goDirection(Direction theDirection) {
        Coordinates newCoord = new Coordinates(myCurrentCoordinates.level(), myCurrentCoordinates.row()+theDirection.getYOffset()
                , myCurrentCoordinates.column()+theDirection.getXOffset());
        if (myMaze.getRoom(newCoord) != null && myCurrentRoom.getDoors().contains(theDirection)) {
            myCurrentRoom = myMaze.getRoom(newCoord);
            myCurrentRoom.setIsVisited(true);
            myCurrentCoordinates = newCoord;
            fireEvent(NAVIGATED);
            return true;
        }

        // TODO: is there a pit
        // TODO: is there a monster?
        // TODO: collect items (if there was a monster after fight)
        fireEvent(NAV_FAIL);
        return false;
    }
}
