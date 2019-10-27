package sample;

import javafx.fxml.FXML;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class in_game_menu_controller {

    @FXML
    private ImageView back;

    private Stage lawn_window;

    @FXML
    void back_clicked(MouseEvent event) {

        // un-blur the lawn window
        BoxBlur blur = new BoxBlur(5,5,5);
        lawn_window.getScene().getRoot().setEffect(null);

        // following line is used to get the stage information...
        Stage window = (Stage)back.getScene().getWindow();
        window.close();
    }

    void setLawn_window(Stage l) {
        lawn_window = l;
    }

}
