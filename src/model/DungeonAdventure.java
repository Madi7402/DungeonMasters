package model;

import java.util.Objects;

/**
 * Creates the dungeon with a difficulty and map.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class DungeonAdventure {
    /**
     * Saves Dungeon info
     */
    private final Dungeon myDungeon;
    private final Hero myHero;

    /**
     * Public constructor for DungeonAdventure
     */
    private DungeonAdventure() {
        myDungeon = new Dungeon();
        myHero = new Thief("Default Hero");
    }

    public DungeonAdventure(final Hero theHero) { // TODO -JA: Adjust generated maze size
        myDungeon = new Dungeon();
        myHero = Objects.requireNonNull(theHero);
    }

    /**
     * Get the Dungeon
     * @return the Dungeon
     */
    public Dungeon getMyDungeon() {
        return myDungeon;
    }

    /**
     * Get the Hero for this DungeonAdventure
     * @return the Hero
     */
    public Hero getMyHero() {
        return myHero;
    }
}