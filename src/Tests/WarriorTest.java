package Tests;

import model.Ogre;
import model.Warrior;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WarriorTest {
    @Test
    public void constructorTest() {
        Warrior warrior = new Warrior("TestName");
        assertNotNull(warrior);
    }

    @Test
    public void toStringTest() {
        Warrior warrior = new Warrior("TestName");
        assertEquals("Level 1 Character: model.Warrior Health: 125 Inventory: []", warrior.toString());
    }

    @Test
    public void testSpecSkillOdds() {
        Warrior warrior = new Warrior("TestName");
        Ogre ogre = new Ogre();
        int trueCount = 0; // expect ~40%
        int iterations = 10_000;
        for (int i = 0; i < iterations; i++) {
            if(warrior.specialSkill(ogre)) {
                trueCount++;
            }
        }

        int expectedTrue = (int) (0.4 * iterations);
        // Except 40% success w/ 10% margin
        assertTrue(trueCount < (expectedTrue + iterations * 0.1));
    }
}
