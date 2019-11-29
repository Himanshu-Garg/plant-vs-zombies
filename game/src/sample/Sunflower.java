package sample;

import javafx.scene.image.ImageView;
import sun.security.provider.Sun;


public class Sunflower extends Plants {
    int sunposx,sunposy;
    ImageView img;

    Sunflower(ImageView im) {
        img=im;
    }

    public double getSunposx() {
        return img.getLayoutX()+52;
    }

    public double getSunposy() {
        return img.getLayoutY()-5;
    }


}
