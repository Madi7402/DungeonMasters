package tests;

import model.Direction;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    public void testGetOppositeDirection() {
        assertEquals(Direction.SOUTH, Direction.NORTH.getOppositeDirection());
        assertEquals(Direction.NORTH, Direction.SOUTH.getOppositeDirection());
        assertEquals(Direction.EAST, Direction.WEST.getOppositeDirection());
        assertEquals(Direction.WEST, Direction.EAST.getOppositeDirection());
    }

    @Test
    public void testGetXOffsetAndYOffset() {
        assertEquals(0, Direction.NORTH.getXOffset());
        assertEquals(-1, Direction.NORTH.getYOffset());

        assertEquals(0, Direction.SOUTH.getXOffset());
        assertEquals(1, Direction.SOUTH.getYOffset());

        assertEquals(1, Direction.EAST.getXOffset());
        assertEquals(0, Direction.EAST.getYOffset());

        assertEquals(-1, Direction.WEST.getXOffset());
        assertEquals(0, Direction.WEST.getYOffset());
    }

    @Test
    public void testEnumValues() {
        Direction[] values = Direction.values();
        assertEquals(4, values.length);
        assertTrue(Arrays.asList(values).contains(Direction.NORTH));
        assertTrue(Arrays.asList(values).contains(Direction.SOUTH));
        assertTrue(Arrays.asList(values).contains(Direction.EAST));
        assertTrue(Arrays.asList(values).contains(Direction.WEST));
    }

    @Test
    public void testToString() {
        assertEquals("NORTH", Direction.NORTH.toString());
        assertEquals("SOUTH", Direction.SOUTH.toString());
        assertEquals("EAST", Direction.EAST.toString());
        assertEquals("WEST", Direction.WEST.toString());
    }
}