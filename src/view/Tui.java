package view;
import model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;

import static controller.PropertyChangeEnableFight.*;
import static controller.PropertyChangeEnableHero.INVENTORY_ACTION;

public class Tui implements PropertyChangeListener {
    public static void main(String[]theArgs) {
        Tui tui = new Tui();
        tui.fightSim();
//        tui.saveLoadConcept();
    }

    private void saveLoadConcept() throws IOException, ClassNotFoundException {
        Hero hero = new Thief("Billy");
        System.out.println("Original hero: " + hero);
        MonsterFactory mf = new MonsterFactory();
        Monster ogre = mf.createMonster(MonsterType.OGRE);

        ogre.addPropertyChangeListener(this);
        hero.addPropertyChangeListener(this);

        while (hero.getMyHealthPoints() >= 75) {
            ogre.attack(hero);
        }
        System.out.println("Original hero: " + hero);

        // Save
        FileOutputStream outputFile = new FileOutputStream("savedhero.txt");
        ObjectOutputStream outputObj = new ObjectOutputStream(outputFile);
        outputObj.writeObject(hero);
        outputObj.flush();
        outputObj.close();

        // Load
        FileInputStream inputFile = new FileInputStream("savedhero.txt");
        ObjectInputStream inputObj = new ObjectInputStream(inputFile);
        Hero newhero = (Hero) inputObj.readObject();
        inputObj.close();

        System.out.println("New Hero: " + newhero.toString());
    }

    public void fightSim() {
        MonsterFactory mf = new MonsterFactory();
        Monster ogre = mf.createMonster(MonsterType.OGRE);
        ogre.addPropertyChangeListener(this);
        Hero hero = new Thief("Billy");
        hero.addPropertyChangeListener(this);
        while (hero.getMyHealthPoints() > 0 && ogre.getMyHealthPoints() > 0) {
            hero.attack(ogre);
            ogre.attack(hero);
        }
        hero.giveItem(ItemType.PILLAR_ENCAPSULATION);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DungeonCharacter source = (DungeonCharacter) evt.getSource();
        String name = source.getMyName();
        switch (evt.getPropertyName()) {
            case DEATH -> System.out.println(name + " DIED!");
            case ATTACK -> System.out.println(name + " Attacked!");
            case TAKE_DAMAGE -> System.out.println(name + " took damage!");
            case HEALTH_CHANGED
                    -> System.out.println(name + "'s health changed from "
                    + evt.getOldValue() + " to " + evt.getNewValue());
            case ATTACK_BLOCK -> System.out.println(name + " blocked the attack.");
            case ATTACK_MISS -> System.out.println(name + " missed!");
            case INVENTORY_ACTION -> System.out.println("Hero Inventory Changed");
            default -> System.err.println("Unhandled Event: " + evt.getPropertyName());
        }
    }
}
