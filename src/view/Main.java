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

    //TODO: Finish controls on the setting menu (SQlite?)
    //TODO: Link View with Model
    //TODO: Controls
    //TODO: Tie text with data
    //TODO: Add images to screens
    //TODO: Hook up controllers

}