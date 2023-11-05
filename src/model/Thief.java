package model;

/**
 * A playable hero class with the special skill: Surprise Attack.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Thief extends Hero {
    /**
     * The Thief's special skill.
     */
    private static final SpecialSkill mySpecSkill = SpecialSkill.SURPRISE_ATTACK;
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
    public boolean specialSkill(final DungeonCharacter theTarget) {
        if (!randomChance(mySpecSkill.getChance())) { // TODO - JA: Special Attack logic
            return false;
        }
        return true;
    }
}
