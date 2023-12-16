package controller;

/**
 * An interface for events that can happen with the hero.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson.
 */
public interface PropertyChangeEnableHero extends PropertyChangeEnable {
    /** Hero Item Added / Removed from inventory **/
    String INVENTORY_ACTION = "iAction";
    /** Hero used Vision Potion **/
    String VISION_POTION_USED = "mapVisionPotion";
}
