package sample;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class welcome_controller {

    // self-defined functions

    private void glow_image(ImageView i){
        Glow glow = new Glow();
        glow.setLevel(1.0);
        i.setEffect(glow);
    }

    private void unglow_image(ImageView i){
        Glow glow = new Glow();
        glow.setLevel(0.3);
        i.setEffect(glow);
    }

    // self-defined functions ends here ------

    @FXML
    private ImageView new_game_image;

    @FXML
    private ImageView choose_level_image;

    @FXML
    private ImageView load_game_image;

    @FXML
    private ImageView exit_image;

    @FXML
    void clicked_exit_game(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void clicked_load_game(MouseEvent event) {

    }

    @FXML
    void clicked_choose_level(MouseEvent event) throws IOException {
        Pane level_parent = FXMLLoader.load(getClass().getResource("/fxml/choose_level_screen.fxml"));
        Scene level_scene=new Scene(level_parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(level_scene);
        window.show();
    }

    /**
     * will change scene when new game button is being clicked...
     * i.e when this function will run, scene will be changed.
     * **/
    @FXML
    void clicked_new_game(MouseEvent event) throws IOException {

        FXMLLoader loader=new FXMLLoader();

        Pane lawn_parent = loader.load(getClass().getResource("/fxml/lawn.fxml"));
        Scene lawn_scene = new Scene(lawn_parent);
        Player player=new Player();

        Level level1=new Level1(player,lawn_parent);
        player.set_level(level1);
        level1.start_level();


        ImageView pea=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea.png")));
        pea.setVisible(false); pea.setX(344); pea.setY(290); pea.setFitHeight(34); pea.setFitWidth(31);


        ImageView buysun =new ImageView(new Image(getClass().getResourceAsStream("../main/resources/suncost.png")));
        buysun.setLayoutX(9);buysun.setLayoutY(7);buysun.setFitWidth(102);buysun.setFitHeight(57);

        ImageView buypea =new ImageView(new Image(getClass().getResourceAsStream("../main/resources/peacost.png")));
        buypea.setLayoutX(9);buypea.setLayoutY(75);buypea.setFitWidth(102);buypea.setFitHeight(57);

        Image sunplanting=new Image(getClass().getResourceAsStream("../main/resources/HD_Sunflower.png"));
        Image peaplanting=new Image(getClass().getResourceAsStream("../main/resources/PEAPLANT.JPG"));



        ImageView zombiehead =new ImageView(new Image(getClass().getResourceAsStream("../main/resources/head.png")));
        zombiehead.setLayoutX(882);zombiehead.setLayoutY(-6);zombiehead.setFitHeight(57);zombiehead.setFitWidth(58);

        TranslateTransition tt3=new TranslateTransition();
        tt3.setNode(zombiehead);
        tt3.setDuration(Duration.seconds(30));
        tt3.setToX(-82);
        tt3.play();

        lawn_parent.getChildren().add(zombiehead);
        lawn_parent.getChildren().add(pea);
        lawn_parent.getChildren().add(buysun);
        lawn_parent.getChildren().add(buypea);
        // following line is used to get the stage information...
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        // setting scene to window and displaying window...
        window.setScene(lawn_scene);
        window.show();

    }

    @FXML
    void mouse_entered_exit(MouseEvent event) {
        glow_image(exit_image);
    }

    @FXML
    void mouse_entered_load_game(MouseEvent event) {
        glow_image(load_game_image);
    }

    @FXML
    void mouse_entered_new_game(MouseEvent event) { glow_image(new_game_image); }

    @FXML
    void mouse_entered_choose_level(MouseEvent event) { glow_image(choose_level_image); }

    @FXML
    void mouse_exited_exit(MouseEvent event) {
        unglow_image(exit_image);
    }

    @FXML
    void mouse_exited_load_game(MouseEvent event) {
        unglow_image(load_game_image);
    }

    @FXML
    void mouse_exited_new_game(MouseEvent event) {
        unglow_image(new_game_image);
    }

    @FXML
    void mouse_exited_choose_level(MouseEvent event) {
        unglow_image(choose_level_image);
    }
}



























//package sample;
//
//import javafx.animation.TranslateTransition;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.geometry.Bounds;
//import javafx.scene.control.Button;
//import javafx.scene.*;
//import javafx.scene.control.*;
//import javafx.event.*;
//import javafx.scene.effect.Bloom;
//import javafx.scene.effect.ColorAdjust;
//import javafx.scene.effect.Glow;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.*;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.stage.Screen;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.concurrent.TimeUnit;
//
//public class welcome_controller {
//
//    // self-defined functions
//
//    private void glow_image(ImageView i){
//        Glow glow = new Glow();
//        glow.setLevel(1.0);
//        i.setEffect(glow);
//    }
//
//    private void unglow_image(ImageView i){
//        Glow glow = new Glow();
//        glow.setLevel(0.3);
//        i.setEffect(glow);
//    }
//
//    // self-defined functions ends here ------
//
//    @FXML
//    private ImageView new_game_image;
//
//    @FXML
//    private ImageView choose_level_image;
//
//    @FXML
//    private ImageView load_game_image;
//
//    @FXML
//    private ImageView exit_image;
//
//    @FXML
//    void clicked_exit_game(MouseEvent event) {
//        System.exit(0);
//    }
//
//    @FXML
//    void clicked_load_game(MouseEvent event) {
//
//    }
//
//    @FXML
//    void clicked_choose_level(MouseEvent event) throws IOException {
//        Pane level_parent = FXMLLoader.load(getClass().getResource("/fxml/choose_level_screen.fxml"));
//        Scene level_scene=new Scene(level_parent);
//        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//        window.setScene(level_scene);
//        window.show();
//    }
//
//    /**
//     * will change scene when new game button is being clicked...
//     * i.e when this function will run, scene will be changed.
//     * **/
//    @FXML
//    void clicked_new_game(MouseEvent event) throws IOException {
//
//        FXMLLoader loader=new FXMLLoader();
//        loader.setLocation(getClass().getResource("/fxml/lawn.fxml"));
//        loader.load();
//
//        Pane lawn_parent = loader.load(getClass().getResource("/fxml/lawn.fxml"));
//        Scene lawn_scene = new Scene(lawn_parent);
//        Player player=new Player();
//
//        Level level1=new Level1(player,lawn_parent);
//
//        level1.start_level();
//
//        lawn_controller lc = loader.getController();
//        lc.setLawn_parent(lawn_parent);
//
//        Text no_of_suns=new Text(220,42,"50");
//        no_of_suns.setFont(Font.font("Verdana", FontWeight.BOLD,30));
//
////        lawn_parent.getChildren().add(falling_sun);
//        lawn_parent.getChildren().add(no_of_suns);
//
//        /*ImageView peaplant =new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea_shooter.gif")));
//        peaplant.setLayoutX(293); peaplant.setLayoutY(297); peaplant.setFitHeight(76); peaplant.setFitWidth(77);*/
//
//        ImageView normzombie=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/zombie_normal.gif")));
//        normzombie.setLayoutX(1139); normzombie.setLayoutY(239); normzombie.setFitHeight(138); normzombie.setFitWidth(100);
//
//        ImageView pea=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea.png")));
//        pea.setVisible(false); pea.setX(344); pea.setY(290); pea.setFitHeight(34); pea.setFitWidth(31);
//
//        TranslateTransition tt2=new TranslateTransition();
//        tt2.setDuration(Duration.seconds(15));
//        tt2.setNode(normzombie);
//        tt2.setToX(-659);
//        tt2.play();
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(pea.isVisible())
//                {
//                    Bounds obj1=pea.localToScene(pea.getBoundsInLocal());
//                    Bounds obj2=normzombie.localToScene(normzombie.getBoundsInLocal());
//                    if(obj1.intersects(obj2))
//                    {
//                        pea.setVisible(false);
//                    }
//                }
//                lawn_parent.getChildren().remove(pea);
//            }
//        }).start();
//
//
//
//
//        Thread zombiedead=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try
//                {
//                    TimeUnit.MILLISECONDS.sleep(2800*2);
//                }
//                catch (InterruptedException e)
//                {
//                    System.out.println("Cant sleep");
//                }
//                normzombie.setImage(new Image(getClass().getResourceAsStream("../main/resources/zombie_normal_dying.gif")));
//                try
//                {
//                    TimeUnit.MILLISECONDS.sleep(1000);
//                }
//                catch (InterruptedException e)
//                {
//                    System.out.println("Cant sleep");
//                }
//                /*normzombie.setVisible(false);*/
//                lawn_parent.getChildren().remove(normzombie);
//            }
//        });
//
//
//
//        ImageView zombiehead =new ImageView(new Image(getClass().getResourceAsStream("../main/resources/head.png")));
//        zombiehead.setLayoutX(882);zombiehead.setLayoutY(-6);zombiehead.setFitHeight(57);zombiehead.setFitWidth(58);
//
//        TranslateTransition tt3=new TranslateTransition();
//        tt3.setNode(zombiehead);
//        tt3.setDuration(Duration.seconds(30));
//        tt3.setToX(-82);
//        tt3.play();
//
//        lawn_parent.getChildren().add(zombiehead);
//        lawn_parent.getChildren().add(pea);
//        lawn_parent.getChildren().add(normzombie);
///*
//        lawn_parent.getChildren().add(peaplant);
//
//*/
//
//        // experimenting by himanshu
//
//
//
//
//        //experimenting over
//
//        // following line is used to get the stage information...
//        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//
//        // setting scene to window and displaying window...
//        window.setScene(lawn_scene);
//        window.show();
//
//    }
//
//    @FXML
//    void mouse_entered_exit(MouseEvent event) {
//        glow_image(exit_image);
//    }
//
//    @FXML
//    void mouse_entered_load_game(MouseEvent event) {
//        glow_image(load_game_image);
//    }
//
//    @FXML
//    void mouse_entered_new_game(MouseEvent event) { glow_image(new_game_image); }
//
//    @FXML
//    void mouse_entered_choose_level(MouseEvent event) { glow_image(choose_level_image); }
//
//    @FXML
//    void mouse_exited_exit(MouseEvent event) {
//        unglow_image(exit_image);
//    }
//
//    @FXML
//    void mouse_exited_load_game(MouseEvent event) {
//        unglow_image(load_game_image);
//    }
//
//    @FXML
//    void mouse_exited_new_game(MouseEvent event) {
//        unglow_image(new_game_image);
//    }
//
//    @FXML
//    void mouse_exited_choose_level(MouseEvent event) {
//        unglow_image(choose_level_image);
//    }
//}