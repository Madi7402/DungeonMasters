package model;

import controller.PropertyChangeEnableFight;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * An abstract class that represents an entity in the dungeon that can engage in combat.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public abstract class DungeonCharacter implements PropertyChangeEnableFight, Serializable {
    /** UID for Serialization */
    @Serial
    private static final long serialVersionUID = 0L; // Update on class changes?
    /**
     * Random source for our DungeonCharacter.
     */
    private static final Random RANDOM_SOURCE = new Random();
    /**
     * The statistics of the DungeonCharacter.
     */
    protected CharStats myStats; // TODO -JA: Do we want this to be private?
    /**
     * The name provided to the DungeonCharacter.
     */
    private final String myName;

    /**
     * The experience level of the DungeonCharacter.
     */
    private int myLevel;
    /**
     * The amount of health points the DungeonCharacter has (<b>remaining</b>).
     */
    private int myHealthPoints;
    /** Keep Track of our Observers and fire events. */
    private final PropertyChangeSupport myPcs;

    DungeonCharacter(final String theName, final int theLevel) {
        myName = theName;
        myLevel = theLevel;
        myStats = new CharStats(this.getClass().getSimpleName().toLowerCase()); // TODO -JA: Currently SQL issue cases termination, catch/try here?
        myHealthPoints = myStats.startingHealth(); // TODO -JA: Do we just want to build this into CharStats?
        myPcs = new PropertyChangeSupport(this);
    }

    private static void attackBehavior(final int theMinDamage, final int theMaxDamage) {
        // TODO -JA: Perhaps attack behavior should be configurable on a per-class basis
        //           or otherwise be substitutable via a attackBehavior Factory
    }


    public String getMyName() {
        return myName;
    }

    /**
     * Get current level of Character.
     * @return the level of the character
     */
    public int getMyLevel() {
        return myLevel;
    }

    /**
     * Set myHealthPoints for this DungeonCharacter.
     * @param theHealthPoints a value above >= 0 representing the character's health points
     * @throws IllegalArgumentException if theHealthPoints < 0
     */
    public void setMyHealthPoints(final int theHealthPoints) {
        if (theHealthPoints < 0) {
            throw new IllegalArgumentException("Health points must not be less than 0");
        }
        this.myHealthPoints = theHealthPoints;
        fireEvent(HEALTH_CHANGED);
    }

    /**
     * Attack selected DungeonCharacter based on min/max stats of character.
     * @param theTarget the Monster the Hero is attacking
     * @return true if the attack hit and was not blocked
     */
    public boolean attack(final DungeonCharacter theTarget) {
        if (randomChance(myStats.hitChance())) {
            fireEvent(ATTACK);
            return theTarget.takeDamage(randomValue(myStats.minDamage(), myStats.maxDamage())); // Blocked if false
        }

        // Attack Missed
        fireEvent(ATTACK_MISS);
        return false;
    }

    /**
     * Reduce health by provided amount.
     * @param theDamage the amount of damage taken
     * @return true if damage has occurred
     */
    protected abstract boolean takeDamage(final int theDamage);

    /**
     * Heal health by provided amount.
     * @param theHealth the amount of health to heal
     * @return true if healing has occurred
     * @throws IllegalArgumentException if theHealth is a negative value
     */
    protected boolean heal(final int theHealth) {
        if (theHealth < 0) {
            throw new IllegalArgumentException("Health value must not be negative to heal");
        }
        // TODO -JA: Should we be able to heal if we are dead (health == 0)?

        // TODO -JA: keep track of maximum health for the character and don't exceed that.
        int newHealthPoints = myHealthPoints += theHealth;
        fireEvent(HEALTH_CHANGED, myHealthPoints, newHealthPoints);
        myHealthPoints = newHealthPoints;
        return true;
    }

    /**
     * Determine if an action was successful with provided probability.
     * @param theChance percentage of being successful (e.g. 40.0 for a 40% chance)
     * @return true if probability was a success.
     */
    protected boolean randomChance(final double theChance) {
        return RANDOM_SOURCE.nextDouble() <= theChance / 100;
    }

    /**
     * Calculate the random damage within range for attack based on character Stats.
     * @return amount of damage for attack
     */
    protected int randomValue(final int theMin, final int theMax) {
        return getRandomSource().nextInt(theMin, theMax);
    }

    /**
     * Get DungeonCharacter's random source generator.
     * @return random source for probability / damage / health calculations.
     */
    protected Random getRandomSource() {
        return RANDOM_SOURCE; // TODO -JA: is this getter a good idea?
    }

    /**
     * Get the current amount of health points of the character.
     * @return number of health points remaining.
     */
    public int getMyHealthPoints() {
        return myHealthPoints;
    }

    /**
     * Fire event to observers.
     * @param theEvent the event from PropertyChangeEnableFight
     */
    protected void fireEvent(final String theEvent) {
        myPcs.firePropertyChange(theEvent, null, true);
    }

    /**
     * Fire event to observers with initial and ending value.
     * @param theEvent the event from PropertyChangeEnableFight
     * @param theStart the initial value
     * @param theEnd the changed value
     */
    protected void fireEvent(final String theEvent, final int theStart, final int theEnd) {
        myPcs.firePropertyChange(theEvent, theStart, theEnd);
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(thePropertyName, theListener);
    }
}