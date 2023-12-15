package model;

/**
 * MonsterFactory crates a monster via the createMonster for the given MonsterType.
 */
public class MonsterFactory {
    public Monster createMonster(final MonsterType theMonsterType) {
        return switch (theMonsterType) {
            case GREMLIN -> new Gremlin();
            case OGRE -> new Ogre();
            case SKELETON -> new Skeleton();
            case NONE -> null;
        };
    }
}
