package view;

import model.Direction;
import model.Dungeon;
import model.DungeonControls;

import java.util.Objects;

public class OverworldControls implements DungeonControls {
    private final Dungeon myDungeon;
    public OverworldControls(final Dungeon theDungeon) {
        myDungeon = Objects.requireNonNull(theDungeon);
    }

    @Override
    public boolean left() {
        return myDungeon.goDirection(Direction.WEST);
    }

    @Override
    public boolean right() {
        return myDungeon.goDirection(Direction.EAST);
    }

    @Override
    public boolean up() {
        return myDungeon.goDirection(Direction.NORTH);
    }

    @Override
    public boolean down() {
        return myDungeon.goDirection(Direction.SOUTH);
    }
}
