package model;

/**
 * A playable hero class with the special skill: Heal.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Priestess extends Hero {
    /**
     * Construct a Priestess.
     * @param theName the provided name of the Hero.
     */
    public Priestess(final String theName) {
        super(theName); // TODO -JA: Do we want to be able to pass in a starting level?
    }

    /**
     * Attack a Monster target with provided skill.
     * @param theTarget the Monster to be attacked
     * @param theSpecSkill the special skill to be used
     */
    public void attack(final Monster theTarget, final SpecialSkill theSpecSkill) {
        // TODO -JA: (!) IMPORTANT (!), since our method signature differs from the Hero class
        //           we are NOT actually overriding the Hero attack method!
        //           This may lead to confusion and may prove suboptimal.

        // TODO -JA: Attack logic with special skill taken into account
    }
}
