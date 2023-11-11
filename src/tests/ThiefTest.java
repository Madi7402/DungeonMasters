package tests;
import model.Ogre;
import model.Thief;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ThiefTest {
    @Test
    public void constructorTest() {
        Thief thief = new Thief("TestName");
        assertNotNull(thief);
    }

    @Test
    public void toStringTest() {
        Thief thief = new Thief("TestName");
        assertEquals("Level 1 Character: model.Thief Health: 75 Inventory: []", thief.toString());
    }

    @Test
    public void testSpecSkillOdds() {
        Thief thief = new Thief("TestName");
        Ogre ogre = new Ogre();
        int falseCount = 0; // expect ~20%
        int iterations = 10_000;
        for (int i = 0; i < iterations; i++) {
            if(!thief.specialSkill(ogre)) {
                falseCount++;
            }
        }

        int expectedFalse = (int) (0.2 * iterations);

        // Expect less than 20% false (with 10% margin of error)
        assertTrue(falseCount < (expectedFalse + iterations * 0.1));
    }
}
