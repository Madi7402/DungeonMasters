package tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import model.ItemType;
import model.Priestess;
import org.junit.jupiter.api.Test;


public class DungeonCharacterTest {
    @Test
    public void setHealthPointsTest() {
        final Priestess priestess = new Priestess("TestName");
        assertThrows(IllegalArgumentException.class, () -> priestess.setMyHealthPoints(-1));
    }

    @Test
    public void setNameTest() {
        final Priestess priestess = new Priestess("TestName");
        assertEquals("TestName", priestess.getMyName());
        priestess.setMyName("Jecka");
        assertEquals("Jecka", priestess.getMyName());
        assertThrows(IllegalArgumentException.class, () -> priestess.setMyName(null));
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
    public void healTest() { // TODO -JA: test failure
        final Priestess priestess = new Priestess("TestName");
        priestess.giveItem(ItemType.HEALING_POTION);
        if (priestess.useItem(0)) {
            priestess.removeItem(0);
        }
        assertEquals(100, priestess.getMyHealthPoints());
    }
}
