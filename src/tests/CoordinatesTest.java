package tests;

import model.Coordinates;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    void compareTo() {
        Coordinates coordinates1 = new Coordinates(1, 2, 3);
        Coordinates coordinates2 = new Coordinates(1, 2, 4);
        assertTrue(coordinates1.compareTo(coordinates2) < 0);
        assertTrue(coordinates2.compareTo(coordinates1) > 0);
        assertEquals(0, coordinates1.compareTo(new Coordinates(1, 2, 3)));
    }

    @Test
    void testEquals() {
        Coordinates coordinates1 = new Coordinates(1, 2, 3);
        Coordinates coordinates2 = new Coordinates(1, 2, 3);
        Coordinates coordinates3 = new Coordinates(1, 2, 4);
        assertEquals(coordinates1, coordinates2);
        assertNotEquals(coordinates1, coordinates3);
    }

    @Test
    void testHashCode() {
        Coordinates coordinates1 = new Coordinates(1, 2, 3);
        Coordinates coordinates2 = new Coordinates(1, 2, 3);
        Coordinates coordinates3 = new Coordinates(1, 2, 4);
        assertEquals(coordinates1.hashCode(), coordinates2.hashCode());
        assertNotEquals(coordinates1.hashCode(), coordinates3.hashCode());
    }

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        Coordinates originalCoordinates = new Coordinates(1, 2, 3);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(originalCoordinates);
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            Coordinates deserializedCoordinates = (Coordinates) objectInputStream.readObject();

            assertEquals(originalCoordinates, deserializedCoordinates); // are they equal?
        }
    }

}

