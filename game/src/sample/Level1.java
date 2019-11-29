package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Level1 extends Level {

    Level1(Player p, Pane pl) {
        super(p,pl);
        time= new ArrayList<Double>();
        double[] arr= {1,7,6,4,5,3,3,4,6,5,10,0.1,0.1,0.1,0.1,0.1,0.1,0.1};
        list_of_zombies=new ArrayList<Zombies>();
        for(int i=0;i<arr.length;i++) {
            time.add(arr[i]);
            list_of_zombies.add(new Zombies(lawn_parent));
        }

    }
    public void use_shove(int tile) {

    }
}
