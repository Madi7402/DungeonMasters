package view;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import model.Direction;
import model.Room;

public class RoomController {
    @FXML
    private Rectangle myNorthDoor;
    @FXML
    private Rectangle myWestDoor;
    @FXML
    private Rectangle myEastDoor;
    @FXML
    private Rectangle mySouthDoor;

    private Room myRoom;

    public void initialize() {

    }

    public void setRoom(final Room theRoom) {
        for (Direction direction : theRoom.getDoors()) {
            System.out.println(direction);
            switch (direction) {
                case NORTH -> myNorthDoor.setVisible(true);
                case WEST -> myWestDoor.setVisible(true);
                case EAST -> myEastDoor.setVisible(true);
                case SOUTH -> mySouthDoor.setVisible(true);
                default -> System.err.println("unknown direction in setRoom (Room Controller)");
            }
        }
    }
}
