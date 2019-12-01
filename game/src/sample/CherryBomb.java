package sample;


import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CherryBomb extends Plants {

    List<Zombies> loz;
    CherryBomb(List<Zombies> l, ImageView im) {
        img=im;
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) { }
        explode();
        loz=new ArrayList<>(l);
    }
    public void explode() {
        for(int i=0;i<loz.size();i++) {
            double xcen=img.getLayoutX()+47;
            double ycen=img.getLayoutX()+44.5;
            double zx=loz.get(i).getZombie_image().getBoundsInParent().getMinX();
            double zy=loz.get(i).getZombie_image().getBoundsInParent().getMinY();
            if(xcen-141<zx && zx<xcen+141 && ycen-133.5<zy && zy<ycen+133.5)
                level.zombie_killed(loz.get(i),1);
        }
    }
}
