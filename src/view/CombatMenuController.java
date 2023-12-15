package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import static controller.PropertyChangeEnableFight.*;
import static controller.PropertyChangeEnableFight.ATTACK_MISS;
import static controller.PropertyChangeEnableHero.INVENTORY_ACTION;

/**
 * Controller for the "CombatMenu.fxml" screen.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson.
 */
public class CombatMenuController extends AbstractController implements PropertyChangeListener {
    @FXML
    private Label myEnemyName;
    @FXML
    private Label myEnemyHealth;
    @FXML
    private Label myHeroName;
    @FXML
    private Label myHeroHealth;
    @FXML
    private ProgressBar myEnemyHealthBar;
    @FXML
    private ProgressBar myHeroHealthBar;
    @FXML
    private Button myReturnButton;
    @FXML
    private ImageView myHeroImageView;
    @FXML
    private ImageView myMonsterImageView;
    @FXML
    private Button myAttackButton;
    @FXML
    private Button mySpecialAttackButton;
    @FXML
    private TextArea myLogTextArea;
    @FXML
    private Button myDieButton;

    @FXML
    private ListView<Item> myInventoryListView;

    @FXML
    private Button myInventoryUseButton;
    private DungeonAdventure myDungeonAdventure;
    private Monster myMonster;
    private Hero myHero;

