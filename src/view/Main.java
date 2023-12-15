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

    private static int WINDOW_WIDTH = 1280;
    private static int WINDOW_HEIGHT = 720;
    @Override
    public void start(Stage theStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartMenu.fxml"));
        Parent root = loader.load();

        theStage.setTitle("Dungeon Master's Adventure");
        theStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        theStage.setResizable(false);
        theStage.show();
        MenuController mc = loader.getController();
        mc.setMyStage(theStage);
    }

}