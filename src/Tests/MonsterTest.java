package Tests;
import model.Ogre;
import model.Thief;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class MonsterTest {
    @Test
    public void takeDamageDeadTest() {
        Thief thief = new Thief("TestName");
        Ogre ogre = new Ogre();
        while (ogre.getMyHealthPoints() > 0) { // Kill Ogre
            thief.attack(ogre);
        }

        assertFalse(thief.attack(ogre)); // should be false as Ogre is dead
    }
    @Test
    public void toStringTest() {
        Ogre ogre = new Ogre();
        assertEquals("Level 1 Character: model.Ogre Health: 200 Heal Chance: 10.0", ogre.toString());
    }
}
