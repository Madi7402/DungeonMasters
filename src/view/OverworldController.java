package view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.DungeonAdventure;

import java.util.Objects;

public class OverworldController extends MenuController {
    @FXML
    private ImageView myHeroImageView;

    @FXML
    private Text myHeroNameDisplayText;

    private DungeonAdventure myDungeonAdventure;

    public void initialize() {
    }

    public void setHeroImage(final Image theImage) {
        myHeroImageView.setImage(Objects.requireNonNull(theImage));
    }

    public void setHeroNameDisplayText(String theText) {
        myHeroNameDisplayText.setText(theText);
    }

    public void setAdventure(DungeonAdventure theDungeonAdventure) {
        myDungeonAdventure = theDungeonAdventure;
        System.out.println(myDungeonAdventure.getMyDungeon().getMyCurrentRoom());
    }
}
