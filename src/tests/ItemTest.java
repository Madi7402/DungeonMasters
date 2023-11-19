package tests;

import model.Item;
import model.ItemType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    @Test
    public void constructItemTest() {
        Item pillar = new Item(ItemType.PILLAR_ABSTRACTION);
        assertEquals("Pillar of Abstraction: \"Stay Hidden.\"", pillar.toString());
    }

    @Test
    public void getNameTest() {
        Item pillar = new Item(ItemType.PILLAR_ABSTRACTION);
        assertEquals("Pillar of Abstraction", pillar.getName());
    }

    @Test
    public void getNameDescription() {
        Item pillar = new Item(ItemType.PILLAR_ABSTRACTION);
        assertEquals("Stay Hidden.", pillar.getDescription());
    }
}
