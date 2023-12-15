package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import model.DungeonAdventure;

import java.io.IOException;

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

    public void resumeButton() throws IOException {
        switchScene("Overworld.fxml");
    }

}
