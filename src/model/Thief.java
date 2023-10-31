package model;

/**
 * A playable hero class with the special skill: Surprise Attack.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Thief extends Hero {
    /**
     * The Thief's special skill.
     */
    private final static SpecialSkill mySpecSkill = SpecialSkill.SURPRISE_ATTACK;
    /**
     * Construct a Thief.
     *
     * @param theName the provided name of the Hero.
     */
    public Thief(final String theName) {
        super(theName); // TODO -JA: Do we want to be able to pass in a starting level?
    }

    /**
     * Perform special skill.
     *
     * @return true if successful
     */
    @Override
    protected boolean specialSkill() {
        if (randomChance(mySpecSkill.getChance())) { // TODO - JA: Special Attack logic
            return true;
        } else {
            return false;
        }
    }
}
