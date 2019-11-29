package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;



public class level_controller implements Initializable {

    @FXML
    private MediaView media;

    private Level level;

    private List<Plants> list_of_plants=new ArrayList<Plants>();

    public void set_level(Level lev) {
        level=lev;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void plant_placed(Plants p) {
        level.place_plant(p);
    }
    //all tiles drag drop
}
