package tests;

import model.Item;
import model.ItemType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    @Test
    public void constructItemTest() {
        Item pillar = new Item(ItemType.PILLAR_ABSTRACTION);
        assertEquals("Pillar of Abstraction", pillar.toString());
    }

    @Test
    public void getNameTest() {
        Item pillar = new Item(ItemType.PILLAR_ABSTRACTION);
        assertEquals("Pillar of Abstraction", pillar.getName());
    }

    @Test
    public void getNameDescriptionTest() {
        Item pillar = new Item(ItemType.PILLAR_ABSTRACTION);
        assertEquals("Stay Hidden.", pillar.getDescription());
    }

    @Test
    public void getMyStatMultiplierTest() {
        Item pillar = new Item(ItemType.PILLAR_ABSTRACTION);
        assertEquals(1.15, pillar.getMyStatMultiplier());
    }

    @Test
    public void getMyIconTest() {
        Item pillar = new Item(ItemType.PILLAR_ABSTRACTION);
        assertNull(pillar.getMyIcon());
        // if we were to add icons, we would also check for a non-null case
    }
}
