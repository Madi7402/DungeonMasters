package view;

import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage theStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
        theStage.setTitle("Dungeon Master's Adventure");
        theStage.setScene(new Scene(root, AbstractController.WINDOW_WIDTH, AbstractController.WINDOW_HEIGHT));
        theStage.setResizable(false);
        theStage.show();
    }

    /*
    TODO:
    -Add art
    -TIE MODEL TO CONTROLLER IN GENERAL
    -Figure out main game menu
    -Remove text area in load game
     */


}