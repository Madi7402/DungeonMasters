package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage theStage) throws Exception {
        theStage.setTitle("DM's Adventure");

        MenuItem save = new MenuItem("Save Game");
        MenuItem settings = new MenuItem("Settings");
        //Radio menu item: Controls, Window, Sound?
        MenuItem tutorial = new MenuItem("Tutorial");
        //Game explanation, pop up screen
        MenuItem credits = new MenuItem("Credits");
        //Pop up screen

        Menu menu = new Menu("Options");    //TODO Submenus

        menu.getItems().add(save);
        menu.getItems().add(settings);
        menu.getItems().add(tutorial);
        menu.getItems().add(credits);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        VBox vbox = new VBox(menuBar);

        Scene scene = new Scene(vbox, 960, 600);
        theStage.setScene(scene);

        theStage.show();

    }

}