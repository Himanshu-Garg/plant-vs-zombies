package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.stage.Stage;


public class welcome_controller {

    @FXML
    private Button new_game_button;

    @FXML
    private Button load_button;

    @FXML
    private Button exit_button;

    @FXML
    void exit_clicked(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void load_game_clicked(ActionEvent event) {

    }

    /**
     * will change scene when new game button is being clicked...
     * i.e when this function will run, scene will be changed.
     * **/
    @FXML
    void new_game_clicked(ActionEvent event) throws Exception{
        Parent lawn_parent = FXMLLoader.load(getClass().getResource("/fxml/lawn.fxml"));
        Scene lawn_scene = new Scene(lawn_parent);

        // following line is used to get the stage information...
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        // setting scene to window and displaying window...
        window.setScene(lawn_scene);
        window.show();


    }

}
