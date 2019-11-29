package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Level {

    Player player;
    Pane lawn_parent;
    boolean level_complete;
    List<Plants> list_of_plants=new ArrayList<Plants>();
    List<Zombies> list_of_zombies=new ArrayList<Zombies>();

    Level(Player p, Pane lp) {
        player=p;
        lawn_parent=lp;
        level_complete=false;
    }

    public void start_level()
    {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater=new Runnable() {
                    @Override
                    public void run() {
                        spawn_sun();
                    }
                };
                while(!level_complete) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(5000);
                    }
                    catch (InterruptedException e) { }
                    Platform.runLater(updater);
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void spawn_sun() {
        ImageView falling_sun=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/brightsun.png")));
        //randomize setX
        falling_sun.setX(665); falling_sun.setY(-50); falling_sun.setFitWidth(60); falling_sun.setFitHeight(60);
        TranslateTransition tt=new TranslateTransition();
        tt.setDuration(Duration.seconds(8));
        tt.setNode(falling_sun);
        tt.setToY(641);
        tt.play();


        falling_sun.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
            //player.sun_collected(25);
            tt.stop();
            lawn_parent.getChildren().remove(falling_sun);
            System.gc();
        });
        lawn_parent.getChildren().add(falling_sun);
    }


    public void place_plant(Plants p) {
        list_of_plants.add(p);
    }

}
