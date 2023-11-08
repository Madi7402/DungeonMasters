package Tests;
import model.Ogre;
import model.Thief;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class HeroTest {
    @RepeatedTest(20)
    public void takeDamageTest() {
        Thief thief = new Thief("TestName");
        int healthPoints = thief.getMyHealthPoints();
        Ogre ogre = new Ogre();
        boolean wasAttacked = ogre.attack(thief);
        assertTrue((wasAttacked && thief.getMyHealthPoints() < healthPoints) ||
                (!wasAttacked && thief.getMyHealthPoints() == healthPoints));
    }

    @Test
    public void takeDamageDeadTest() {
        Thief thief = new Thief("TestName");
        Ogre ogre = new Ogre();
        while (thief.getMyHealthPoints() > 0) { // Kill thief
            ogre.attack(thief);
        }

        assertFalse(ogre.attack(thief)); // should be false as thief is dead
    }
}
