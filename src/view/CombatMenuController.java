package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import model.DungeonAdventure;
import model.DungeonCharacter;

import java.beans.PropertyChangeEvent;
import java.io.IOException;

import static controller.PropertyChangeEnableFight.*;
import static controller.PropertyChangeEnableFight.ATTACK_MISS;
import static controller.PropertyChangeEnableHero.INVENTORY_ACTION;

public class CombatMenuController extends AbstractController {
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
    private TextArea myTextLogs;
    private DungeonAdventure myDungeonAdventure;
    public void initialize(){
        initNames(myHeroName);
        initNames(myEnemyName);
        initHealth(myHeroHealth);
        initHealth(myEnemyHealth);
        initHealthBar(myHeroHealthBar);
        initHealthBar(myEnemyHealthBar);
        //initArt();
        //initArt();
    }

    public void gameOver(ActionEvent event) throws IOException {
        switchScene(event, "GameOver.FXML");
    }
    public void victory(ActionEvent event) throws IOException {
        switchScene(event, "VictoryScreen");
    }

    public void initNames(Label theNameLabel){

    }

    public void initHealth(Label theHealthLabel){

    }

    public void initHealthBar(ProgressBar theHealthBar){

    }

    public void initArt(ImageView theImageView){

    }

    public void setDungeon(DungeonAdventure theDungeon){
        myDungeonAdventure = theDungeon;
    }

    public void propertyChange(PropertyChangeEvent evt) {   //TODO Make these situations update everything needed
        DungeonCharacter source = (DungeonCharacter) evt.getSource();
        String name = source.getMyName();
        switch (evt.getPropertyName()) {
            case DEATH -> myTextLogs.appendText("\n" + name + " DIED!");
            case ATTACK -> myTextLogs.appendText("\n" + name + " Attacked!");
            case TAKE_DAMAGE -> myTextLogs.appendText("\n" + name + " took damage!");
            case HEALTH_CHANGED
                    -> myTextLogs.appendText("\n" + name + "'s health changed from "
                    + evt.getOldValue() + " to " + evt.getNewValue());
            case ATTACK_BLOCK -> myTextLogs.appendText("\n" + name + " blocked the attack.");
            case ATTACK_MISS -> myTextLogs.appendText("\n" + name + " missed!");
            case INVENTORY_ACTION -> myTextLogs.appendText("\n" + "Hero Inventory Changed");
            default -> System.err.println("Unhandled Event: " + evt.getPropertyName());
        }
    }


    //myEnemyHealthBar.setProgress((current/total)F);

    //Load in text fields
    //Art
    //Health bars
    //Hook up buttons

    //SQlite
    //
}
