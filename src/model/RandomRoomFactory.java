package model;

import java.util.EnumSet;
import java.util.Random;

/**
 * A factory for creating randomly generated rooms with various features such as pits, potions, monsters, and doors.
 * The likelihood of each feature is determined by specified percentage constants.
 * Coordinates for the generated rooms are provided during the creation process.
 * Extends the AbstractRoomFactory class.
 *
 * @author Jonathan Abrams, Martha Emerson, Madison Pope
 */
public class RandomRoomFactory extends AbstractRoomFactory {
    /**
     * The likelihood of generating a pit in a room.
     * Value between 0.0 (no pit) and 1.0 (always pit).
     */
    private static final double PIT_PERCENTAGE = 0.1;
    /**
     * The likelihood of generating a healing potion in a room.
     * Value between 0.0 (no healing potion) and 1.0 (always healing potion).
     */
    private static final double HEALING_POTION_PERCENTAGE = 0.1;
    /**
     * The likelihood of generating a vision potion in a room.
     * Value between 0.0 (no vision potion) and 1.0 (always vision potion).
     */
    private static final double VISION_POTION_PERCENTAGE = 0.1;
    /**
     * The likelihood of generating a monster in a room.
     * Value between 0.0 (no monster) and 1.0 (always monster).
     */
    private static final double MONSTER_PERCENTAGE = 0.02;
    /**
     * The likelihood of generating a door to the north in a room.
     * Value between 0.0 (no north door) and 1.0 (always north door).
     */
    private static final double NORTH_DOOR_PERCENTAGE = 0.8;
    /**
     * The likelihood of generating a door to the south in a room.
     * Value between 0.0 (no south door) and 1.0 (always south door).
     */
    private static final double SOUTH_DOOR_PERCENTAGE = 0.8;
    /**
     * The likelihood of generating a door to the east in a room.
     * Value between 0.0 (no east door) and 1.0 (always east door).
     */
    private static final double EAST_DOOR_PERCENTAGE = 0.8;
    /**
     * The likelihood of generating a door to the west in a room.
     * Value between 0.0 (no west door) and 1.0 (always west door).
     */
    private static final double WEST_DOOR_PERCENTAGE = 0.8;

    /**
     * Generates a random room with random features.
     *
     * @param theCoordinates The coordinates for the generated room.
     * @return A randomly generated room.
     */
    // using
    public Room createRoom(final Coordinates theCoordinates) {
        Random random = new Random();

        boolean hasPit = random.nextDouble() < PIT_PERCENTAGE;
        boolean hasHealingPotion = random.nextDouble() < HEALING_POTION_PERCENTAGE;
        boolean hasVisionPotion = random.nextDouble() < VISION_POTION_PERCENTAGE;
        boolean hasMonster = random.nextDouble() < MONSTER_PERCENTAGE;
        var monster = MonsterType.NONE;

        if (hasMonster) {
            monster = generateRandomMonster(random);
        }

        var doors = generateRandomDoors(random);

        return new Room(hasPit, hasHealingPotion, hasVisionPotion, monster, doors, theCoordinates);
    }

    /**
     * Generates a random monster type based on specified probabilities.
     *
     * @param theRandom The random number generator.
     * @return A randomly selected monster type.
     */
    private MonsterType generateRandomMonster(final Random theRandom) {
        if (theRandom.nextDouble() < 0.33) {
            return MonsterType.SKELETON;
        } else if (theRandom.nextDouble() < 0.66) {
            return MonsterType.GREMLIN;
        } else {
            return MonsterType.OGRE;
        }
    }

    /**
     * Generates a random set of doors (directions) for a room.
     * The likelihood of having a door in each direction is determined by a random number generator.
     *
     * @param theRandom The random number generator used for door generation.
     * @return An EnumSet containing randomly generated doors.
     */
    private EnumSet<Direction> generateRandomDoors(final Random theRandom) {
        var doors = EnumSet.noneOf(Direction.class);

        if (theRandom.nextDouble() < NORTH_DOOR_PERCENTAGE) {
            doors.add(Direction.NORTH);
        }
        if (theRandom.nextDouble() < SOUTH_DOOR_PERCENTAGE) {
            doors.add(Direction.SOUTH);
        }
        if (theRandom.nextDouble() < EAST_DOOR_PERCENTAGE) {
            doors.add(Direction.EAST);
        }
        if (theRandom.nextDouble() < WEST_DOOR_PERCENTAGE) {
            doors.add(Direction.WEST);
        }
        return doors;
    }
}
