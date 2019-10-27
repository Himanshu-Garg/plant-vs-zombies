package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class lawn_controller {

    @FXML
    private AnchorPane root_AnchorPane;

    @FXML
    private ImageView menu;

    @FXML
    void menu_clicked(MouseEvent event) throws IOException {
        System.out.println("Menu button clicked...");

        // to blur the current anchor pane
        BoxBlur blur = new BoxBlur(5,5,5);
        root_AnchorPane.setEffect(blur);

        // add the function to stop all the threads here.......
        //
        //
        //
        //


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/in_game_menu.fxml"));
        loader.load();

        // to set lawn_window in in_game_menu_controller
        in_game_menu_controller in_game_menu = loader.getController();
        in_game_menu.setLawn_window((Stage) root_AnchorPane.getScene().getWindow());


        // to create the scene and make it transparent  .... and also creating the stage
        Scene menu_scene = new Scene(loader.getRoot());
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("IN-GAME Options");

        menu_scene.setFill(Color.TRANSPARENT);

        // setting scene to window and displaying window...
        window.setScene(menu_scene);
        window.setResizable(false);

        // to hide the above cross/minimize option...
        window.initStyle(StageStyle.TRANSPARENT);
        window.showAndWait();

    }

    @FXML
    void menu_entered(MouseEvent event) {
        glow_image(menu);
    }

    @FXML
    void menu_exited(MouseEvent event) {
        unglow_image(menu);
    }

    // self-defined functions

    private void glow_image(ImageView i){
        Glow glow = new Glow();
        glow.setLevel(0.7);
        i.setEffect(glow);
    }

    private void unglow_image(ImageView i){
        Glow glow = new Glow();
        glow.setLevel(0);
        i.setEffect(glow);
    }

    // self-defined functions ends here ------

}













