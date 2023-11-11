package model;

/**
 * MonsterFactory crates a monster via  the createMonster for the given MonsterType.
 */
public class MonsterFactory {
    public Monster createMonster(final MonsterType theMonsterType) {
        return switch (theMonsterType) {
            case MonsterType.GREMLIN -> new Gremlin();
            case MonsterType.OGRE -> new Ogre();
            case MonsterType.SKELETON -> new Skeleton();
        };
    }
}
