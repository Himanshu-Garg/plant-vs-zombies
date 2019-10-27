package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class lawn_controller {

    @FXML
    private AnchorPane root_AnchorPane;

    @FXML
    void menu_clicked(MouseEvent event) throws IOException {
        System.out.println("Menu button clicked...");

        // to blur the current anchor pane
        BoxBlur blur = new BoxBlur(5,5,5);
        root_AnchorPane.setEffect(blur);

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("IN-GAME Options");

        Parent menu_parent = FXMLLoader.load(getClass().getResource("/fxml/in_game_menu.fxml"));
        Scene menu_scene = new Scene(menu_parent);

        // to make the scene transparent
        menu_scene.setFill(Color.TRANSPARENT);

        // setting scene to window and displaying window...
        window.setScene(menu_scene);
        window.setResizable(false);

        // to hide the above cross/minimize option...
        window.initStyle(StageStyle.TRANSPARENT);

        window.showAndWait();


    }

}
