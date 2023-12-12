package view;

import controller.OverworldControls;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Objects;

import static controller.PropertyChangeEnableDungeon.*;

public class OverworldController extends MenuController implements PropertyChangeListener {
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
    private Timeline myDamageAnimation;
    private DungeonAdventure myDungeonAdventure;
    private OverworldControls myOverworldControls;

    public void initialize() {
        setupAnimations();
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

    public void setHeroImage(final Image theImage) {
        myHeroImageView.setImage(Objects.requireNonNull(theImage));
    }

    public void setHeroNameDisplayText(String theText) {
        myHeroNameDisplayText.setText(theText);
    }

    public void setAdventure(DungeonAdventure theDungeonAdventure) throws IOException {
        myDungeonAdventure = theDungeonAdventure;
        myOverworldControls = new OverworldControls(myDungeonAdventure.getMyDungeon());
        myDungeonAdventure.getMyDungeon().addPropertyChangeListener(NAVIGATED, this);
        myDungeonAdventure.getMyDungeon().addPropertyChangeListener(NAV_FAIL, this);
        myDungeonAdventure.getMyDungeon().addPropertyChangeListener(HIT_PIT, this);
        updateRoomGrid();
    }

    public void updateRoomGrid() throws IOException {
        if (myDungeonAdventure != null) {
            loadRoom(myDungeonAdventure.getMyDungeon().getMyCurrentRoom(), myCurrentRoom);
            getAdjacentRooms();
        }
    }

    public void getAdjacentRooms() throws IOException { // I don't want to talk about this
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

    public void loadRoom(Room theRoom, SubScene theSubScene) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Room.fxml"));
        Parent fxmlRoot = loader.load();
        theSubScene.setRoot(fxmlRoot);
        RoomController rc = loader.getController();
        rc.setRoom(theRoom);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case NAVIGATED -> {
                try {
                    updateRoomGrid();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case  NAV_FAIL -> {
                System.err.println("Failed to navigate, no doors?");
                // TODO -JA: Play failure sound or animate direction that failed
            }
            case HIT_PIT -> myDamageAnimation.play();
            default -> System.err.println("Received unknown event " + evt.getPropertyName());
        }
    }
}
