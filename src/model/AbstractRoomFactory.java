package model;

/**
 * Abstract factory class for creating rooms.
 * Concrete subclasses should implement the method to create a room with specific characteristics.
 * This abstract class defines the contract for creating rooms, allowing for different room creation
 * strategies in subclasses.
 *
 * @author Jonathan Abrams, Martha Emerson, Madison Pope
 */
public abstract class AbstractRoomFactory { // TODO - fyi this does not need unit tests

    /**
     * Creates a room with specific characteristics based on the given theCoordinates.
     *
     * @param theCoordinates The theCoordinates of the room to be created.
     * @return The created room.
     */
    protected abstract Room createRoom(final Coordinates theCoordinates);
}