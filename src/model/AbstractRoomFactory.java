package model;

/**
 * Abstract factory class for creating rooms.
 * Concrete subclasses should implement the method to create a room with specific characteristics.
 */
public abstract class AbstractRoomFactory {

    /**
     * Creates a room with specific characteristics based on the given theCoordinates.
     *
     * @param theCoordinates The theCoordinates of the room to be created.
     * @return The created room.
     */
    protected abstract Room createRoom(final Coordinates theCoordinates);
}