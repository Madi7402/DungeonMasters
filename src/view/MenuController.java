package view;

import javafx.event.ActionEvent;
import java.io.IOException;

public class MenuController extends AbstractController{

    public void newGameButton(ActionEvent event) throws IOException {
        switchScene(event, "NewGame.fxml");
    }
     public void loadGameButton(ActionEvent event) throws IOException {
        switchScene(event, "LoadGameMenu.fxml");
    }
    public void settingsButton(ActionEvent event) throws IOException {
        switchScene(event, "Settings.fxml");
    }
    public void creditsButton(){    //TODO Textarea
        System.out.println("Credits button pressed");
    }
    
}
