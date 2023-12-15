package tests;
import static org.junit.jupiter.api.Assertions.assertThrows;

import model.Priestess;
import org.junit.jupiter.api.Test;


public class DungeonCharacterTest {
    @Test
    public void setHealthPointsTest() {
        final Priestess priestess = new Priestess("TestName");
        assertThrows(IllegalArgumentException.class, () -> priestess.setMyHealthPoints(-1));
    }
}
