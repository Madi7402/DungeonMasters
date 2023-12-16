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


    /**
     * Create a Maze of room to serve as the game's main Dungeon.
     * @param theHero the Hero of the DungeonAdventure
     * @param theWidth the width of the maze
     * @param theHeight the height of the maze
     * @throws IllegalArgumentException if the maze is smaller than 4x4
     */
    public Dungeon(final Hero theHero, final int theWidth, final int theHeight) {
        if (theWidth < 4 || theHeight < 4) {
            throw new IllegalArgumentException("Map must be at least 4x4");
        }
        myHero = Objects.requireNonNull(theHero);
        myPcs = new PropertyChangeSupport(this);


        var myRoomFactory = new RandomRoomFactory();
        myMaze = new Maze(myRoomFactory, theWidth, theHeight);
        myCurrentRoom = myMaze.getStartingRoom(0);
        myCurrentCoordinates = myCurrentRoom.getCoordinate();

        myCurrentRoom.setVisited(true);
    }

    /**
     * Get the room you are currently navigated to in the Maze.
     * @return the current room of the Maze
     */
    public Room getMyCurrentRoom() {
        return myCurrentRoom;
    }

    /**
     * Get the Dungeon's Maze.
     * @return get the Maze the Dungeon is using
     */
    public Maze getMyMaze() {
        return myMaze;
    }

    /**
     * Get the coordinates of the current room of the Maze.
     * @return current room coordinates
     */
    public Coordinates getMyCurrentCoordinates() {
        return myCurrentCoordinates;
    }

    /**
     * Navigate the maze in the given direction.
     * @param theDirection which way to go
     * @return true if navigated, false if could not navigate (no doors, edge of map, etc.)
     */
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

            checkIfExit();
            return true;
        }

        fireEvent(NAV_FAIL); // Fallback-failure case, shouldn't ever get here
        return false; // Could not navigate to room
    }

    /**
     * Check if we're on an exit, fire GAME_WIN if we're on the last exit.
     * @return true if we're in an exit room
     */
    public boolean checkIfExit() {
        if (myCurrentRoom.getPortal() == Portal.EXIT) {
            if (myCurrentRoom.getCoordinate().level() == 3) {
                fireEvent(GAME_WIN);
                return true;
            }
            navigateToRoom(myMaze.getStartingRoom(myCurrentCoordinates.level()+1));
            return true;
        }
        return false;
    }

    /**
     * Warp to any valid room. For testing.
     * @param theRoom the room to navigate to
     * @return true if room was valid
     */
    public boolean navigateToRoom(final Room theRoom) {
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
