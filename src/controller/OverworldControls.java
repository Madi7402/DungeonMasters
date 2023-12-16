package controller;

import model.Direction;
import model.Dungeon;
import model.DungeonControls;

import java.util.Objects;

/**
 * Class that handles dungeon movement controls.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson.
 */
public class OverworldControls implements DungeonControls {
    private final Dungeon myDungeon;

    /**
     * Constructor method for OverworldControls.
     * @param theDungeon A final Dungeon.
     */
    public OverworldControls(final Dungeon theDungeon) {
        myDungeon = Objects.requireNonNull(theDungeon);
    }

    /**
     * A method that makes the hero move left.
     * @return boolean
     */
    @Override
    public boolean left() {
        return myDungeon.goDirection(Direction.WEST);
    }

    /**
     * A method that makes the hero move right.
     * @return boolean
     */
    @Override
    public boolean right() {
        return myDungeon.goDirection(Direction.EAST);
    }

    /**
     * A method that makes the hero move up.
     * @return boolean
     */
    @Override
    public boolean up() {
        return myDungeon.goDirection(Direction.NORTH);
    }

    /**
     * A method that makes the hero move down.
     * @return boolean.
     */
    @Override
    public boolean down() {
        return myDungeon.goDirection(Direction.SOUTH);
    }
}
