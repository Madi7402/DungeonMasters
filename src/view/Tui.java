package view;
import model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;

import static controller.PropertyChangeEnableFight.DEATH;

public class Tui implements PropertyChangeListener {
    public static void main(String[]theArgs) throws IOException, ClassNotFoundException {
        Tui tui = new Tui();
//        tui.fightSim();
        tui.saveLoadConcept();
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
        System.out.println("start");
        MonsterFactory mf = new MonsterFactory();
        Monster ogre = mf.createMonster(MonsterType.OGRE);
        ogre.addPropertyChangeListener(this);
        Hero hero = new Thief("Billy");
        hero.addPropertyChangeListener(this);
        while (hero.getMyHealthPoints() > 0 && ogre.getMyHealthPoints() > 0) {
            System.out.println(hero.getMyHealthPoints());
            hero.attack(ogre);
            ogre.attack(hero);
        }
        System.out.println("end");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.toString()); // TODO custom actions depending on event
        if (DEATH.equals(evt.getPropertyName())) {
            DungeonCharacter source = (DungeonCharacter) evt.getSource();
            System.out.println(evt.getSource().getClass().getName() + " " + source.getMyName() + " DIED!");
        }
    }
}
