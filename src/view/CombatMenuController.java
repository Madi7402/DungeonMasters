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
import java.util.List;

import static controller.PropertyChangeEnableDungeon.GAME_WIN;
import static controller.PropertyChangeEnableFight.*;
import static controller.PropertyChangeEnableFight.ATTACK_MISS;
import static controller.PropertyChangeEnableHero.INVENTORY_ACTION;

public class CombatMenuController extends AbstractController implements PropertyChangeListener {
    /* FXML References from CombatMenu.xml */
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
    private ListView<Item> myInventoryListView;
    @FXML
    private Button myInventoryUseButton;
    /* Model References */
    private DungeonAdventure myDungeonAdventure;
    private Monster myMonster;
    private Hero myHero;

    public void initialize(){
        myReturnButton.setOnAction(event -> returnToOverworld());
        myAttackButton.setOnAction(actionEvent -> heroAction(MoveType.ATTACK));
        mySpecialAttackButton.setOnAction(actionEvent -> heroAction(MoveType.SPEC));
        myInventoryUseButton.setOnAction(actionEvent -> heroAction(MoveType.ITEM));
    }


    private enum MoveType {
        ATTACK,
        SPEC,
        ITEM
    }

    /**
     * Have the hero perform an action
     * @param theMoveType the type of action to be performed from the battle screen
     */
    private void heroAction(MoveType theMoveType) {
        if (myMonster == null || !myMonster.isAlive()) {
            if (theMoveType == MoveType.ITEM) // No monster, can still use item
                myHero.useItem(myInventoryListView.getSelectionModel().getSelectedItem());
            return;
        } else if (myMonster == null || myHero == null) {
            return;
        }

        if (myHero.getAttackSpeed() > myMonster.getAttackSpeed()) { // Hero faster, attacks first
            switch (theMoveType) {
                case ATTACK -> myHero.attack(myMonster);
                case SPEC -> myHero.specialSkill(myMonster);
                case ITEM -> myHero.useItem(myInventoryListView.getSelectionModel().getSelectedItem());
            }

            if (myMonster.isAlive()) {
                myMonster.attack(myHero);
            }
        } else { // Monster faster, attacks first
            myMonster.attack(myHero);
            if (myHero.isAlive()) {
                switch (theMoveType) {
                    case ATTACK -> myHero.attack(myMonster);
                    case SPEC -> myHero.specialSkill(myMonster);
                    case ITEM -> myHero.useItem(myInventoryListView.getSelectionModel().getSelectedItem());
                }
            }
        }
    }

    public void returnToOverworld() {
        myDungeonAdventure.getMyHero().removePropertyChangeListener(this);
        myDungeonAdventure.getMyDungeon().removePropertyChangeListener(this);
        FXMLLoader loader = switchScene("Overworld.fxml");
        OverworldController controller = loader.getController();
        controller.setAdventure(myDungeonAdventure);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        DungeonCharacter source = (DungeonCharacter) evt.getSource();
        String name = source.getMyName();
        switch (evt.getPropertyName()) {
            case DEATH -> deathAction(evt, source, name);
            case ATTACK -> myLogTextArea.appendText("\n" + name + " Attacked!");
            case TAKE_DAMAGE -> myLogTextArea.appendText("\n" + name + " took damage!");
            case HEALTH_CHANGED, HEALTH_UPDATE -> updateHealth(evt, source, name);
            case ATTACK_BLOCK -> myLogTextArea.appendText("\n" + name + " blocked the attack.");
            case ATTACK_MISS -> myLogTextArea.appendText("\n" + name + " missed!");
            case GAME_WIN -> switchScene("VictoryScreen.fxml");
            case INVENTORY_ACTION -> updateInventoryList();
            default -> System.err.println("Unhandled Event: " + evt.getPropertyName());
        }
    }

    private void deathAction(PropertyChangeEvent evt, DungeonCharacter source, String name) {
        myLogTextArea.appendText("\n" + name + " DIED!");
        if (source.equals(myHero)) {
            myDungeonAdventure.getMyHero().removePropertyChangeListener(this);
            myDungeonAdventure.getMyDungeon().removePropertyChangeListener(this);
            gameOver();
        } else {
            myAttackButton.setDisable(true);
            mySpecialAttackButton.setDisable(true);
            myDungeonAdventure.getMyDungeon().getMyCurrentRoom().setMonsterType(MonsterType.NONE);
        }
    }

    private void updateHealth(PropertyChangeEvent evt, DungeonCharacter source, String name) {
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

    public void setAdventure(final DungeonAdventure theDungeon) {
        myDungeonAdventure = theDungeon;
        if (myDungeonAdventure != null) {
            myDungeonAdventure.getMyHero().addPropertyChangeListener(this);
            // Configure hero
            myHero = myDungeonAdventure.getMyHero();
            myHeroName.setText(myHero.getMyName());
            myHeroHealth.setText(myHero.getMyHealthPoints() + "/" + myHero.getMyMaxHealthPoints());
            myHeroHealthBar.setProgress(myHero.getMyHealthPoints() / (double) myHero.getMyMaxHealthPoints());
            myHeroImageView.setImage(myHero.getMyImage());

            // Create new monster if there is a monster in the room
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
        // Only get potions for Combat menu
        List<Item> potions = myDungeonAdventure.getMyHero().getInventory().stream()
                .filter(item -> item.getType() == ItemType.HEALING_POTION).toList();
        ObservableList<Item> items = FXCollections.observableArrayList(potions);
        myInventoryListView.setItems(items);
    }

}
