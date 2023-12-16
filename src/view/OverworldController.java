package view;

import controller.OverworldControls;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static controller.PropertyChangeEnableDungeon.*;
import static controller.PropertyChangeEnableFight.*;
import static controller.PropertyChangeEnableHero.INVENTORY_ACTION;
import static controller.PropertyChangeEnableHero.VISION_POTION_USED;

public class OverworldController extends AbstractController implements PropertyChangeListener {
    @FXML
    private ImageView myHeroImageView;
    @FXML
    private Text myHeroNameDisplayText;
    @FXML
    private SubScene myCurrentRoom;
    @FXML
    private SubScene myNorthWestRoom;
    @FXML
    private SubScene myNorthRoom;
    @FXML
    private SubScene myWestRoom;
    @FXML
    private SubScene myNorthEastRoom;
    @FXML
    private SubScene myEastRoom;
    @FXML
    private SubScene mySouthEastRoom;
    @FXML
    private SubScene mySouthRoom;
    @FXML
    private SubScene mySouthWestRoom;
    @FXML
    private Button myUpButton;
    @FXML
    private Button myDownButton;
    @FXML
    private Button myLeftButton;
    @FXML
    private Button myRightButton;
    @FXML
    private FlowPane myFlowPane;
    @FXML
    private Circle myCircle;
    @FXML
    private Text myHeroHealthText;
    @FXML
    private Text myHeroCharStatsText;
    @FXML
    private ListView<Item> myInventoryListView;
    @FXML
    private Button myInventoryUseButton;
    @FXML
    private Button myInventoryInfoButton;
    @FXML
    private Button myFightButton;
    @FXML
    private Accordion myAccordion;
    @FXML
    private Button myPitButton;
    @FXML
    private Button myHpButton;
    private Timeline myDamageAnimation;
    private DungeonAdventure myDungeonAdventure;
    private OverworldControls myOverworldControls;

    public void initialize() {
        setupAnimations();
        myAccordion.setExpandedPane(myAccordion.getPanes().get(0));
        myUpButton.setOnAction(actionEvent -> {
            if (myOverworldControls != null) {
                myOverworldControls.up();
            }
        });

        myDownButton.setOnAction(actionEvent -> {
            if (myOverworldControls != null) {
                myOverworldControls.down();
            }
        });

        myLeftButton.setOnAction(actionEvent -> {
            if (myOverworldControls != null) {
                myOverworldControls.left();
            }
        });

        myRightButton.setOnAction(actionEvent -> {
            if (myOverworldControls != null) {
                myOverworldControls.right();
            }
        });

        // Cheats/Dev Tools
        myPitButton.setOnAction(actionEvent -> myDungeonAdventure.getMyHero().hitPit());
        myFightButton.setOnAction(actionEvent -> switchToFightScene());
        myHpButton.setOnAction(actionEvent -> giveHeroHpPotion());
    }

    private void giveHeroHpPotion() {
        if (myDungeonAdventure != null) {
            myDungeonAdventure.getMyHero().giveItem(ItemType.HEALING_POTION);
        }
    }

    private void switchToFightScene() {
        FXMLLoader loader = switchScene("CombatMenu.fxml");
        CombatMenuController controller = loader.getController();
        myDungeonAdventure.getMyHero().removePropertyChangeListener(this);
        myDungeonAdventure.getMyDungeon().removePropertyChangeListener(this);
        controller.setAdventure(myDungeonAdventure);
    }

