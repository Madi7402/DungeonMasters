package tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import model.AbstractRoomFactory;
import model.Maze;
import model.RandomRoomFactory;
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
        assertNotNull(maze.toString());
    }

    // TODO - at least one test with the random room factory (needs to complete, and that it *can* print the string)
    // ^^^ maybe make it assert not null or not empty
    // TODO - make a procedural room factory
    // ^^ do the tests with toString = ... (must print something exactly)
}