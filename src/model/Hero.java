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
     * Construct a Hero.
     * @param theName the provided name of the Hero
     */
    Hero(final String theName) { // TODO -JA: Do we want to construct with a starting level?
        super(theName, 1);
        myInventory = new ArrayList<>();
    }

    /**
     * Reduce health by provided amount.
     * @param theDamage the amount of damage taken
     * @return true if damage has occurred
     */
    @Override
    protected boolean takeDamage(final int theDamage) {
        if (theDamage < 0 || getMyHealthPoints() == 0) {
            return false;
        }

        // Hero has a chance to block an attack and not take damage
        if (randomChance(myStats.blockChance())) {
            return false; // TODO -JA: notify observers the attack was *blocked*
        }

        if (theDamage >= getMyHealthPoints()) {
            setMyHealthPoints(0); // TODO -JA: notify observers of death
        } else {
            setMyHealthPoints(getMyHealthPoints() - theDamage);
        }

        return true;
    }

    /**
     * The special skill our Heroes implement.
     * @return true if special Skill had an effect
     */
    public abstract boolean specialSkill(final DungeonCharacter theTarget);

    /**
     * Return various Hero stats as string.
     * @return Level, Character Type, Health, and Inventory of Hero
     */
    @Override
    public String toString() {
        return "Level " + super.getMyLevel() + " Character: " + this.getClass().getName()
                + " Health: " + super.getMyHealthPoints() + " Inventory: "
                + myInventory.toString();
    }
}
