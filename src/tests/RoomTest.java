package tests;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void testEmptyRoomInitialization() {
        Room room = new Room();

        assertEquals(Portal.NONE, room.getPortal());
        assertFalse(room.isVisited());
        assertFalse(room.hasMonster());
        assertFalse(room.hasPit());
        assertFalse(room.hasPillar());
        assertFalse(room.hasHealingPotion());
        assertFalse(room.hasVisionPotion());
        assertEquals(new Coordinates(0, 0, 0), room.getCoordinate());
        assertEquals(EnumSet.noneOf(Direction.class), room.getDoors());
    }

    @Test
    void testRoomWithFeaturesInitialization() {
        Coordinates coordinates = new Coordinates(1, 2, 3);
        EnumSet<Direction> doors = EnumSet.of(Direction.NORTH, Direction.EAST);

        Room room = new Room(true, true, true, MonsterType.GREMLIN, doors, coordinates);

        assertEquals(Portal.NONE, room.getPortal());
        assertFalse(room.isVisited());
        assertTrue(room.hasMonster());
        assertFalse(room.hasPit());
        assertFalse(room.hasPillar());
        assertTrue(room.hasHealingPotion());
        assertTrue(room.hasVisionPotion());
        assertEquals(coordinates, room.getCoordinate());
        assertEquals(doors, room.getDoors());
        assertEquals(MonsterType.GREMLIN, room.getMyMonsterType());
        assertEquals(2, room.takeEquipableItems().size());
    }

    @Test
    void testRoomWithDoorsInitialization() {
        EnumSet<Direction> doors = EnumSet.of(Direction.WEST, Direction.EAST);

        Room room = new Room(false, false, false, MonsterType.NONE, doors, new Coordinates(0, 0, 0));

        assertEquals(Portal.NONE, room.getPortal());
        assertFalse(room.isVisited());
        assertFalse(room.hasMonster());
        assertFalse(room.hasPit());
        assertFalse(room.hasPillar());
        assertFalse(room.hasHealingPotion());
        assertFalse(room.hasVisionPotion());
        assertEquals(new Coordinates(0, 0, 0), room.getCoordinate());
        assertEquals(doors, room.getDoors());
    }

    @Test
    public void testAddNeighborsInDifferentDirections() {
        Room room1 = new Room();
        Room room2 = new Room();

        room1.trySetNeighbor(room2, Direction.NORTH);
        room1.trySetNeighbor(room2, Direction.SOUTH);
        room1.trySetNeighbor(room2, Direction.EAST);
        room1.trySetNeighbor(room2, Direction.WEST);

        assertTrue(room1.getNeighbors().contains(room2));
        assertTrue(room2.getNeighbors().contains(room1));
    }

    @Test
    public void testRemoveDoorsAndUpdateNeighbors() {
        Room room1 = new Room();
        Room room2 = new Room();

        room1.trySetNeighbor(room2, Direction.NORTH);
        room1.trySetNeighbor(room2, Direction.EAST);

        room1.tryRemoveDoor(Direction.NORTH);

        assertFalse(room1.getDoors().contains(Direction.NORTH));

        assertFalse(room1.getNeighbors().contains(room2));
        assertFalse(room2.getNeighbors().contains(room1));
    }

    @Test
    public void testTrySetNeighborWithMismatchedDoors() {
        Room room1 = new Room();
        Room room2 = new Room();

        room1.trySetNeighbor(room2, Direction.NORTH);
        room1.trySetNeighbor(room2, Direction.SOUTH);

        boolean result = room1.trySetNeighbor(room2, Direction.EAST);

        assertFalse(result);

        room1.trySetNeighbor(room2, Direction.EAST);
        room1.trySetNeighbor(room2, Direction.WEST);

        result = room1.trySetNeighbor(room2, Direction.NORTH);

        assertFalse(result);
    }

    @Test
    public void testAddItemsToRoom() {
        Room room = new Room();

        Item healingPotion = new Item(ItemType.HEALING_POTION);
        Item visionPotion = new Item(ItemType.VISION_POTION);

        room.addItem(healingPotion);
        room.addItem(visionPotion);

        assertTrue(room.hasHealingPotion());
        assertTrue(room.hasVisionPotion());
    }

    @Test
    public void testHasMultipleItems() {
        Room room = new Room();

        room.addItem(new Item(ItemType.HEALING_POTION));
        room.addItem(new Item(ItemType.VISION_POTION));

        assertTrue(room.hasMultipleItems());
    }

    @Test
    public void testGenerateGraphicalRepresentation() {
        Room room = new Room(true, false, true, MonsterType.GREMLIN,
                EnumSet.of(Direction.NORTH, Direction.EAST), new Coordinates(0, 0, 0));

        String graphicalRepresentation = room.toString();

        assertEquals("*-*\n*M|\n***\n", graphicalRepresentation);
    }

    @Test
    public void testSetAndGetPortalType() {
        Room room = new Room();

        room.setPortal(Portal.ENTRANCE);

        assertEquals(Portal.ENTRANCE, room.getPortal());
    }

    @Test
    public void testSetAndGetVisitationStatus() {
        Room room = new Room();

        room.setVisited(true);

        assertTrue(room.isVisited());
    }

    @Test
    public void testGetCoordinate() {
        Coordinates testCoordinates = new Coordinates(1, 2, 3);
        Room room = new Room();
        room = new Room(false, false, false, MonsterType.NONE,
                EnumSet.noneOf(Direction.class), testCoordinates);

        assertEquals(testCoordinates, room.getCoordinate());
    }

    @Test
    public void testHasMonster() {
        Room roomWithMonster = new Room();
        roomWithMonster.setMonsterType(MonsterType.SKELETON);

        Room roomWithoutMonster = new Room();
        roomWithoutMonster.setMonsterType(MonsterType.NONE);

        assertTrue(roomWithMonster.hasMonster());
        assertFalse(roomWithoutMonster.hasMonster());
    }

    @Test
    public void testSerialization() {
        Room originalRoom = new Room(true, false, true, MonsterType.SKELETON,
                EnumSet.of(Direction.NORTH, Direction.EAST), new Coordinates(1, 2, 3));

        byte[] serializedRoom = serialize(originalRoom);

        Room deserializedRoom = deserialize(serializedRoom);

        assertEquals(originalRoom, deserializedRoom);
    }

    private byte[] serialize(Room room) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(room);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Serialization error: " + e.getMessage(), e);
        }
    }

    private Room deserialize(byte[] data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInput in = new ObjectInputStream(bis)) {
            return (Room) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Deserialization error: " + e.getMessage(), e);
        }
    }

    @Test
    public void testEmptyRoom() {
        Room room = new Room();

        assertNull(room.getPortal());
        assertFalse(room.isVisited());
        assertNull(room.getCoordinate());
        assertTrue(room.getNeighbors().isEmpty());
        assertTrue(room.getDoors().isEmpty());
        assertEquals(MonsterType.NONE, room.getMyMonsterType());
        assertFalse(room.hasMonster());
        assertFalse(room.hasPit());
        assertFalse(room.hasHealingPotion());
        assertFalse(room.hasVisionPotion());
        assertFalse(room.hasPillar());
    }

    @Test
    public void testRoomWithNoDoors() {
        Room room = new Room(false, false, false, MonsterType.NONE, EnumSet.noneOf(Direction.class), new Coordinates(0, 0, 0));

        assertTrue(room.getNeighbors().isEmpty());
        assertTrue(room.getDoors().isEmpty());
    }
}
