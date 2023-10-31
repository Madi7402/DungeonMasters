package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon character that can be played by the player. Only one can exist per dungeon.
 * Has special attacks.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public abstract class Hero extends DungeonCharacter {
    /**
     * List of items the Hero has collected.
     */
    private final List<Item> myInventory;
    /**
     * Probability of blocking a successful attack from a Monster.
     */
    private double myBlockChance;

    /**
     * Construct a Hero.
     * @param theName the provided name of the Hero
     */
    Hero(final String theName) { // TODO -JA: Do we want to construct with a starting level?
        super(theName, 1);
        myInventory = new ArrayList<>();
        myBlockChance = 25.0; // TODO -JA: determine reasonable values and read from SQLite DB
    }

    /**
     * Attack selected Monster.
     * @param theTarget the Monster the Hero is attacking
     */
    public void attack(final Monster theTarget) {
        // TODO -JA: Attack Logic
        if (randomChance(getMyHitChance())) {
            // Attack
            theTarget.takeDamage(calculateDamage());
        } else {
            // Attack failed
            // TODO -JA: notify observers that attack failed? or should we return a boolean?
        }
    }

    /**
     * The special skill our Heroes implement.
     * @return
     */
    protected abstract boolean specialSkill();

    private int calculateDamage() {
        return getRandomSource().nextInt(getMyMinDamage(), getMyMaxDamage());
    }

    @Override
    public String toString() {
        return "Level " + super.getMyLevel() + " Character: " + this.getClass().getName()
                + " Health: " + super.getMyHealthPoints() + " Inventory: "
                + myInventory.toString();
    }
}
