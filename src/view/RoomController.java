package view;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Direction;
import model.Room;

import java.util.Objects;

/**
 * Controller class for "Room.fxml."
 * @author Jonathan Abrams, Madison Pope, Martha Emerson.
 */
public class RoomController {
    @FXML
    private Rectangle myNorthDoor;
    @FXML
    private Rectangle myWestDoor;
    @FXML
    private Rectangle myEastDoor;
    @FXML
    private Rectangle mySouthDoor;

    @FXML
    private AnchorPane myAnchorPane;
    @FXML
    private Text myRoomText;
    private Room myRoom;

    /**
     * Sets the adjacent rooms and makes them visible.
     * @param theRoom A Room you want to get the adjacent rooms for.
     */
    public void setRoom(final Room theRoom) {
        myRoom = Objects.requireNonNull(theRoom);
        for (Direction direction : theRoom.getDoors()) {
            switch (direction) {
                case NORTH -> myNorthDoor.setVisible(true);
                case WEST -> myWestDoor.setVisible(true);
                case EAST -> myEastDoor.setVisible(true);
                case SOUTH -> mySouthDoor.setVisible(true);
                default -> System.err.println("unknown direction in setRoom (Room Controller)");
            }
        }

        myRoomText.setText(myRoom.toString() + myRoom.getCoordinate());
        if (theRoom.isVisited()) {
            myAnchorPane.setVisible(true);
        }
    }

}
