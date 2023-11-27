package view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.sqlite.SQLiteDataSource;
import res.SQLite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NewGameController {

    @FXML
    private ComboBox<String> myHeroTypeCB;

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
    }
}