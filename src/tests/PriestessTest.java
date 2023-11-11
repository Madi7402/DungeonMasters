package tests;
import model.Ogre;
import model.Priestess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class PriestessTest {
    @Test
    public void constructorTest() {
        Priestess priestess = new Priestess("TestName");
        assertNotNull(priestess);
    }

    @Test
    public void toStringTest() {
        Priestess priestess = new Priestess("TestName");
        assertEquals("Level 1 Character: model.Priestess Health: 75 Inventory: []", priestess.toString());
    }

    @Test
    public void testSpecSkillOdds() {
        Priestess priestess = new Priestess("TestName");
        Ogre ogre = new Ogre();
        int trueCount = 0; // expect ~100%
        int iterations = 10_000;
        for (int i = 0; i < iterations; i++) {
            if(priestess.specialSkill(ogre)) {
                trueCount++;
            }
        }

        // Expect 100% true
        assertEquals(trueCount, iterations);
    }
}
