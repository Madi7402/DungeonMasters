package view;

import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Main class that launches the program GUI
 * @author Joanthan Abrams, Madison Pope, Martha Emerson
 */
public class Main extends Application {
    private final static int WINDOW_WIDTH = 1280;
    private final static int WINDOW_HEIGHT = 720;

    /**
     * Main method that launches the program.
     * @param theArgs String[] of program arguments.
     */
    public static void main(String[] theArgs){
        launch(theArgs);
    }

    /**
     * Overriden start method for starting the program.
     * @param theStage a FXML Stage object.
     * @throws Exception
     */
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