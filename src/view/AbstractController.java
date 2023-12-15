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

    private final static int WINDOW_WIDTH = 1280;
    private final static int WINDOW_HEIGHT = 720;

    public FXMLLoader switchScene(String sceneName) {
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(sceneName)));
            myRoot = fxmlLoader.load();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.err.println("[IOException] Could not load scene: " + sceneName);
            System.exit(1);
        }
        myScene = new Scene(myRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
        AbstractController ac = fxmlLoader.getController();
        ac.setMyStage(myStage);
        myStage.setScene(myScene);
        myStage.show();
        return fxmlLoader;
    }

    public void backToMenuButton() {
        switchScene("StartMenu.fxml");
    }

    public void gameOver() {
        switchScene("GameOverScreen.fxml");
    }

    protected void setMyStage(final Stage theStage) {
        myStage = Objects.requireNonNull(theStage);
    }
}
