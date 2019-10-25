package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.*;


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

    @FXML
    void new_game_clicked(ActionEvent event) {

    }

}
