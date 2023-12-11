package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.*;
import res.SQLite;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewGameController extends MenuController {

    @FXML
    private ChoiceBox<String> myHeroTypeCB;

    @FXML
    private Slider myMapWidthSlider;

    @FXML
    private Slider myMapHeightSlider;

    @FXML
    private Text myWidthSliderValue;

    @FXML
    private Text myHeightSliderValue;

    @FXML
    private TextField myHeroNameTextField;

    @FXML
    private Text myHeroNameDisplayText;

    @FXML
    private Button myPlayButton;

    @FXML
    private ImageView myHeroImageView;

    public void initialize() {
        initHeroSelect();
        initHeroTextBox();
        initSliders();
        initPlayButton();
    }

    private void initPlayButton() {
        myPlayButton.setOnAction(actionEvent -> {
            DungeonAdventure da = createAdventure();
            if (da == null) {
                // TODO -JA: Indicate that DungeonAdventure creation failed.
                return;
            }
            try {
                FXMLLoader loader = switchScene(actionEvent, "Overworld.fxml");
                OverworldController controller = loader.getController();
                controller.setAdventure(da);
                controller.setHeroImage(myHeroImageView.getImage());
                controller.setHeroNameDisplayText(myHeroNameDisplayText.getText());
                if (myHeroNameDisplayText.getText().isEmpty()) {
                    controller.setHeroNameDisplayText("Unnamed Hero");
                }
                createAdventure();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private DungeonAdventure createAdventure() {
        // TODO -JA: get map size
        String name = myHeroNameDisplayText.getText();
        return switch(myHeroTypeCB.getValue()) {
            case "Thief" -> new DungeonAdventure(new Thief(name));
            case "Priestess" -> new DungeonAdventure(new Priestess(name));
            case "Warrior" -> new DungeonAdventure(new Warrior(name));
            default -> null;
        };
    }

    private void initHeroSelect() {
        // Select Hero Type
        myHeroTypeCB.setOnAction(actionEvent -> {
            String query = "SELECT img FROM character where friendly_name = '" + myHeroTypeCB.getValue() + "'";
            try (SQLite db = new SQLite(query)) {
                ResultSet rs = db.getMyResults();
                if (rs.next()) {
                    String imageName = rs.getString("img");
                    if (imageName != null) {
                        try {
                            myHeroImageView.setImage(new Image("img/" + imageName));
                        } catch (IllegalArgumentException iae) {
                            System.err.print("Received Image path (" + imageName + ") from Database, However: ");
                            System.err.println(iae.getMessage());
                        }
                    } else {
                        myHeroImageView.setImage(null); // TODO -JA: Set to a default image
                        System.err.println(myHeroTypeCB.getValue() + " has no image!");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        });

        // Populate HeroType Choice Box
        String query = "SELECT friendly_name FROM character where isHero = 1";
        try (SQLite db = new SQLite(query)) {
            ResultSet rs = db.getMyResults();
            while (rs.next()) {
                myHeroTypeCB.getItems().add(rs.getString("friendly_name"));
            }

            myHeroTypeCB.setValue(myHeroTypeCB.getItems().getFirst());
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void initHeroTextBox() {
        // Display Hero name under Image and clamp to MAX_NAME_LENGTH
        myHeroNameTextField.setOnKeyTyped(keyEvent -> {
            TextField tf = myHeroNameTextField;
            if (tf.getText().length() > DungeonCharacter.MAX_NAME_LENGTH) { // Clamp max HERO length
                tf.setText(tf.getText().substring(0, DungeonCharacter.MAX_NAME_LENGTH));
                tf.positionCaret(tf.getLength()); // Reset caret to the end to avoid overwrite
            }
            myHeroNameDisplayText.setText(tf.getText());
        });
    }

    private void initSliders() {
        // Write slider value text
        myMapWidthSlider.valueProperty().addListener(
                (observable, oldVal, newVal) -> myWidthSliderValue.setText(newVal.intValue()+"")
        );

        // Write slider value text
        myMapHeightSlider.valueProperty().addListener(
                (o, oldVal, newVal) -> myHeightSliderValue.setText(newVal.intValue()+"")
        );
    }
}