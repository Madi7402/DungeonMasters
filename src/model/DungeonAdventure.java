package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Creates the dungeon with a difficulty and map.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class DungeonAdventure implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Update on class changes (!)
    /**
     * Saves Dungeon info
     */
    private final Dungeon myDungeon;
    private final Hero myHero;

    /**
     * Public constructor for DungeonAdventure
     */
    private DungeonAdventure() {
        myHero = new Thief("Default Hero");
        myDungeon = new Dungeon(myHero,10, 10); // TODO -JA: Is this default size good?
    }

    public DungeonAdventure(final Hero theHero, final int theMapWidth, final int theMapHeight) {
        myHero = Objects.requireNonNull(theHero);
        myDungeon = new Dungeon(myHero, theMapWidth, theMapHeight);
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