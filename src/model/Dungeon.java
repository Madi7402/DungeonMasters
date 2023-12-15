package model;

import controller.PropertyChange;
import controller.PropertyChangeEnableDungeon;

import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import static controller.PropertyChangeEnableFight.FIGHT_BEGIN;

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

        myCurrentRoom.setVisited(true);
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
        if (!myCurrentRoom.getDoors().contains(theDirection)) {
            fireEvent(NAV_FAIL);
            return false;
        }
        Room newRoom = myMaze.getRoom(myCurrentCoordinates, theDirection);

        if (navigateToRoom(newRoom)) {
            checkForPit();
            checkForMonster();

            // Collect items from room
            for (Item item : myCurrentRoom.takeEquipableItems()) {
                myHero.getItem(item);
            }

            return true;
        }

        fireEvent(NAV_FAIL);
        return false; // Could not navigate to room
    }

    private boolean navigateToRoom(final Room theRoom) {
        if (theRoom == null || !myMaze.isValidRoom(theRoom.getCoordinate())) {
            return false;
        }

        myCurrentRoom = theRoom;
        myCurrentRoom.setVisited(true);
        myCurrentCoordinates = myCurrentRoom.getCoordinate();
        fireEvent(NAVIGATED);

        return true;
    }

    private void checkForPit() {
        if (myCurrentRoom.hasPit()) {
            myHero.hitPit();
        }
    }

    private void checkForMonster() {
        if (myCurrentRoom.hasMonster()) {
            startMonsterFight();
        }
    }

    private void startMonsterFight() {
        fireEvent(FIGHT_BEGIN);
    }
}
