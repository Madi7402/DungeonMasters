package view;

import controller.PropertyChangeEnableFight;
import model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Tui implements PropertyChangeListener {
    public static void main(String[]theArgs) {
        Tui tui = new Tui();
        tui.fightSim();
    }

    public void fightSim() {
        System.out.println("start");
        MonsterFactory mf = new MonsterFactory();
        Monster ogre = mf.createMonster(MonsterType.OGRE);
        ogre.addPropertyChangeListener(this);
        Hero hero = new Thief("Billy");
        hero.addPropertyChangeListener(this);
        while (hero.getMyHealthPoints() > 0) {
            System.out.println(hero.getMyHealthPoints());
            ogre.attack(hero);
        }
        System.out.println("end");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.toString()); // TODO custom actions depending on event
    }
}
