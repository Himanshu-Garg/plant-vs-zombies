package sample;

import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.swing.text.Element;
import java.util.List;
import java.util.concurrent.Callable;

public class Zombies extends Character {

    protected int attack_value;
    protected int speed;
    protected List<Plants> plants_on_field;
    Pane lawn_parent;
    ImageView zombie_image;
    TranslateTransition tt;
    double mili;

    public double getMili() {
        return mili;
    }

    public void setMili(double mili) {
        this.mili = mili;
    }

    Zombies(Pane lp, List<Plants> l) {
        lawn_parent=lp;plants_on_field=l;
        tt = new TranslateTransition();
        zombie_image=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/zombie_normal.gif")));
        zombie_image.setLayoutX(1139);
        zombie_image.setLayoutY(239);
        zombie_image.setFitHeight(138);
        zombie_image.setFitWidth(100);
        //lawn_parent.getChildren().add(zombie_image);
    }

    public TranslateTransition getTt() {
        return tt;
    }

    public void plant_added(Plants p) {

//        System.out.println(lawn_parent.getChildren().contains(zombie_image));
//        System.out.println(lawn_parent.getChildren().contains(p.getImg()));
//
//        ImageView zom=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea.png")));
//        zom.setX(p.getImg().getLayoutX()+50); zom.setY(p.getImg().getLayoutY()); zom.setFitHeight(34); zom.setFitWidth(31);
//        lawn_parent.getChildren().add(zom);
//
//        ObservableBooleanValue colliding = Bindings.createBooleanBinding(new Callable<Boolean>() {
//
//            @Override
//            public Boolean call() throws Exception {
//                return zombie_image.getBoundsInParent().intersects(zom.getBoundsInParent());
//            }
//
//        }, zombie_image.boundsInParentProperty(), zom.boundsInParentProperty());
//
//        colliding.addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> obs,
//                                Boolean oldValue, Boolean newValue) {
//                if (newValue) {
//                    System.out.println("Colliding");
//                    tt.stop();
//                } else {
//                    System.out.println("Not colliding");
//                }
//            }
//        });
    }

    public int getAttack_value() {
        return attack_value;
    }

    public int getSpeed() {
        return speed;
    }

    public List<Plants> getPlants_on_field() {
        return plants_on_field;
    }

    public void setPlants_on_field(List<Plants> plants_on_field) {
        this.plants_on_field = plants_on_field;
    }

    public void attack(Character c) {
        c.setHp(c.getHp()-attack_value);
    }

    public void move() {
        lawn_parent.getChildren().add(zombie_image);
        tt.setDuration(Duration.seconds(24.1047));
        tt.setNode(zombie_image);
        tt.setToX(-1059);
        tt.play();
    }

    public ImageView getZombie_image() {
        return zombie_image;
    }
}



