package model;

import java.util.ArrayList;
import java.util.List;
import controller.PropertyChangeEnableHero;

/**
 * A dungeon character that can be played by the player. Only one can exist per dungeon.
 * Has special attacks.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public abstract class Hero extends DungeonCharacter implements PropertyChangeEnableHero {
    /**
     * List of items the Hero has collected.
     */
    private final List<Item> myInventory = new ArrayList<>();
    /**
     * The amount of Healing Potions in the Hero's inventory.
     */
    private int myHealingPotions = 0;
    /**
     * The amount of Healing Potions in the Hero's inventory.
     */
    private int myVisionPotions = 0;

    /**
     * Construct a Hero.
     * @param theName the provided name of the Hero
     */
    Hero(final String theName) { // TODO -JA: Do we want to construct with a starting level?
        super(theName, 1);
    }

    /**
     * The special skill our Heroes implement.
     * @return true if special Skill had an effect
     */
    public abstract boolean specialSkill(DungeonCharacter theTarget);

    /**
     * Reduce health by provided amount.
     * @param theDamage the amount of damage taken
     * @return true if damage has occurred
     */
    @Override
    protected boolean takeDamage(final int theDamage) {
        if (theDamage < 0 || getMyHealthPoints() == 0) {
            return false;
        }

        // Hero has a chance to block an attack and not take damage
        if (randomChance(myStats.blockChance())) {
            fireEvent(ATTACK_BLOCK);
            return false;
        }

        if (theDamage >= getMyHealthPoints()) {
            setMyHealthPoints(0);
            fireEvent(DEATH);
        } else {
            setMyHealthPoints(getMyHealthPoints() - theDamage);
            fireEvent(TAKE_DAMAGE);
        }

        return true;
    }

    /**
     * Give the Hero an Item. (Cheat)
     * @param theItemType the item to create and to the Hero's inventory
     */
    public void giveItem(final ItemType theItemType) {
        switch (theItemType) { // Update Potion counts
            case HEALING_POTION -> myHealingPotions++;
            case VISION_POTION -> myVisionPotions++;
        }
        myInventory.add(new Item(theItemType));
        fireEvent(INVENTORY_ACTION);
    }

    /**
     * Add an Item into the Hero's inventory
     * @param theItem the Item to add to the Hero's inventory (e.g. a picked up item)
     */
    public void getItem(final Item theItem) {
        if (theItem == null) {
            throw new RuntimeException("Hero tried to receive null Item");
        }

        switch (theItem.getType()) { // Update Potion counts
            case HEALING_POTION -> myHealingPotions++;
            case VISION_POTION -> myVisionPotions++;
        }

        myInventory.add(theItem);
        fireEvent(INVENTORY_ACTION);
    }

    /**
     * Return various Hero information as a String
     * @return Name, Hit Points, Number of Healing Potions, Number of Vision Potions, Pillars
     */
    @Override
    public String toString() {
        return "Name: " + getMyName() + " HP: " + getMyHealthPoints() + " Healing Potions: "
                + myHealingPotions + " Vision Potions: " + myVisionPotions + " Inventory: "
                + myInventory.toString();
    }
}
