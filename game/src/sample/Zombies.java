package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.swing.text.Element;
import java.util.List;

public class Zombies extends Character {

    protected int attack_value;
    protected int speed;
    protected List<Plants> plants_on_field;
    Pane lawn_parent;
    ImageView zombie_image;

    Zombies(Pane lp) {
        lawn_parent=lp;
    }

    {
        zombie_image=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/zombie_normal.gif")));
        zombie_image.setLayoutX(1139);
        zombie_image.setLayoutY(239);
        zombie_image.setFitHeight(138);
        zombie_image.setFitWidth(100);
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
        TranslateTransition tt2=new TranslateTransition();
        tt2.setDuration(Duration.seconds(15));
        tt2.setNode(zombie_image);
        tt2.setToX(-659);
        tt2.play();
        lawn_parent.getChildren().add(zombie_image);
    }

    public ImageView getZombie_image() {
        return zombie_image;
    }
}



