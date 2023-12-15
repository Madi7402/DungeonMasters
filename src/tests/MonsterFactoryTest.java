package tests;

import model.Monster;
import model.MonsterFactory;
import model.MonsterType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MonsterFactoryTest {
    private static MonsterFactory mf;
    @BeforeAll
    public static void beforeAll() {
        mf = new MonsterFactory();
    }

    @Test
    public void createGremlin() {
        Monster gremlin = mf.createMonster(MonsterType.GREMLIN);
        assertEquals("Level 1 Character: model.Gremlin Health: 70 Heal Chance: 40.0",
                gremlin.toString());
    }

    @Test
    public void createOgre() {
        Monster ogre = mf.createMonster(MonsterType.OGRE);
        assertEquals("Level 1 Character: model.Ogre Health: 200 Heal Chance: 10.0",
                ogre.toString());
    }

    @Test
    public void createSkeleton() {
        Monster skeleton = mf.createMonster(MonsterType.SKELETON);
        assertEquals("Level 1 Character: model.Skeleton Health: 100 Heal Chance: 30.0",
                skeleton.toString());
    }

    @Test
    public void createNone() {
        Monster noneMonster = mf.createMonster(MonsterType.NONE);
        assertNull(noneMonster);
    }
}
