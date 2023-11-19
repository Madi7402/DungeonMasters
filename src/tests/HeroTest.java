package tests;
import model.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class HeroTest {
    @RepeatedTest(20)
    public void takeDamageTest() {
        Thief thief = new Thief("TestName");
        int healthPoints = thief.getMyHealthPoints();
        Ogre ogre = new Ogre();
        boolean wasAttacked = ogre.attack(thief);
        assertTrue((wasAttacked && thief.getMyHealthPoints() < healthPoints) ||
                (!wasAttacked && thief.getMyHealthPoints() == healthPoints));
    }

    @Test
    public void takeDamageDeadTest() {
        Thief thief = new Thief("TestName");
        Ogre ogre = new Ogre();
        while (thief.getMyHealthPoints() > 0) { // Kill thief
            ogre.attack(thief);
        }

        assertFalse(ogre.attack(thief)); // should be false as thief is dead
    }

    @Test
    public void getItemTest() {
        Hero billy = new Warrior("Billy");
        billy.getItem(new Item(ItemType.HEALING_POTION));
        assertEquals("Name: Billy HP: 125 Healing Potions: 1 Vision Potions: 0 Inventory: [Healing Potion]", billy.toString());
        billy.getItem(new Item(ItemType.VISION_POTION));
        assertEquals("Name: Billy HP: 125 Healing Potions: 1 Vision Potions: 1 Inventory: [Healing Potion, Vision Potion]", billy.toString());
        billy.getItem(new Item(ItemType.HEALING_POTION));
        assertEquals("Name: Billy HP: 125 Healing Potions: 2 Vision Potions: 1 Inventory: [Healing Potion, Vision Potion, Healing Potion]", billy.toString());
    }

    @Test
    public void giveItemTest() {
        Hero billy = new Warrior("Billy");
        billy.giveItem(ItemType.HEALING_POTION);
        assertEquals("Name: Billy HP: 125 Healing Potions: 1 Vision Potions: 0 Inventory: [Healing Potion]", billy.toString());
        billy.giveItem(ItemType.VISION_POTION);
        assertEquals("Name: Billy HP: 125 Healing Potions: 1 Vision Potions: 1 Inventory: [Healing Potion, Vision Potion]", billy.toString());
        billy.giveItem(ItemType.HEALING_POTION);
        assertEquals("Name: Billy HP: 125 Healing Potions: 2 Vision Potions: 1 Inventory: [Healing Potion, Vision Potion, Healing Potion]", billy.toString());
    }
}
