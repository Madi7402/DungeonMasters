package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.IOException;

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
    public void initialize(){
        //Figure out how to determine monster


    }

    public void gameOver(ActionEvent event) throws IOException {
        switchScene(event, "GameOver.FXML");
    }
    public void victory(ActionEvent event) throws IOException {
        switchScene(event, "VictoryScreen");
    }

    //myEnemyHealthBar.setProgress((current/total)F);

    //Load in text fields
    //Art
    //Health bars
    //Hook up buttons
}
