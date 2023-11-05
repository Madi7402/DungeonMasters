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
     * Perform special skill (Surprise attack!).
     *
     * @return true if successful
     */
    @Override
    public boolean specialSkill(final DungeonCharacter theTarget) { // TODO -JA: TEST ME, is this logic good?
        double randValue = getRandomSource().nextDouble();
        if (randValue <= 0.4) { // 40% chance of Success, two attacks
            attack(theTarget);
            attack(theTarget); // Extra attack
            return true;
        } else if (randValue <= 0.6) { // 20% chance Thief is caught
            return false;
        }

        attack(theTarget); // Other 40% chance of just a normal attack
        return true;
    }
}
