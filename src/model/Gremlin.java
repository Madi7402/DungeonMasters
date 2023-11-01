package model;

/**
 * A monster that pulls data from SQLite.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class Gremlin extends Monster {
    /**
     * Construct a Gremlin.
     * @param theMinHeal the least amount for the Monster to heal
     * @param theMaxHeal the greatest amount for the monster to heal
     */
    public Gremlin(final int theMinHeal, final int theMaxHeal) {
        super(theMinHeal, theMaxHeal); // TODO -JA: Is this constructor useful?
    }
}
