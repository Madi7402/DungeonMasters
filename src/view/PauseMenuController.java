package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import model.DungeonAdventure;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PauseMenuController extends AbstractController {
    @FXML
    private ProgressBar myHealthBar;
    @FXML
    private Label myHealth;
    @FXML
    private Label myHeroName;
    @FXML
    private Label myPillars;
    @FXML
    private Label myFloors;

    private static DungeonAdventure myDungeonAdventure;

    public void initialize(){

    }

    public void resumeButton(ActionEvent event) throws IOException {
        switchScene(event, "Overworld.fxml");
    }

}
