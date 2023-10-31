package model;

/**
 * A playable hero class with the special skill: Surprise Attack.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Thief extends Hero {
    /**
     * Construct a Thief.
     * @param theName the provided name of the Hero.
     */
    public Thief(final String theName) {
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
