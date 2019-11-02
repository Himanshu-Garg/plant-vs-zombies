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
    void clicked_choose_level(MouseEvent event) {

    }

    /**
     * will change scene when new game button is being clicked...
     * i.e when this function will run, scene will be changed.
     * **/
    @FXML
    void clicked_new_game(MouseEvent event) throws IOException {

        Pane lawn_parent = FXMLLoader.load(getClass().getResource("/fxml/lawn.fxml"));
        Scene lawn_scene = new Scene(lawn_parent);

        Text no_of_suns=new Text(220,42,"50");
        no_of_suns.setFont(Font.font("Verdana", FontWeight.BOLD,30));

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
            lawn_parent.getChildren().remove(falling_sun);
            /*falling_sun.setVisible(false);*/
        });

        lawn_parent.getChildren().add(falling_sun);
        lawn_parent.getChildren().add(no_of_suns);

        /*ImageView peaplant =new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea_shooter.gif")));
        peaplant.setLayoutX(293); peaplant.setLayoutY(297); peaplant.setFitHeight(76); peaplant.setFitWidth(77);*/

        ImageView normzombie=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/zombie_normal.gif")));
        normzombie.setLayoutX(1139); normzombie.setLayoutY(239); normzombie.setFitHeight(138); normzombie.setFitWidth(100);

        ImageView pea=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea.png")));
        pea.setVisible(false); pea.setX(344); pea.setY(307); pea.setFitHeight(34); pea.setFitWidth(31);

        TranslateTransition tt2=new TranslateTransition();
        tt2.setDuration(Duration.seconds(15));
        tt2.setNode(normzombie);
        tt2.setToX(-659);
        tt2.play();

        /*TranslateTransition tt1=new TranslateTransition();
        pea.setVisible(true);
        tt1.setDuration(Duration.seconds(2.8));
        tt1.setNode(pea);
        tt1.setToX(1200);
        tt1.play();*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(pea.isVisible())
                {
                    Bounds obj1=pea.localToScene(pea.getBoundsInLocal());
                    Bounds obj2=normzombie.localToScene(normzombie.getBoundsInLocal());
                    if(obj1.intersects(obj2))
                    {
                        pea.setVisible(false);
                    }
                }
                lawn_parent.getChildren().remove(pea);
            }
        }).start();

        ImageView buysun =new ImageView(new Image(getClass().getResourceAsStream("../main/resources/suncost.png")));
        buysun.setLayoutX(9);buysun.setLayoutY(7);buysun.setFitWidth(102);buysun.setFitHeight(57);

        ImageView buypea =new ImageView(new Image(getClass().getResourceAsStream("../main/resources/peacost.png")));
        buypea.setLayoutX(9);buypea.setLayoutY(75);buypea.setFitWidth(102);buypea.setFitHeight(57);

        Image sunplanting=new Image(getClass().getResourceAsStream("../main/resources/HD_Sunflower.png"));
        Image peaplanting=new Image(getClass().getResourceAsStream("../main/resources/PEAPLANT.JPG"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                int f=0;
            }
        }).start();
        ImageView tile1 =new ImageView(new Image(getClass().getResourceAsStream("../main/resources/tiles/37.png")));
        tile1.setLayoutX(213);tile1.setLayoutY(79);tile1.setFitHeight(95);tile1.setFitWidth(81);

        ImageView tile8 =new ImageView(new Image(getClass().getResourceAsStream("../main/resources/tiles/20.png")));
        tile8.setLayoutX(297);tile8.setLayoutY(276);tile8.setFitHeight(103);tile8.setFitWidth(77);

        buysun.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("drag detected");
                Dragboard db = buysun.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(sunplanting);
                db.setContent(content);
                event.consume();
            }
        });

        tile1.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Bloom bl=new Bloom();
                bl.setThreshold(0.3);
                tile1.setEffect(bl);
            }
        });

        tile1.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                tile1.setEffect(null);
            }
        });

        tile1.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != tile1) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        tile1.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Bloom bl=new Bloom();
                bl.setThreshold(0.3);
                tile1.setEffect(bl);
            }
        });

        tile1.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                tile1.setEffect(null);
            }
        });

        tile1.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != tile1) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        tile1.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                tile1.setImage(new Image(getClass().getResourceAsStream("../main/resources/sun_flower.gif")));
                buysun.setDisable(true);
                ColorAdjust ca=new ColorAdjust();
                ca.setBrightness(-0.6);
                buysun.setEffect(ca);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.MILLISECONDS.sleep(5000);
                        }
                        catch(InterruptedException e) {
                            System.out.println("Cant sleep");
                        }
                        buysun.setEffect(null);
                        buysun.setDisable(false);
                    }
                }).start();
                event.setDropCompleted(true);
                event.consume();
            }
        });

        buypea.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("drag detected");
                Dragboard db = buypea.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(peaplanting);
                db.setContent(content);
                event.consume();
            }
        });

        tile8.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Bloom bl=new Bloom();
                bl.setThreshold(0.3);
                tile8.setEffect(bl);
            }
        });

        tile8.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                tile8.setEffect(null);
            }
        });

        tile8.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != tile8) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        Thread zombiedead=new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    TimeUnit.MILLISECONDS.sleep(2800*2);
                }
                catch (InterruptedException e)
                {
                    System.out.println("Cant sleep");
                }
                normzombie.setImage(new Image(getClass().getResourceAsStream("../main/resources/zombie_normal_dying.gif")));
                try
                {
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    System.out.println("Cant sleep");
                }
                /*normzombie.setVisible(false);*/
                lawn_parent.getChildren().remove(normzombie);
            }
        });


        tile8.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                tile8.setImage(new Image(getClass().getResourceAsStream("../main/resources/pea_shooter.gif")));
                buypea.setDisable(true);
                ColorAdjust ca=new ColorAdjust();
                TranslateTransition tt1=new TranslateTransition();
                pea.setVisible(true);
                tt1.setDuration(Duration.seconds(2.8));
                tt1.setNode(pea);
                tt1.setToX(1200);
                tt1.setCycleCount(10);
                tt1.play();
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(pea.isVisible())
                        {
                            Bounds obj1=pea.localToScene(pea.getBoundsInLocal());
                            Bounds obj2=normzombie.localToScene(normzombie.getBoundsInLocal());
                            if(obj1.intersects(obj2))
                            {
                                pea.setVisible(false);
                            }
                        }
                        lawn_parent.getChildren().remove(pea);
                    }
                }).start();*/

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            TimeUnit.MILLISECONDS.sleep(4800);
                        }
                        catch (InterruptedException e)
                        {
                            System.out.println("Cant sleep");
                        }
                        normzombie.setImage(new Image(getClass().getResourceAsStream("../main/resources/zombie_normal_dying.gif")));
                        try
                        {
                            TimeUnit.MILLISECONDS.sleep(1000);
                        }
                        catch (InterruptedException e)
                        {
                            System.out.println("Cant sleep");
                        }
                        /*normzombie.setVisible(false);*/
                        normzombie.setVisible(false);
                        lawn_parent.getChildren().remove(normzombie);
                    }
                }).start();
                ca.setBrightness(-0.6);
                buypea.setEffect(ca);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.MILLISECONDS.sleep(5000);
                        }
                        catch(InterruptedException e) {
                            System.out.println("Cant sleep");
                        }
                        buypea.setEffect(null);
                        buypea.setDisable(false);
                    }
                }).start();
                event.setDropCompleted(true);
                event.consume();
            }
        });

        lawn_parent.getChildren().add(tile8);
        lawn_parent.getChildren().add(tile1);
        lawn_parent.getChildren().add(pea);
        lawn_parent.getChildren().add(buysun);
        lawn_parent.getChildren().add(buypea);
        lawn_parent.getChildren().add(normzombie);
/*
        lawn_parent.getChildren().add(peaplant);
*/

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

