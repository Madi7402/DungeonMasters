package tests;

import model.*;
import java.io.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveLoadTest {

    @Test
    public void SaveLoadHeroTest() throws IOException, ClassNotFoundException {
            // Create the original Hero object
            Hero originalHero = new Thief("Billy");
            MonsterFactory mf = new MonsterFactory();
            Monster ogre = mf.createMonster(MonsterType.OGRE);
            originalHero.giveItem(ItemType.HEALING_POTION);
            originalHero.giveItem(ItemType.PILLAR_ENCAPSULATION);
            // Simulate the attack until the health is below 75 to ensure we didn't just
            // recreate a new hero with the default health value
            while (originalHero.getMyHealthPoints() >= 75) {
                ogre.attack(originalHero);
            }

            // Save the original Hero's state to a byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputObj = new ObjectOutputStream(byteArrayOutputStream);
            outputObj.writeObject(originalHero);
            outputObj.flush();
            outputObj.close();

            // Deserialize the Hero object from the byte array
            ObjectInputStream inputObj = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            Hero deserializedHero = (Hero) inputObj.readObject();
            inputObj.close();

            // Compare the original and deserialized Hero objects
            assertEquals(originalHero.toString(), deserializedHero.toString());
    }
}
