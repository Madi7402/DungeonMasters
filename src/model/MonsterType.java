package model;

import java.util.Random;

/**
 * The Types of Monsters we can select via the MonsterFactory.
 */
public enum MonsterType {
    GREMLIN,
    OGRE,
    SKELETON,
    NONE;

    public static MonsterType getRandomMonster(final Random theRandom) {
        if (theRandom.nextDouble() < 0.33) {
            return MonsterType.SKELETON;
        } else if (theRandom.nextDouble() < 0.66) {
            return MonsterType.GREMLIN;
        } else {
            return MonsterType.OGRE;
        }
    }
}
