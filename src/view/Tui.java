package view;
import model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.Scanner;

import static controller.PropertyChangeEnableFight.*;
import static controller.PropertyChangeEnableHero.INVENTORY_ACTION;

public class Tui implements PropertyChangeListener {
    public static void main(String[]theArgs) {
        Tui tui = new Tui();
        tui.generateDungeon();
        tui.loadDungeonAdventure();
//        tui.fightSim();
    }

    private void loadDungeonAdventure() {
        DungeonAdventure da = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("output.dat"))) {
            da = (DungeonAdventure) ois.readObject();
            System.out.println("Object has been deserialized");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(da.getMyDungeon().getMyCurrentRoom());
        System.out.println(da.getMyHero().toString());
        Scanner scanner = new Scanner(System.in);
        TuiControl control = new TuiControl(da.getMyDungeon());
        while (scanner.hasNext()) {
            System.out.println(da.getMyDungeon().getMyCurrentRoom());
            String option = scanner.next();
            boolean isValidMove = switch (option) {
                case "l" -> control.left();
                case "r" -> control.right();
                case "u" -> control.up();
                case "d" -> control.down();
                default -> false;
            };

            if (isValidMove) {
                System.out.println("Moved to + "+ da.getMyDungeon().getMyCurrentCoordinates().row()
                        + ", " + da.getMyDungeon().getMyCurrentCoordinates().column() + "\n"
                        + da.getMyDungeon().getMyCurrentRoom());
            } else {
                System.out.println("invalid move");
                break;
            }

        }
    }

    private void generateDungeon() {
        DungeonAdventure da = new DungeonAdventure(new Thief("Test Thief"), 10, 10);
        Scanner scanner = new Scanner(System.in);
        TuiControl control = new TuiControl(da.getMyDungeon());
        while (scanner.hasNext()) {
            System.out.println(da.getMyDungeon().getMyCurrentRoom());
            String option = scanner.next();
            boolean isValidMove = switch (option) {
                case "l" -> control.left();
                case "r" -> control.right();
                case "u" -> control.up();
                case "d" -> control.down();
                default -> false;
            };

            if (isValidMove) {
                System.out.println("Moved to + "+ da.getMyDungeon().getMyCurrentCoordinates().row()
                        + ", " + da.getMyDungeon().getMyCurrentCoordinates().column() + "\n"
                        + da.getMyDungeon().getMyCurrentRoom());
            } else {
                System.out.println("invalid move");
                break;
            }
        }
        // Define the file path where you want to save the object
        String filePath = "output.dat";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Write the object to the file
            oos.writeObject(da);
            System.out.println("Object has been written to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
