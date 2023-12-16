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
        assertThrows(RuntimeException.class, () -> billy.getItem(null));
    }

    @Test
    public void useItemTest() {
        Hero billy = new Warrior("Billy");

        // Empty inventory
        assertThrows(IllegalArgumentException.class, () -> billy.useItem(0));
        assertThrows(IllegalArgumentException.class, () -> billy.useItem(1));

        // Invalid index
        assertThrows(IllegalArgumentException.class, () -> billy.useItem(-1));

        // index Greater than inventory size
        billy.giveItem(ItemType.HEALING_POTION);
        assertThrows(IllegalArgumentException.class, () -> billy.useItem(1));

        // Use the healing potion
        assertTrue(billy.useItem(0)); // inventory should now be empty

        // Use the vision potion
        billy.giveItem(ItemType.VISION_POTION);
        assertTrue(billy.useItem(0));

        // Try using a non-removable item
        billy.giveItem(ItemType.PILLAR_INHERITANCE);
        assertFalse(billy.useItem(0));

        // Use item by reference
        assertFalse(billy.useItem(null));
        Item testItem = new Item(ItemType.HEALING_POTION);

        // Billy doesn't have the item yet
        assertFalse(billy.useItem(testItem));

        // Now Billy has the item
        billy.getItem(testItem);
        assertTrue(billy.useItem(testItem));
    }

    @Test
    public void getInventoryTest() {
        Warrior billy = new Warrior("Billy");
        assertNotNull(billy.getInventory());
        assertEquals(0, billy.getInventory().size());
        billy.giveItem(ItemType.HEALING_POTION);
        assertEquals(1, billy.getInventory().size());
    }

    @Test
    public void hitPitTest() {
        Priestess priestess = new Priestess("testPri");
        assertEquals(75, priestess.getMyHealthPoints());
        priestess.hitPit();
        assertEquals(60, priestess.getMyHealthPoints());
    }

    @Test
    public void getStatsStringTest() {
        Priestess priestess = new Priestess("testPri");
        assertNotNull(priestess.getStatsString()); // Will test output in CharStats
        assertFalse(priestess.getStatsString().isEmpty());
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
