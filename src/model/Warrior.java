package model;

/**
 * A playable hero class with the special skill: Crushing Blow.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Warrior extends Hero {
    /**
     * The Warrior's special skill.
     */
    private final static SpecialSkill mySpecSkill = SpecialSkill.CRUSHING_BLOW;

    /**
     * Construct a Warrior.
     * @param theName the provided name of the Hero.
     */
    public Warrior(final String theName) {
        super(theName); // TODO -JA: Do we want to be able to pass in a starting level?
    }

    /**
     * Perform special skill.
     * @return true if successful
     */
    @Override
    protected boolean specialSkill() {
        if (!randomChance(mySpecSkill.getChance())) { // TODO - JA: Special Attack logic
            return false;
        }
        return true;
    }
}
