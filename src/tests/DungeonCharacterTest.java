package tests;

import model.ItemType;
import model.Priestess;
import model.StressTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DungeonCharacterTest {
    @Test
    public void setHealthPointsTest() {
        Priestess priestess = new Priestess("TestName");
        priestess.setMyHealthPoints(-1);
        assertEquals(0, priestess.getMyHealthPoints());

        priestess = new Priestess("TestName");
        priestess.setMyHealthPoints(0);
        assertEquals(0, priestess.getMyHealthPoints());

        priestess = new Priestess("TestName");
        priestess.setMyHealthPoints(1);
        assertEquals(1, priestess.getMyHealthPoints());
    }

    @Test
    public void setNameTest() {
        final Priestess priestess = new Priestess("TestName");
        assertEquals("TestName", priestess.getMyName());
        priestess.setMyName("Jecka");
        assertEquals("Jecka", priestess.getMyName());
        assertThrows(IllegalArgumentException.class, () -> priestess.setMyName(null));
        priestess.setMyName("");
        assertEquals("Unnamed Priestess", priestess.getMyName());
    }

    @Test
    public void setLevelTest() {
        final Priestess priestess = new Priestess("TestName");
        assertEquals(1, priestess.getMyLevel());
        priestess.setMyLevel(25);
        assertEquals(25, priestess.getMyLevel());
        assertThrows(IllegalArgumentException.class, () -> priestess.setMyLevel(-1));
    }

    @Test
    public void healTest() {
        final Priestess priestess = new Priestess("TestName");
        priestess.giveItem(ItemType.HEALING_POTION);
        priestess.useItem(0);
        assertEquals(100, priestess.getMyHealthPoints());
    }

    @Test
    public void getMyImageTest() {
        Priestess priestess = new Priestess("testPri");
        assertNotNull(priestess.getMyImage());
        StressTest stressTest = new StressTest("ok");
        assertNull(stressTest.getMyImage());
    }

    @Test
    public void getMyMaxHealPointsTest() {
        Priestess priestess = new Priestess("testPri");
        assertEquals(75, priestess.getMyMaxHealthPoints());
    }

    @Test
    public void getAttackSpeedTest() {
        Priestess priestess = new Priestess("testPri");
        assertEquals(5, priestess.getAttackSpeed());
    }

    @Test
    public void isAlive() {
        Priestess priestess = new Priestess("testPri");
        assertTrue(priestess.isAlive());
        for (int i = 0; i < 4; i++) {
            priestess.hitPit(); // each hit reduces health by 15
        }
        assertTrue(priestess.isAlive()); // Should have 15 health remaining

        priestess.hitPit(); // should kill
        assertFalse(priestess.isAlive());
    }
}
