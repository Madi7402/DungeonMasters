package model;

/**
 * A monster that pulls data from SQLite.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Ogre extends Monster {
    /**
     * Construct an Ogre.
     * @param theMinHeal the least amount for the Monster to heal
     * @param theMaxHeal the greatest amount for the monster to heal
     */
    public Ogre(final int theMinHeal, final int theMaxHeal) {
        super(theMinHeal, theMaxHeal); // TODO -JA: Is this constructor useful?
    }
}
