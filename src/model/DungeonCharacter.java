package model;

import controller.PropertyChange;
import controller.PropertyChangeEnableFight;
import javafx.scene.image.Image;
import res.SQLite;

import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * An abstract class that represents an entity in the dungeon that can engage in combat.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public abstract class DungeonCharacter extends PropertyChange implements PropertyChangeEnableFight, Serializable {
    /** UID for Serialization */
    @Serial
    private static final long serialVersionUID = 1L; // Update on class changes (!)
    /**
     * The maximum length of a Character's Name
     */
    public static final int MAX_NAME_LENGTH = 20;
    /**
     * Random source for our DungeonCharacter.
     */
    private static final Random RANDOM_SOURCE = new Random();
    /**
     * The statistics of the DungeonCharacter.
     */
    protected CharStats myStats;
    /**
     * The name provided to the DungeonCharacter.
     */
    private String myName;
    /**
     * The Image path for the Character
     */
    private String myImagePath;
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
        setMyName(theName);
        setMyLevel(theLevel);
        myStats = new CharStats(this.getClass().getSimpleName());
        myHealthPoints = myStats.startingHealth(); // TODO -JA: Do we just want to build this into CharStats?
        myPcs = new PropertyChangeSupport(this);
        final String imgQuery = "SELECT img FROM character where name == '" + this.getClass().getSimpleName().toLowerCase() + "'";
        System.out.println(imgQuery);
        try (SQLite db = new SQLite(imgQuery)) {
            ResultSet rs = db.getMyResults();
            myImagePath = rs.getString("img");
        } catch (SQLException e) {
            System.out.println();
        }
    }

    /**
     * Get the name of the Character.
     * @return the name of the Character
     */
    public String getMyName() {
        return myName;
    }

    /**
     * Return the character's image
     * @return Image or null if null
     */
    public Image getMyImage() {
        try {
            return new Image("img/" + myImagePath);
        } catch(Exception e) {
            // We do what we must because we can
            System.err.println(getClass().getSimpleName() + "img was null!");
        }
        return null;
    }

    /**
     * Set the name of the Character
     * @throws IllegalArgumentException if length > MAX_NAME_LENGTH or null
     */
    public void setMyName(final String theName) {
        if (theName == null || theName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name must not be longer than " + MAX_NAME_LENGTH + " or null");
        }

        if (theName.isEmpty())
            myName = "Unnamed " + getClass().getSimpleName();
        else
            myName = theName;
    }

    /**
     * Get current level of Character.
     * @return the level of the character
     */
    public int getMyLevel() {
        return myLevel;
    }

    /**
     * Set current level of the Character.
     * @throws IllegalArgumentException if level is < 1
     */
    public void setMyLevel(final int theLevel) {
        if (theLevel < 1) {
            throw new IllegalArgumentException("Level must be a positive integer");
        }
        myLevel = theLevel;
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
        fireEvent(HEALTH_CHANGED, myHealthPoints, theHealthPoints);
        this.myHealthPoints = theHealthPoints;
        fireEvent(HEALTH_UPDATE);
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
        fireEvent(HEALTH_CHANGED, myHealthPoints, newHealthPoints); // TODO -JA: Why can't OverWorld see this event?
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
     * Get the maximum amount of health the character may have.
     */
    public int getMyMaxHealthPoints() {
        return myStats.startingHealth();
    }
}