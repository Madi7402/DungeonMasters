package view;

import javafx.event.ActionEvent;
import java.io.IOException;

public class MenuController extends AbstractController {

    public void newGameButton(ActionEvent event) throws IOException {
        switchScene(event, "NewGame.fxml");
    }
     public void loadGameButton(ActionEvent event) throws IOException {
        switchScene(event, "LoadGameMenu.fxml");
    }

    public void creditsButton(){
        System.out.println("Credits button pressed");
    }
    
}
