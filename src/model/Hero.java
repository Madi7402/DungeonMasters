package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon character that can be played by the player. Only one can exist per dungeon. Has special attacks.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public abstract class Hero extends DungeonCharacter {
    /**
     * List of items the Hero has collected.
     */
    private final List<Item> myInventory;
    /**
     * The special skill defined by our character type.
     */
    private final SpecialSkill mySpecSkill;
    /**
     * Probability of blocking a successful attack from a Monster.
     */
    private double myBlockChance;
    Hero(final String theName) { // TODO -JA: Do we want to construct with a starting level?
        super(theName, 1);
        myInventory = new ArrayList<>();
        mySpecSkill = SpecialSkill.DEFAULT;
        myBlockChance = 25.0; // TODO -JA: determine reasonable values and read from SQLite DB
    }

    public void attack(final Monster theTarget) {
        // TODO -JA: Attack Logic
    }

    @Override
    public String toString() {
        return "Level " + super.getMyLevel() + " Character: " + this.getClass().getName()
                + " Health: " + super.getMyHealthPoints() + " Inventory: "
                + myInventory.toString();
    }
}
