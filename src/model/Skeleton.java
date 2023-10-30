package model;

/**
 * A monster that pulls data from SQLite.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Skeleton extends Monster{
    /**
     * Construct a Skeleton.
     * @param theMinHeal the least amount for the Monster to heal
     * @param theMaxHeal the greatest amount for the monster to heal
     */
    public Skeleton(int theMinHeal, int theMaxHeal) { // TODO -JA: Is this constructor useful?
        super(theMinHeal, theMaxHeal);
    }
}
