package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * An abstract class for all controllers.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public abstract class AbstractController {
    private Stage myStage;
    private Scene myScene;
    private Parent myRoot;

    private static int WINDOW_WIDTH = 1280;
    private static int WINDOW_HEIGHT = 720;

    /**
     * A universal method for switching scenes (fxml files).
     * @param sceneName a String name of the fxml file you want to switch to (Ex: "File.fxml").
     * @return FXMLLoader
     * @throws IOException
     */
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

    /**
     * A method that switches back to the main menu.
     * @throws IOException
     */
    public void backToMenuButton() throws IOException {
        switchScene("StartMenu.fxml");
    }

    /**
     * A method that switches to the game over screen.
     * @throws IOException
     */
    public void gameOver() throws IOException {
        switchScene("GameOverScreen.fxml");
    }

    /**
     * A method that lets you set the stage.
     * @param theStage a final FXML Stage object.
     */
    protected void setMyStage(final Stage theStage) {
        myStage = Objects.requireNonNull(theStage);
    }
}
