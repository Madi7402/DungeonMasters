package model;

/**
 * A playable hero class with the special skill: Heal.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Priestess extends Hero {
    /**
     * The Priestess' special skill.
     */
    private final static SpecialSkill mySpecSkill = SpecialSkill.HEAL;

    /**
     * Construct a Priestess.
     * @param theName the provided name of the Hero.
     */
    public Priestess(final String theName) {
        super(theName); // TODO -JA: Do we want to be able to pass in a starting level?
    }

    /**
     * Perform special skill.
     *
     * @return true if successful
     */
    @Override
    public boolean specialSkill(final DungeonCharacter theTarget) {
        if (!randomChance(mySpecSkill.getChance())) { // TODO - JA: Special Attack logic
            return false;
        }
        return true;
    }
}
