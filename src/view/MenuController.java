package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the main start menu ("StartMenu.fxml").
 * @author Jonathan Abrams, Madison Pope, Martha Emerson
 */
public class MenuController extends AbstractController {

    /**
     * Button that switches to the new game screen when pressed ("NewGame.fxml").
     * @throws IOException Input/Output exception.
     */
    public void newGameButton() throws IOException {
        switchScene("NewGame.fxml");
    }

    /**
     * Button that switches to the load game screen when pressed ("LoadGameMenu.fxml").
     * @throws IOException Input/Output exception.
     */
     public void loadGameButton() throws IOException {
        switchScene("LoadGameMenu.fxml");
    }

    /**
     * Button that initializes and loads a credits pop up when pressed.
     */
    public void creditsButton(){
        Stage creditStage = new Stage();
        creditStage.initModality(Modality.APPLICATION_MODAL);
        creditStage.setTitle("Credits");
        creditStage.setMinWidth(200);
        creditStage.setMinHeight(200);
        creditStage.setResizable(false);

        Label creditLabel = new Label("Project by: " +
                        "\n Jonathan Abrams " +
                        "\n Madison Pope " +
                        "\n Martha Emerson \n" +
                        "\n Course: TCSS 360" +
                        "\n Professor: Tom Capaul");

        VBox vbox = new VBox(10);
        vbox.getChildren().add(creditLabel);
        vbox.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(vbox);
        creditStage.setScene(scene);
        creditStage.showAndWait();
    }
    
}
