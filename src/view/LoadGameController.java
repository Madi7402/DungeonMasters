package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.DungeonAdventure;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the Load game FXML file ("LoadGameMenu.fxml")
 * @author Jonathan Abrams, Madison Pope, Martha Emerson.
 */
public class LoadGameController extends AbstractController implements Initializable{

    @FXML
    private ListView<String> mySaveList;

    /**
     * Overrien initialize method from FXML. Loads data into save file table.
     * @param url A URL.
     * @param resourceBundle A ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> options = new ArrayList();
        File[] files = new File("Save Files").listFiles();
        for (File file : files) {
            options.add(file.getAbsolutePath());
        }
        ObservableList<String> list = FXCollections.observableArrayList(options);
        mySaveList.setItems(list);
    }

    /**
     * Button method for loading in a selected save file when player picks one from the ListView.
     * Reads from "Save Game" folder.
     */
    public void loadButton(){
        try{
            FileInputStream fileInputStream = new FileInputStream(mySaveList.getSelectionModel().getSelectedItem());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
            DungeonAdventure da = (DungeonAdventure) objectInputStream.readObject();

            FXMLLoader loader = switchScene("Overworld.fxml");
            OverworldController controller = loader.getController();
            controller.setAdventure(da);
            //controller.setHeroImage(myHeroImageView.getImage()); // TODO - JA: Get this in Overworld Controller Instead from DA

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Switches to "NewGame.fxml" on button press.
     * @throws IOException Input/Output Exception.
     */
    public void newGameButton() throws IOException {
        switchScene( "NewGame.fxml");
    }

}
