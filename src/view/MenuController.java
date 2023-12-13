package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController extends AbstractController {

    public void newGameButton(ActionEvent event) throws IOException {
        switchScene(event, "NewGame.fxml");
    }
     public void loadGameButton(ActionEvent event) throws IOException {
        switchScene(event, "LoadGameMenu.fxml");
    }

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
