package view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NewGameController {

    @FXML
    private ComboBox<String> herotype;

    @FXML
    public void initialize() {
        // Initialization logic, if needed
        ComboBox<String> cb = herotype;

        final SQLiteDataSource ds = new SQLiteDataSource(); // From example code
        ds.setUrl("jdbc:sqlite:database.sqlite.db");

        final String query = "SELECT friendly_name FROM character where isHero == 1";

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {

            final ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                cb.getItems().add(rs.getString("friendly_name"));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}