package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class AbstractController {
    private Stage myStage;
    private Scene myScene;
    private Parent myRoot;

    private static int WINDOW_WIDTH = 1280;
    private static int WINDOW_HEIGHT = 720;

    public FXMLLoader switchScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(sceneName)));
        myRoot = fxmlLoader.load();
        myScene = new Scene(myRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
        AbstractController ac = fxmlLoader.getController();
        ac.setMyStage(myStage);
        myStage.setScene(myScene);
        myStage.show();
        return fxmlLoader;
    }

    public void backToMenuButton() throws IOException {
        switchScene("StartMenu.fxml");
    }

    public void gameOver() throws IOException {
        switchScene("GameOverScreen.fxml");
    }

    protected void setMyStage(final Stage theStage) {
        myStage = Objects.requireNonNull(theStage);
    }
}
