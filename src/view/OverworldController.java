package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.*;

import java.io.IOException;
import java.util.Objects;

public class OverworldController extends MenuController {
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


    private DungeonAdventure myDungeonAdventure;

    public void initialize() {
        myUpButton.setOnAction(actionEvent -> {
            if (myDungeonAdventure != null) {
                myDungeonAdventure.getMyDungeon().goDirection(Direction.NORTH);
                try {
                    loadRoom(myDungeonAdventure.getMyDungeon().getMyCurrentRoom(), myCurrentRoom);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    getAdjacentRooms();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        myDownButton.setOnAction(actionEvent -> {
            if (myDungeonAdventure != null) {
                myDungeonAdventure.getMyDungeon().goDirection(Direction.SOUTH);
                try {
                    loadRoom(myDungeonAdventure.getMyDungeon().getMyCurrentRoom(), myCurrentRoom);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    getAdjacentRooms();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        myLeftButton.setOnAction(actionEvent -> {
            if (myDungeonAdventure != null) {
                myDungeonAdventure.getMyDungeon().goDirection(Direction.WEST);
                try {
                    loadRoom(myDungeonAdventure.getMyDungeon().getMyCurrentRoom(), myCurrentRoom);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    getAdjacentRooms();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        myRightButton.setOnAction(actionEvent -> {
            if (myDungeonAdventure != null) {
                myDungeonAdventure.getMyDungeon().goDirection(Direction.EAST);
                try {
                    loadRoom(myDungeonAdventure.getMyDungeon().getMyCurrentRoom(), myCurrentRoom);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    getAdjacentRooms();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void setHeroImage(final Image theImage) {
        myHeroImageView.setImage(Objects.requireNonNull(theImage));
    }

    public void setHeroNameDisplayText(String theText) {
        myHeroNameDisplayText.setText(theText);
    }

    public void setAdventure(DungeonAdventure theDungeonAdventure) throws IOException {
        myDungeonAdventure = theDungeonAdventure;

        loadRoom(myDungeonAdventure.getMyDungeon().getMyCurrentRoom(), myCurrentRoom);
        getAdjacentRooms();
    }

    public void getAdjacentRooms() throws IOException {
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
}
