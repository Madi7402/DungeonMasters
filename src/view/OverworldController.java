package view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Objects;

public class OverworldController extends MenuController {
    @FXML
    private ImageView myHeroImageView;

    @FXML
    private Text myHeroNameDisplayText;

    public void initialize() {
    }

    public void setHeroImage(final Image theImage) {
        myHeroImageView.setImage(Objects.requireNonNull(theImage));
    }

    public void setHeroNameDisplayText(String theText) {
        myHeroNameDisplayText.setText(theText);
    }
}
