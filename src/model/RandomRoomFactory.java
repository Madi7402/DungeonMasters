package model;

import java.util.EnumSet;
import java.util.Random;

public class RandomRoomFactory extends AbstractRoomFactory {

    private static final double PIT_PERCENTAGE = 0.1;

    private static final double HEALING_POTION_PERCENTAGE = 0.1;

    private static final double VISION_POTION_PERCENTAGE = 0.1;

    private static final double NORTH_DOOR_PERCENTAGE = 0.8;

    private static final double SOUTH_DOOR_PERCENTAGE = 0.8;

    private static final double EAST_DOOR_PERCENTAGE = 0.8;

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

        var doors = generateRandomDoors(random);

        return new Room(hasPit, hasHealingPotion, hasVisionPotion, doors, theCoordinates);
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
