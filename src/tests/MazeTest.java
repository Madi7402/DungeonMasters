package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import model.AbstractRoomFactory;
import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MazeTest {

    private AbstractRoomFactory myProceduralRoomFactory; // TODO - write the test
    // just make something simple and stupid to print every type of item

    private AbstractRoomFactory myRandomRoomFactory;
    @BeforeEach
    public void beforeEach() {
        myProceduralRoomFactory = new FakeRoomFactory();
        myRandomRoomFactory = new RandomRoomFactory();
    }

    @Test
    public void mazeTest() {
        final var maze = new Maze(myRandomRoomFactory, 3, 3);
        System.out.println(maze);
        assertNotNull(maze.toString());
    }

    // TODO - at least one test with the random room factory (needs to complete, and that it *can* print the string)
    // ^^^ maybe make it assert not null or not empty
    // TODO - make a procedural room factory
    // ^^ do the tests with toString = ... (must print something exactly)

    @Test
    void testMazeInitialization() {
        Maze maze = new Maze(myRandomRoomFactory, 5, 5);

        assertNotNull(maze);
        assertEquals(5, maze.getWidth());
        assertEquals(5, maze.getHeight());
    }

    @Test
    void testGetStartingRoom() {
        Maze maze = new Maze(myRandomRoomFactory, 5, 5);

        Room startingRoom = maze.getStartingRoom();

        assertNotNull(startingRoom);
        assertEquals(Portal.ENTRANCE, startingRoom.getPortal());
    }

    @Test
    void testGetRoom() {
        Maze maze = new Maze(myRandomRoomFactory, 5, 5);

        Coordinates coordinates = new Coordinates(0, 0, 0);
        Room room = maze.getRoom(coordinates);

        assertNotNull(room);
        assertEquals(coordinates, room.getCoordinate());
    }
}