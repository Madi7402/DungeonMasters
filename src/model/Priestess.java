package model;

/**
 * A playable hero class with the special skill: Heal.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Priestess extends Hero {
    /**
     * The Priestess' special skill.
     */
    private static final SpecialSkill SPECIAL_SKILL = SpecialSkill.HEAL;

    /**
     * Construct a Priestess.
     * @param theName the provided name of the Hero.
     */
    public Priestess(final String theName) {
        super(theName);
    }

    /**
     * Perform special skill.
     *
     * @return true if successful
     */
    @Override
    public boolean specialSkill(final DungeonCharacter theTarget) {
        if (!randomChance(SPECIAL_SKILL.getChance())) {
            return false;
        }
        heal(randomValue(myStats.minHeal(), myStats.maxHeal()));
        return true;
    }
}
