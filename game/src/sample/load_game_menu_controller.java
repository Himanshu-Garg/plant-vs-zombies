package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.io.*;

public class load_game_menu_controller implements Initializable {

    @FXML
    private ImageView main_menu1;

    @FXML
    private ImageView level1;

    @FXML
    private ImageView level2;

    @FXML
    private ImageView level3;

    @FXML
    private ImageView level4;

    @FXML
    private ImageView level5;

    @FXML
    private ImageView level6;

    @FXML
    void back_to_menu_entered(MouseEvent event) {
        Glow glow = new Glow();
        glow.setLevel(0.7);
        main_menu1.setEffect(glow);
    }

    @FXML
    void back_to_menu_exited(MouseEvent event) {
        Glow glow = new Glow();
        glow.setLevel(0);
        main_menu1.setEffect(glow);
    }

    @FXML
    void clicked_back_to_main_menu(MouseEvent event) throws IOException {
        System.out.println("back-to-main-menu button clicked...");

        Scene lawn_scene = null;
        try {
            Parent lawn_parent = FXMLLoader.load(getClass().getResource("/fxml/welcome.fxml"));
            lawn_scene = new Scene(lawn_parent);
        }
        catch (Exception e) {System.out.println("error in loading_screen_controller.java");}

        // following line is used to get the stage information...
        Stage window = (Stage)level1.getScene().getWindow();

        // setting scene to window and displaying window...
        window.setScene(lawn_scene);
        window.show();

    }


    // self-defined functions

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<ImageView> all = new ArrayList<>();
        all.add(level1);all.add(level2);all.add(level3);all.add(level4);all.add(level5);all.add(level6);

        // names of the saved files
        ArrayList<String> names = new ArrayList<>();
        names.add("saved_games/1.txt");
        names.add("saved_games/2.txt");
        names.add("saved_games/3.txt");
        names.add("saved_games/4.txt");
        names.add("saved_games/5.txt");
        names.add("saved_games/6.txt");

        for(int i=0;i<all.size();i++) {

            String path = names.get(i);
            System.out.println(path);

            File to_check = new File(path);

            if(!to_check.exists()) {
                // i.e cannot be loaded from this file as it don't exists...
                cannot_access(all.get(i));
            }
            else {
                // i.e can be loaded from this file
                can_access(all.get(i));

                // here add code to serialize it when click...
                //
                //
                //
                //
                //
                //
            }


        }

    }


    // helping functions ...

    private void can_access(ImageView i) {
        i.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Glow glow = new Glow();
                glow.setLevel(0);
                i.setEffect(glow);
            }
        });

        i.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Glow glow = new Glow();
                glow.setLevel(0.7);
                i.setEffect(glow);
            }
        });
    }

    private void cannot_access(ImageView i) {
        ColorAdjust ca=new ColorAdjust();
        ca.setBrightness(-0.7);
        i.setEffect(ca);
    }
}
