package sample;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


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
        Pane lawn_parent = FXMLLoader.load(getClass().getResource("/fxml/lawn.fxml"));
        Scene lawn_scene = new Scene(lawn_parent);

        Text no_of_suns=new Text(220,42,"0");
        no_of_suns.setFont(Font.font("Verdana", FontWeight.BOLD,35));

        ImageView falling_sun=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/brightsun.png")));
        falling_sun.setX(665); falling_sun.setY(-50); falling_sun.setFitWidth(60); falling_sun.setFitHeight(60);

        TranslateTransition tt=new TranslateTransition();
        tt.setDuration(Duration.seconds(8));
        tt.setNode(falling_sun);
        tt.setToY(641);
        tt.play();

        falling_sun.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
            String k=no_of_suns.getText();
            no_of_suns.setText(Integer.toString(25+Integer.parseInt(k)));
            falling_sun.setVisible(false);
        });

        lawn_parent.getChildren().add(falling_sun);
        lawn_parent.getChildren().add(no_of_suns);

        ImageView peaplant =new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea_shooter.gif")));
        peaplant.setLayoutX(293); peaplant.setLayoutY(297); peaplant.setFitHeight(76); peaplant.setFitWidth(77);

        ImageView normzombie=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/zombie_normal.gif")));
        normzombie.setLayoutX(1139); normzombie.setLayoutY(239); normzombie.setFitHeight(138); normzombie.setFitWidth(100);

        ImageView pea=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea.png")));
        pea.setVisible(false);
        pea.setX(344); pea.setY(307); pea.setFitHeight(34); pea.setFitWidth(31);

        TranslateTransition tt2=new TranslateTransition();
        tt2.setDuration(Duration.seconds(15));
        tt2.setNode(normzombie);
        tt2.setToX(-659);
        tt2.play();

        TranslateTransition tt1=new TranslateTransition();
        pea.setVisible(true);
        tt1.setDuration(Duration.seconds(2.8));
        tt1.setNode(pea);
        tt1.setToX(1200);
        tt1.setCycleCount(5);
        tt1.play();

        lawn_parent.getChildren().add(pea);
        lawn_parent.getChildren().add(normzombie);
        lawn_parent.getChildren().add(peaplant);

        // following line is used to get the stage information...
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        // setting scene to window and displaying window...
        window.setScene(lawn_scene);
        window.show();

    }

}
