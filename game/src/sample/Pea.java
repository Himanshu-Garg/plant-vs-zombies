package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Pea {
    int x;
    int y;
    ImageView pea;

    Pea(int a,int b) {
        x=a; y=b;
        pea=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea.png")));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
