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
        assertEquals("Name: TestName HP: 75 Healing Potions: 0 Vision Potions: 0 Inventory: []"
                , priestess.toString());
    }

    @Test
    public void testSpecSkillOdds() {
        Priestess priestess = new Priestess("TestName");
        Ogre ogre = new Ogre();
        // Priestess always heals
        assertTrue(priestess.specialSkill(ogre));
    }
}
