package tests;

import model.AbstractRoomFactory;
import model.Coordinates;
import model.Room;

public class FakeRoomFactory extends AbstractRoomFactory {
    @Override
    protected Room createRoom(final Coordinates theCoordinates) {
        return null;
    }
}
