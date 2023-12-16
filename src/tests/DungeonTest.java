package tests;

import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonTest {
    private static DungeonAdventure myDungeonAdventure = null;
    private static Dungeon myTestDungeon = null;
    @BeforeAll
    static void setup() {
        myDungeonAdventure = new DungeonAdventure(new Priestess("testHero"));
        myTestDungeon = myDungeonAdventure.getMyDungeon();
    }
    @Test
    public void DungeonConstructorTest() {
        assertNotNull(myTestDungeon);
        assertThrows(IllegalArgumentException.class, () -> new Dungeon(new Priestess("testHero"), 3, 4));
    }

    @Test
    public void getMyCurrentRoomTest() {
        assertNotNull(myTestDungeon.getMyCurrentRoom());
    }

    @Test
    public void getMyMaze() {
        assertNotNull(myTestDungeon.getMyMaze());
    }

    @Test
    public void getMyCurrentCoordinatesTest() { // THIS MUST RUN BEFORE TESTING NAVIGATION (!)
        assertEquals(new Coordinates(0,0,9), myTestDungeon.getMyCurrentCoordinates());
    }

    @Test
    public void goDirectionTest() {
        // Go invalid direction
        assertFalse(myTestDungeon.goDirection(Direction.NORTH)); // edge of maze
        // Go valid direction
        List<Direction> doors = myTestDungeon.getMyCurrentRoom().getDoors().stream().toList();
        myTestDungeon.goDirection(doors.get(0));
        assertNotEquals(new Coordinates(0,0,9), myTestDungeon.getMyCurrentCoordinates());

        myTestDungeon.navigateToRoom(myTestDungeon.getMyMaze().getRoom(new Coordinates(0,9,0)));
        doors = myTestDungeon.getMyCurrentRoom().getDoors().stream().toList();
        if (doors.contains(Direction.NORTH)) { // Trigger exit check
            myTestDungeon.goDirection(Direction.NORTH);
            myTestDungeon.goDirection(Direction.SOUTH);
        } if (doors.contains(Direction.EAST)) {
            myTestDungeon.goDirection(Direction.EAST);
            myTestDungeon.goDirection(Direction.WEST);
        }
        System.err.println(myDungeonAdventure.getMyHero());
        assertTrue(myDungeonAdventure.getMyHero().toString().endsWith("Pillar of Encapsulation]")); // we got pillar
    }


}
