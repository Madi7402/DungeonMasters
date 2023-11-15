package model;

/**
 * A dungeon character that cannot be played by the player, spawns in the dungeon. Uses SQLite.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public abstract class Monster extends DungeonCharacter {
    Monster() {
        super("Monster", 1); // TODO -JA: set monster name
    }
    /**
     * Reduce health by provided amount, possibly heal afterward.
     * @param theDamage the amount of damage taken
     * @return true if damage has occurred
     */
    @Override
    protected boolean takeDamage(final int theDamage) {
        if (theDamage < 0 || getMyHealthPoints() == 0) {
            return false;
        }

        if (theDamage >= getMyHealthPoints()) { // Dead, never heal
            setMyHealthPoints(0);
            fireEvent(DEATH);
            return true;
        }

        // Monsters have a chance to heal after an attack that loses hit points, if they're alive.
        setMyHealthPoints(getMyHealthPoints() - theDamage);
        if (randomChance(myStats.healChance())) { // Random chance to heal after taking damage
            heal(randomValue(myStats.minHeal(), myStats.maxHeal())); // TODO -JA: max healing to startHealth or a cap%
        }

        return true;
    }

    /**
     * Provide various information about the Monster.
     * @return a String containing useful information about the Monster
     */
    @Override
    public String toString() {
        return "Level " + super.getMyLevel() + " Character: " + this.getClass().getName()
                + " Health: " + super.getMyHealthPoints() + " Heal Chance: " + myStats.healChance();
    }
}
