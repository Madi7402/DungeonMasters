package model;

/**
 * A playable hero class with the special skill: Crushing Blow.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Warrior extends Hero {
    /**
     * The Warrior's special skill.
     */
    private static final SpecialSkill SPECIAL_SKILL = SpecialSkill.CRUSHING_BLOW;
    /**
     * The least amount of damage the warrior's special attack can inflict.
     */
    private static final int MIN_SPEC_DAMAGE = 75;
    /**
     * The most amount of damage the warrior's special attack can inflict.
     */
    private static final int MAX_SPEC_DAMAGE = 175;
    /**
     * Construct a Warrior.
     * @param theName the provided name of the Hero.
     */
    public Warrior(final String theName) {
        super(theName);
    }

    /**
     * Perform special skill.
     * @return true if successful
     */
    @Override
    public boolean specialSkill(final DungeonCharacter theTarget) {
        if (!randomChance(SPECIAL_SKILL.getChance())) {
            fireEvent(ATTACK_MISS);
            return false;
        }
        fireEvent(ATTACK);
        theTarget.takeDamage(randomValue(MIN_SPEC_DAMAGE, MAX_SPEC_DAMAGE));
        return true;
    }
}
