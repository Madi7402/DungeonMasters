package model;

import java.util.EnumSet;
import java.util.Random;

public class RandomRoomFactory extends AbstractRoomFactory {

    private static final double PIT_PERCENTAGE = 0.1;

    private static final double HEALING_POTION_PERCENTAGE = 0.1;

    private static final double VISION_POTION_PERCENTAGE = 0.1;

    private static final double NORTH_DOOR_PERCENTAGE = 0.5;

    private static final double SOUTH_DOOR_PERCENTAGE = 0.5;

    private static final double EAST_DOOR_PERCENTAGE = 0.5;

    private static final double WEST_DOOR_PERCENTAGE = 0.5;

    /**
     * Generates a random room with random features.
     *
     * @param coordinates The coordinates for the generated room.
     * @return A randomly generated room.
     */
    // using
    public Room createRoom(Coordinates coordinates) {
        Random random = new Random();

        boolean hasPit = random.nextDouble() < PIT_PERCENTAGE;
        boolean hasHealingPotion = random.nextDouble() < HEALING_POTION_PERCENTAGE;
        boolean hasVisionPotion = random.nextDouble() < VISION_POTION_PERCENTAGE;

        var doors = generateRandomDoors(random);

        return new Room(hasPit, hasHealingPotion, hasVisionPotion, doors, coordinates);
    }

    /**
     * Generates a random set of doors (directions) for a room.
     * The likelihood of having a door in each direction is determined by a random number generator.
     *
     * @param random The random number generator used for door generation.
     * @return An EnumSet containing randomly generated doors.
     */
    private EnumSet<Direction> generateRandomDoors(Random random) {
        var doors = EnumSet.noneOf(Direction.class);

        if (random.nextDouble() < NORTH_DOOR_PERCENTAGE) {
            doors.add(Direction.NORTH);
        }
        if (random.nextDouble() < SOUTH_DOOR_PERCENTAGE) {
            doors.add(Direction.SOUTH);
        }
        if (random.nextDouble() < EAST_DOOR_PERCENTAGE) {
            doors.add(Direction.EAST);
        }
        if (random.nextDouble() < WEST_DOOR_PERCENTAGE) {
            doors.add(Direction.WEST);
        }
        return doors;
    }
}