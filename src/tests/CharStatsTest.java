package tests;

import model.Priestess;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharStatsTest {
    @Test
    public void toStringTest() {
        Priestess priestess = new Priestess("TestName");
        assertEquals("Max Health: 75\nAttack Speed: 5\nHit Chance: 70.0", priestess.getStatsString());
    }
}
