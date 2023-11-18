package view;
import model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;

import static controller.PropertyChangeEnableFight.*;

public class Tui implements PropertyChangeListener {
    public static void main(String[]theArgs) throws IOException, ClassNotFoundException {
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
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DungeonCharacter source = (DungeonCharacter) evt.getSource();
        if (DEATH.equals(evt.getPropertyName())) {
            System.out.println(evt.getSource().getClass().getName() + " " + source.getMyName() + " DIED!");
        } else if (ATTACK.equals(evt.getPropertyName())) {
            System.out.println(evt.getSource().getClass().getName() + " " + source.getMyName() + " Attacked!");
        } else if (HEALTH_CHANGED.equals(evt.getPropertyName())) {
            System.out.println(evt.getSource().getClass().getName() + " " + source.getMyName() + " Health Changed!"
                    + " changed health From: " + evt.getOldValue() + " to " + evt.getNewValue());
        } else if (ATTACK_BLOCK.equals(evt.getPropertyName())) {
            System.out.println(evt.getSource().getClass().getName() + " " + source.getMyName() + " Blocked the Attack!");
        } else if (ATTACK_MISS.equals(evt.getPropertyName())) {
            System.out.println(evt.getSource().getClass().getName() + " " + source.getMyName() + " Missed!");
        } else {
            System.out.println(evt.toString()); // TODO custom actions depending on event
        }
    }
}
