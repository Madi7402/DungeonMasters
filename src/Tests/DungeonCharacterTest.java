package Tests;
import model.Priestess;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DungeonCharacterTest {
    @Test
    public void setHealthPointsTest() {
        Priestess priestess = new Priestess("TestName");
        assertThrows(IllegalArgumentException.class, () -> priestess.setMyHealthPoints(-1));
    }
}
