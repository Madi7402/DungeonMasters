package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.DungeonAdventure;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoadGameController extends AbstractController implements Initializable{

    @FXML
    private ListView<String> mySaveList;

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

    public void loadButton(){
        try{
            FileInputStream fileInputStream = new FileInputStream(mySaveList.getSelectionModel().getSelectedItem());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
            DungeonAdventure da;
            try {
                da = (DungeonAdventure) objectInputStream.readObject();
            } catch (InvalidClassException ice) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry, Save file is incompatible: Possibly from an old version.");
                alert.showAndWait();
                return;
            }
            FXMLLoader loader = switchScene("Overworld.fxml");
            OverworldController controller = loader.getController();
            controller.setAdventure(da);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void newGameButton() throws IOException {
        switchScene( "NewGame.fxml");
    }



//    public void loadAdventure(ActionEvent event){
//        //When player hits play, load the dungeon based on the file name
//    }
}
