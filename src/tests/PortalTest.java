package tests;

import model.Portal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class PortalTest {

    @Test
    public void testPortalValues() {
        assertEquals(3, Portal.values().length);

        assertEquals("NONE", Portal.NONE.name());
        assertEquals("ENTRANCE", Portal.ENTRANCE.name());
        assertEquals("EXIT", Portal.EXIT.name());

        assertEquals(Portal.NONE, Portal.valueOf("NONE"));
        assertEquals(Portal.ENTRANCE, Portal.valueOf("ENTRANCE"));
        assertEquals(Portal.EXIT, Portal.valueOf("EXIT"));
    }
}
