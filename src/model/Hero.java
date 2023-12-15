package model;

import java.util.ArrayList;
import java.util.List;

import controller.PropertyChangeEnableFight;
import controller.PropertyChangeEnableHero;

import static controller.PropertyChangeEnableDungeon.HIT_PIT;
import static model.ItemType.*;

/**
 * A dungeon character that can be played by the player. Only one can exist per dungeon.
 * Has special attacks.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public abstract class Hero extends DungeonCharacter implements PropertyChangeEnableHero, PropertyChangeEnableFight {
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
            //System.out.println("Here in death event case");
            setMyHealthPoints(0);
            fireEvent(DEATH); // TODO -JA: Perhaps fire events in setMyHealthPoints()?
            return true;
        }

        //System.out.println("Here");
        fireEvent(TAKE_DAMAGE);
        setMyHealthPoints(getMyHealthPoints() - theDamage);

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

        if (theItem.getType() == PIT) {
            takeDamage(15);
            fireEvent(HIT_PIT);
            return;
        }

        switch (theItem.getType()) { // Update Potion counts
            case HEALING_POTION -> myHealingPotions++;
            case VISION_POTION -> myVisionPotions++;
        }

        myInventory.add(theItem);
        fireEvent(INVENTORY_ACTION);
    }

    /**
     * Use the item at the given index, must be separately removed
     * @param theIndex the index of the item in the hero's inventory
     * @return true if item was used
     */
    public boolean useItem(final int theIndex) {
        if (theIndex > myInventory.size()-1 || theIndex < 0) {
            throw new IllegalArgumentException("Item index out of bounds");
        }
        Item currentItem = myInventory.get(theIndex);
        switch(currentItem.getType()) {
            case HEALING_POTION -> {
                boolean didHeal = heal(currentItem.getMyHealthPoints());
                if (didHeal) {
                    fireEvent(HEALTH_CHANGED);
                    return true;
                }
                return false;
            }
            case VISION_POTION -> {
                fireEvent(VISION_POTION_USED); // Advise view to reveal relevant part of maze
                return true;
            }
            default -> {
                return false; // Other items, such as pillars, are NOT consumable
            }
        }
    }

    /**
     * Use the provided Item from the inventory, must be separately removed.
     * @param theItem the Item to use
     * @return true if the item was used
     */
    public boolean useItem(final Item theItem) {
        if (theItem == null) { return false; }
        return useItem(myInventory.indexOf(theItem));
    }


    /**
     * Remove the item at the given index from the Hero's inventory
     * @param theIndex the item at the hero's inventory index
     * @return true if item was removed
     */
    public boolean removeItem(final int theIndex) {
        if (theIndex > myInventory.size()-1 || theIndex < 0) {
            throw new IllegalArgumentException("Item index out of bounds");
        }
        Item currentItem = myInventory.get(theIndex);
        if (currentItem.getType() == HEALING_POTION || currentItem.getType() == VISION_POTION) {
            myInventory.remove(currentItem);
            return true;
        } // TODO -JA: Make this more flexible with additional potential items
        return false; // Pillars are not removable
    }

    /**
     * Get list of statistics from CharStats
     * @return toString of Character Stats
     */
    public String getStatsString() {
        return myStats.toString();
    }

    /**
     * Return the Hero's Inventory
     * @return the Hero's Inventory as a List
     */
    public List<Item> getInventory() {
        return myInventory;
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
