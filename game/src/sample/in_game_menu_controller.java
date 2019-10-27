package sample;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class in_game_menu_controller {

    @FXML
    private ImageView back;

    @FXML
    void back_clicked(MouseEvent event) {

        // following line is used to get the stage information...
        Stage window = (Stage)back.getScene().getWindow();
        window.close();
    }

}