    private void setupAnimations() {
        // Start the animation with timeline.play();
        myDamageAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(myCurrentRoom.translateXProperty(), 0.0)),
                new KeyFrame(Duration.millis(50), new KeyValue(myCurrentRoom.translateXProperty(), 10.0)),
                new KeyFrame(Duration.millis(100), new KeyValue(myCurrentRoom.translateXProperty(), -10.0)),
                new KeyFrame(Duration.millis(120), new KeyValue(myCurrentRoom.translateXProperty(), 0.0))
        );
        myDamageAnimation.setCycleCount(5);
    }

    public void setAdventure(DungeonAdventure theDungeonAdventure) {
        myDungeonAdventure = theDungeonAdventure;
        Dungeon dungeon = theDungeonAdventure.getMyDungeon();
        Hero hero = theDungeonAdventure.getMyHero();

        myOverworldControls = new OverworldControls(myDungeonAdventure.getMyDungeon());

        // Add event listeners
        dungeon.addPropertyChangeListener(this);
        hero.addPropertyChangeListener(this);

        myHeroNameDisplayText.setText(hero.getMyName());
        myHeroHealthText.setText("Health: " + myDungeonAdventure.getMyHero().getMyHealthPoints());
        myHeroCharStatsText.setText(myDungeonAdventure.getMyHero().getStatsString());
        myHeroImageView.setImage(hero.getMyImage());

        updateInventoryList();


        myInventoryUseButton.setOnAction(actionEvent ->
                myDungeonAdventure.getMyHero().useItem(myInventoryListView.getSelectionModel().getSelectedItem()));

        myInventoryInfoButton.setOnAction(actionEvent -> { // TODO: Don't run if no item is selected
            Alert alert = new Alert(Alert.AlertType.INFORMATION, myInventoryListView.getSelectionModel().getSelectedItem().getDescription());
            alert.setHeaderText(myInventoryListView.getSelectionModel().getSelectedItem().getName());
            alert.setTitle("Item Information");
            alert.showAndWait();
        });

        // Draw initial room grid
        updateRoomGrid();
        myDungeonAdventure.getMyDungeon().checkIfExit(); // check if exit after returning from Fight scene
    }

    private void updateInventoryList() {
        ObservableList<Item> items = FXCollections.observableArrayList(myDungeonAdventure.getMyHero().getInventory());
        myInventoryListView.setItems(items);
    }

    public void updateRoomGrid() {
        if (myDungeonAdventure != null) {
            loadRoom(myDungeonAdventure.getMyDungeon().getMyCurrentRoom(), myCurrentRoom);
            getAdjacentRooms();
        }
    }

    public void getAdjacentRooms() { // I don't want to talk about this
        Coordinates myCurrentCoordinates = myDungeonAdventure.getMyDungeon().getMyCurrentRoom().getCoordinate();
        Maze maze = myDungeonAdventure.getMyDungeon().getMyMaze();
        Room north = maze.getRoom(myCurrentCoordinates, Direction.NORTH);
        Room south = maze.getRoom(myCurrentCoordinates, Direction.SOUTH);
        Room west = maze.getRoom(myCurrentCoordinates, Direction.WEST);
        Room east = maze.getRoom(myCurrentCoordinates, Direction.EAST);

        if (north != null) {
            loadRoom(north, myNorthRoom);
            myNorthRoom.setVisible(true);
            Room northWest = maze.getRoom(north.getCoordinate(), Direction.WEST);
            if (northWest != null) {
                loadRoom(northWest, myNorthWestRoom);
                myNorthWestRoom.setVisible(true);
            } else {
                myNorthWestRoom.setVisible(false);
            }
            Room northEast = maze.getRoom(north.getCoordinate(), Direction.EAST);
            if (northEast != null) {
                loadRoom(northEast, myNorthEastRoom);
                myNorthEastRoom.setVisible(true);
            } else {
                myNorthEastRoom.setVisible(false);
            }
        } else {
            myNorthRoom.setVisible(false);
            myNorthEastRoom.setVisible(false);
            myNorthWestRoom.setVisible(false);
        }
        if (south != null) {
            loadRoom(south, mySouthRoom);
            mySouthRoom.setVisible(true);
            Room southWest = maze.getRoom(south.getCoordinate(), Direction.WEST);
            if (southWest != null) {
                loadRoom(southWest, mySouthWestRoom);
                mySouthWestRoom.setVisible(true);
            } else {
                mySouthWestRoom.setVisible(false);
            }
            Room southEast = maze.getRoom(south.getCoordinate(), Direction.EAST);
            if (southEast != null) {
                loadRoom(southEast, mySouthEastRoom);
                mySouthEastRoom.setVisible(true);
            } else {
                mySouthEastRoom.setVisible(false);
            }

        } else {
            mySouthRoom.setVisible(false);
            mySouthEastRoom.setVisible(false);
            mySouthWestRoom.setVisible(false);
        }
        if (west != null) {
            loadRoom(west, myWestRoom);
            myWestRoom.setVisible(true);
        } else {
            myWestRoom.setVisible(false);
        }
        if (east != null) {
            loadRoom(east, myEastRoom);
            myEastRoom.setVisible(true);
        } else {
            myEastRoom.setVisible(false);
        }
    }

    public void loadRoom(Room theRoom, SubScene theSubScene) {
        FXMLLoader loader = null;
        Parent fxmlRoot = null;
        try {
            loader = new FXMLLoader(getClass().getResource("Room.fxml"));
            fxmlRoot = loader.load();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
        theSubScene.setRoot(fxmlRoot);
        RoomController rc = loader.getController();
        rc.setRoom(theRoom);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case NAVIGATED -> updateRoomGrid();
            case NAV_FAIL -> {
                // TODO -JA: Play failure sound or animate direction that failed
            }
            case HIT_PIT -> myDamageAnimation.play();
            case HEALTH_CHANGED, HEALTH_UPDATE -> myHeroHealthText.setText("Health: " + myDungeonAdventure.getMyHero().getMyHealthPoints());
            case VISION_POTION_USED -> {
                myDungeonAdventure.getMyDungeon().getMyMaze().setSurroundingRoomsVisible(myDungeonAdventure.getMyDungeon().getMyCurrentCoordinates(), 1);
                updateRoomGrid();
            }
            case DEATH -> switchScene("GameOverScreen.fxml");
            case INVENTORY_ACTION -> updateInventoryList();
            case FIGHT_BEGIN -> switchToFightScene();
            case GAME_WIN -> switchScene("VictoryScreen.fxml");
            default -> System.err.println("Received unknown event " + evt.getPropertyName());
        }
    }

    public DungeonAdventure getMyDungeonAdventure(){
        return myDungeonAdventure;
    }

    public void saveButton(){
        String filePath = "Save Files/" + getMyDungeonAdventure().getMyHero().getMyName() + ".dat";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Write the object to the file
            oos.writeObject(myDungeonAdventure);
            System.out.println("Object has been written to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
