package model;

/**
 * Test Hero for invalid values
 */
public class StressTest extends Hero {
    /**
     * Construct a Hero.
     *
     * @param theName the provided name of the Hero
     */
    public StressTest(String theName) {
        super(theName);
    }

    @Override
    public boolean specialSkill(DungeonCharacter theTarget) {
        return false;
    }
}
