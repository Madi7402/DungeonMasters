package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.DungeonCharacter;
import res.SQLite;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewGameController {

    @FXML
    private ComboBox<String> myHeroTypeCB;

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

    public void initialize() {
        final String query = "SELECT friendly_name FROM character where isHero = 1";
        try (SQLite db = new SQLite(query)) {
            ResultSet rs = db.getMyResults();
            while (rs.next()) {
                myHeroTypeCB.getItems().add(rs.getString("friendly_name"));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        myMapWidthSlider.valueProperty().addListener(
                (observable, oldVal, newVal) -> myWidthSliderValue.setText(newVal.intValue()+"")
        );

        myMapHeightSlider.valueProperty().addListener(
                (o, oldVal, newVal) -> myHeightSliderValue.setText(newVal.intValue()+"")
        );

        myHeroNameTextField.setOnKeyTyped(keyEvent -> {
            TextField tf = myHeroNameTextField;
            if (tf.getText().length() > DungeonCharacter.MAX_NAME_LENGTH) { // Clamp max HERO length
                tf.setText(tf.getText().substring(0, DungeonCharacter.MAX_NAME_LENGTH));
                tf.positionCaret(tf.getLength()); // Reset caret to the end to avoid overwrite
            }
            myHeroNameDisplayText.setText(tf.getText());
        });
    }
}