package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Level {

    Player player;
    Pane lawn_parent;
    boolean level_complete;
    List<Plants> list_of_plants;
    List<Zombies> list_of_zombies;
    List<Double> time;
    Text no_of_suns=new Text(210,42,"50");

    Level(Player p, Pane lp) {
        player=p;
        lawn_parent=lp;
        level_complete=false;
        no_of_suns.setFont(Font.font("Verdana", FontWeight.BOLD,30));
        no_of_suns.setText("50");
        lawn_parent.getChildren().add(no_of_suns);
    }

    public void update_no_of_suns(int n) {
        no_of_suns.setText(Integer.toString(n));
    }

    public void start_level()
    {
        Thread t1=new Thread(new Runnable() {
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
        t1.setDaemon(true);
        t1.start();

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                int j=0;
                while(j<time.size()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep((int)(time.get(j)*1000));
                    }
                    catch (InterruptedException e) { }
                    Platform.runLater(new zombiemover(j,list_of_zombies));
                    j+=1;
                }
            }
        });
        t2.setDaemon(true);
        t2.start();
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
            player.sun_collected(25);
            tt.stop();
            lawn_parent.getChildren().remove(falling_sun);
            System.gc();
        });
        lawn_parent.getChildren().add(falling_sun);
    }


    public void place_plant(PeaShooter p) {
        list_of_plants.add(p);
        p.setShoot(true);
    }

}


class zombiemover implements Runnable
{
    int pos;
    List<Zombies> loz;
    zombiemover(int i, List<Zombies> l) {
        pos=i;
        loz=new ArrayList<Zombies>(l);
    }

    @Override
    public void run() {
        loz.get(pos).move();
    }
}