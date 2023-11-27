package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private Stage myStage;
    private Scene myScene;
    private Parent myRoot;
    private Canvas myRoomCanvas;

    public static int WINDOW_WIDTH = 1280;
    public static int WINDOW_HEIGHT = 720;

    public void newGameButton(ActionEvent event) throws IOException {
        System.out.println("New game button pressed");
//        switchScene(event, "NewGameMenu.fxml");
//        switchScene(event, "Canvas.fxml");
        switchScene(event, "NewGame.fxml");
//        myRoomCanvas = (Canvas) myStage.getScene().lookup("#canvas-room");
//        initialize(myRoomCanvas);
    }

    public void initialize(Canvas theCanvas) {
        // Initialize the canvas and draw something
        GraphicsContext gc = theCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillRect(50, 50, 100, 100);
    }

    public void loadGameButton(ActionEvent event) throws IOException {
        System.out.println("Load game button pressed");
        switchScene(event, "LoadGameMenu.fxml");
    }
    public void settingsButton(ActionEvent event) throws IOException {
        System.out.println("Settings button pressed");
        switchScene(event, "Settings.fxml");
    }
    public void creditsButton(){    //TODO Textarea
        System.out.println("Credits button pressed");
        //switchScene(event, <Insert FXML here>)
    }

    public void switchScene(ActionEvent event, String sceneName) throws IOException {
        myRoot = FXMLLoader.load(getClass().getResource(sceneName));
        myStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        myScene = new Scene(myRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
        myStage.setScene(myScene);
        myStage.show();
    }

}
