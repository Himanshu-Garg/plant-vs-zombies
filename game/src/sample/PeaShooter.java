package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

public class PeaShooter extends Plants{

    Pane lawn_parent;

    public Pea getPea() {
        return pea;
    }

    Pea pea;
    boolean shoot;
    PeaShooter(Pane pl,ImageView img) {
        shoot=false;
        lawn_parent=pl;
        this.img=img;
    }

    public double getpeaposx() {
        return img.getLayoutX()+58;
    }

    public double getpeaposy() {
        return img.getLayoutY()+10;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public void shoot_peas() {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater=new Runnable() {
                    @Override
                    public void run() {
                        make_and_move_pea();
                    }
                };
                while(shoot) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(2800);
                    }
                    catch (InterruptedException e) { }
//                    Platform.runLater(make_and_move_pea());
                }
            }
        });

        //Event x=new Event();

        t.setDaemon(true);
        t.start();
    }

    public void make_and_move_pea() {
        ImageView pea=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea.png")));
        pea.setVisible(false); pea.setX(344); pea.setY(290); pea.setFitHeight(34); pea.setFitWidth(31);
        TranslateTransition tt1=new TranslateTransition();
        pea.setVisible(true);
        tt1.setDuration(Duration.seconds(2.8));
        tt1.setNode(pea);
        tt1.setToX(1200);
        tt1.play();

    }


}