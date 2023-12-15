package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class AbstractController {
    private Stage myStage;
    private Scene myScene;
    private Parent myRoot;

    public static int WINDOW_WIDTH = 1280;
    public static int WINDOW_HEIGHT = 720;

    public FXMLLoader switchScene(ActionEvent event, String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(sceneName)));
        myRoot = fxmlLoader.load();
        myStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        myScene = new Scene(myRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
        myStage.setScene(myScene);  //Bug here, however it works if not sent here by menu
        myStage.show();
        return fxmlLoader;
    }

    public void backToMenuButton(ActionEvent event) throws IOException {
        switchScene(event, "StartMenu.fxml");
    }

    public void settingsButton(ActionEvent event) throws IOException {
        switchScene(event, "Settings.fxml");
    }

    public void gameOver(ActionEvent theEvent) throws IOException {
        switchScene(theEvent, "GameOverScreen.fxml");

    }
}