    /**
     * Initializes all buttons on the screen.
     */
    public void initialize(){
        myReturnButton.setOnAction(event -> {
            try {
                victory();
            } catch (IOException e) {
                // Handle the IOException, e.g., log it or show an error message
                e.printStackTrace();
            }
        });

        myAttackButton.setOnAction(actionEvent -> {
            if (myMonster.getMyHealthPoints() > 0 && myHero.getMyMaxHealthPoints() > 0) {
                myHero.attack(myMonster);
                myMonster.attack(myHero);
            }
        });

        mySpecialAttackButton.setOnAction(actionEvent -> {
            if (myMonster.getMyHealthPoints() > 0 && myHero.getMyMaxHealthPoints() > 0) {
                myHero.specialSkill(myMonster);
                myMonster.attack(myHero);
            }
        });

        myDieButton.setOnAction(actionEvent -> {
            myDungeonAdventure.getMyHero().removePropertyChangeListener(this);
            myDungeonAdventure.getMyDungeon().removePropertyChangeListener(this);
            try {
                gameOver();
            } catch (IOException e) {
                throw new RuntimeException("Could not reach GameOver from CombatMenuController");
            }
        });

        myInventoryListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                System.out.println("Selected Index: " + newValue.intValue());
            } else {
                System.out.println("No item selected");
            }
        });

        myInventoryUseButton.setOnAction(actionEvent -> {
            System.out.println(myInventoryListView.getSelectionModel().getSelectedItem());
            myDungeonAdventure.getMyHero().useItem(myInventoryListView.getSelectionModel().getSelectedItem());
        });

    }

    /**
     * Method that takes the player back to the over world screen ("Overworld.fxml") after combat.
     * @throws IOException Input/Output Exception.
     */
    public void victory() throws IOException {
        myDungeonAdventure.getMyHero().removePropertyChangeListener(this);
        myDungeonAdventure.getMyDungeon().removePropertyChangeListener(this);
        FXMLLoader loader = switchScene("Overworld.fxml");
        OverworldController controller = loader.getController();
        controller.setAdventure(myDungeonAdventure);
    }

    /**
     * Property Change method that handles PropertyChangeEvents.
     * @param evt A PropertyChangeEvent object describing the event source and the property that has changed.
     */
    public void propertyChange(PropertyChangeEvent evt) {   //TODO Make these situations update everything needed
        DungeonCharacter source = (DungeonCharacter) evt.getSource();
        String name = source.getMyName();
        switch (evt.getPropertyName()) {
            case DEATH -> {
                myLogTextArea.appendText("\n" + name + " DIED!");
                if (source.equals(myHero)) {
                    myDieButton.fire(); // HACK
                } else {
                    myAttackButton.setDisable(true);
                    mySpecialAttackButton.setDisable(true);
                    myDungeonAdventure.getMyDungeon().getMyCurrentRoom().setMonsterType(MonsterType.NONE);
                }
            }
            case ATTACK -> myLogTextArea.appendText("\n" + name + " Attacked!");
            case TAKE_DAMAGE -> myLogTextArea.appendText("\n" + name + " took damage!");
            case HEALTH_CHANGED, HEALTH_UPDATE -> {
                if (evt.getOldValue() != null) {
                    myLogTextArea.appendText("\n" + name + "'s health changed from "
                            + evt.getOldValue() + " to " + evt.getNewValue());

                    if (source.equals(myMonster)) {
                        myEnemyHealthBar.setProgress((int) evt.getNewValue() / (double) myMonster.getMyMaxHealthPoints());
                        myEnemyHealth.setText(evt.getNewValue() + "/" + myMonster.getMyMaxHealthPoints());
                    } else {
                        int healthPoints = (int) evt.getNewValue();
                        int maxHealthPoint = myHero.getMyMaxHealthPoints();
                        myHeroHealthBar.setProgress(healthPoints / (double) maxHealthPoint);
                        myHeroHealth.setText(evt.getNewValue() + "/" + myHero.getMyMaxHealthPoints());
                    }
                } else {
                    if (source.equals(myMonster)) {
                        myEnemyHealthBar.setProgress(myMonster.getMyHealthPoints() / (double) myMonster.getMyMaxHealthPoints());
                        myEnemyHealth.setText(myMonster.getMyHealthPoints()  + "/" + myMonster.getMyMaxHealthPoints());
                    } else {
                        int healthPoints = myHero.getMyHealthPoints();
                        int maxHealthPoint = myHero.getMyMaxHealthPoints();
                        myHeroHealthBar.setProgress(healthPoints / (double) maxHealthPoint);
                        myHeroHealth.setText(myHero.getMyHealthPoints() + "/" + myHero.getMyMaxHealthPoints());
                    }
                }
            }
            case ATTACK_BLOCK -> myLogTextArea.appendText("\n" + name + " blocked the attack.");
            case ATTACK_MISS -> myLogTextArea.appendText("\n" + name + " missed!");
            case INVENTORY_ACTION -> {
                updateInventoryList();
                myLogTextArea.appendText("\n" + "Hero Inventory Changed");
            }
            default -> System.err.println("Unhandled Event: " + evt.getPropertyName());
        }
    }

    /**
     * A setter method that lets you set the DungeonAdventure.
     * @param theDungeon A DungeonAdventure.
     */
    public void setAdventure(final DungeonAdventure theDungeon) {
        myDungeonAdventure = theDungeon;
        if (myDungeonAdventure != null) {
            myDungeonAdventure.getMyHero().addPropertyChangeListener(this);
            // Configure hero
            myHero = myDungeonAdventure.getMyHero();
            myHeroName.setText(myHero.getMyName());
            myHeroHealth.setText(myHero.getMyHealthPoints() + "/" + myHero.getMyMaxHealthPoints());
            myHeroImageView.setImage(myHero.getMyImage());


            // TODO -JA: get monster from room instead of this test monster
            MonsterFactory mf = new MonsterFactory();
            myMonster = mf.createMonster(myDungeonAdventure.getMyDungeon().getMyCurrentRoom().getMyMonsterType());
            if (myMonster != null) {
                myMonster.addPropertyChangeListener(this);
                myEnemyName.setText(myMonster.getMyName());
                myEnemyHealth.setText(myMonster.getMyHealthPoints() + "/" + myMonster.getMyMaxHealthPoints());
                myMonsterImageView.setImage(myMonster.getMyImage());
            } else {
                myLogTextArea.appendText("There was no monster in this room.");
                myAttackButton.setDisable(true);
                mySpecialAttackButton.setDisable(true);
            }
            updateInventoryList();
        }
    }

    private void updateInventoryList() {
        ObservableList<Item> items = FXCollections.observableArrayList(myDungeonAdventure.getMyHero().getInventory());
        myInventoryListView.setItems(items);
    }

}
