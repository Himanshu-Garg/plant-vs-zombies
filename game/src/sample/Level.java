package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sun.security.provider.Sun;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Level {

    Player player;
    Pane lawn_parent;
    boolean level_complete;
    volatile List<Plants> list_of_plants;
    List<Zombies> list_of_zombies;
    volatile List<PeaShooter> list_of_shooters;
    volatile List<Sunflower> list_of_sunflowers;
    volatile List<ImageView> list_of_peas;
    List<Double> time;
    Text no_of_suns=new Text(210,42,"50");

    Level(Player p, Pane lp) {
        player=p;
        lawn_parent=lp;
        level_complete=false;
        no_of_suns.setFont(Font.font("Verdana", FontWeight.BOLD,30));
        no_of_suns.setText("50");
        lawn_parent.getChildren().add(no_of_suns);
        list_of_sunflowers=new ArrayList<Sunflower>();
        list_of_peas=new ArrayList<ImageView>();
        list_of_shooters=new ArrayList<PeaShooter>();
        list_of_plants=new ArrayList<Plants>();
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
                        TimeUnit.MILLISECONDS.sleep(1000);
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

        Thread t3=new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater=new Runnable() {
                    @Override
                    public void run() {
                        for(int j=0;j<list_of_sunflowers.size();j++) {
                            spawn_sun_for_flower(list_of_sunflowers.get(j).getSunposx(),list_of_sunflowers.get(j).getSunposy());
                        }
                    }
                };
                while(!level_complete) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(7000);
                    }
                    catch (InterruptedException e) { }
                    Platform.runLater(updater);
                }
            }
        });
        t3.setDaemon(true);
        t3.start();

        Thread t4=new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater =new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<list_of_shooters.size();i++) {
                            make_pea(list_of_shooters.get(i).getpeaposx(),list_of_shooters.get(i).getpeaposy());
                        }
                    }
                };

                while(!level_complete) {
                    list_of_peas.clear();
                    try {
                        TimeUnit.MILLISECONDS.sleep(2800);
                    }
                    catch (InterruptedException e) { }
                    Platform.runLater(updater);
                }

            }
        });
        t4.setDaemon(true);
        t4.start();

        Thread t5=new Thread(new Runnable() {
            int j=0;

            @Override
            public void run() {
                if(j==0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(2800);
                    }
                    catch (InterruptedException e) { }
                }
                while(!level_complete) {
                    check_collision();
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    }
                    catch (InterruptedException e) {
                    }
                }
            }
        });
        t5.setDaemon(true);
        t5.start();
    }

//    class make_pea
//    {
//        double x,y;
//        ImageView pea;
//
//        make_pea(double x, double y) {
//            this.x=x;
//            this.y=y;
//            pea=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea.png")));
//            pea.setX(x); pea.setY(y); pea.setFitHeight(34); pea.setFitWidth(31);
//        }
//
//        public ImageView getPea() {
//            return pea;
//        }
//
//        public void run() {
//            TranslateTransition tt1=new TranslateTransition();
//            tt1.setDuration(Duration.seconds(2.8));
//            tt1.setNode(pea);
//            tt1.setToX(1200);
//            tt1.play();
//
////            t=new Thread(new Runnable() {
////                @Override
////                public void run() {
////                    while(pea.isVisible() && !stop_thread) {
////                        Bounds obj1=pea.localToScene(pea.getBoundsInLocal());
////                        for(int j=0;j<list_of_zombies.size();j++) {
////                            Bounds obj2=list_of_zombies.get(j).getZombie_image().localToScene(list_of_zombies.get(j).getZombie_image().getBoundsInLocal());
////                            if(obj1.intersects(obj2)) {
////                                pea.setVisible(false);
////                            }
////                        }
////                    }
////                }
////            });
////            t.setName("I am super annoying");
////            t.setDaemon(true);
////            t.start();
//        }
//    }

    public void check_collision() {
        for(int i=0;i<list_of_peas.size();i++) {
            for(int j=0;j<list_of_zombies.size();j++) {
                try {
                    ImageView pea = list_of_peas.get(i);
                    Zombies zombie = list_of_zombies.get(j);
                    Bounds obj1 = pea.localToScene(pea.getBoundsInLocal());
                    Bounds obj2 = zombie.getZombie_image().localToScene(zombie.getZombie_image().getBoundsInLocal());
                    if (obj1.intersects(obj2)) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(50);
                        } catch (InterruptedException e) {
                        }
                        pea.setVisible(false);
                    }
                } catch (Exception e) { }
            }
        }
    }

    public void make_pea(double x,double y) {
        ImageView pea=new ImageView(new Image(getClass().getResourceAsStream("../main/resources/pea.png")));
        pea.setX(x); pea.setY(y); pea.setFitHeight(34); pea.setFitWidth(31);

        TranslateTransition tt1=new TranslateTransition();
        tt1.setDuration(Duration.seconds(2.8));
        tt1.setNode(pea);
        tt1.setToX(1200);
        tt1.play();

        list_of_peas.add(pea);
        lawn_parent.getChildren().add(pea);
    }

    public void plant_removed(int tile) {
        System.out.println(list_of_plants==null);
        Plants p=null;

        for(int i=0;i<list_of_plants.size();i++) {
            if(list_of_plants.get(i).getTile_no()==tile) {
                p=list_of_plants.get(i);
                System.out.println("reached");
                break;
            }
        }
        try {
            list_of_sunflowers.remove(p);
        }
        catch (Exception e) { }

        try {
            list_of_shooters.remove(p);
        }
        catch (Exception e) { }
    }


    public void spawn_sun_for_flower(double x, double y) {
        ImageView falling_sun = new ImageView(new Image(getClass().getResourceAsStream("../main/resources/brightsun.png")));
        falling_sun.setX(x); falling_sun.setY(y); falling_sun.setFitWidth(60); falling_sun.setFitHeight(60);

        falling_sun.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
            player.sun_collected(25);
            lawn_parent.getChildren().remove(falling_sun);
            System.gc();
        });
        lawn_parent.getChildren().add(falling_sun);
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


    public void place_plant(int x, ImageView i, int tile) {
        Plants p=null;
        if(x==0) {
            p = new Sunflower(i);
            list_of_sunflowers.add((Sunflower) p);
            player.plant_purchased(50);
        }
        else if(x==1) {
            p = new PeaShooter(lawn_parent,i);
            list_of_shooters.add((PeaShooter)p);
            player.plant_purchased(100);
        }
        else if(x==2) {
            p = new Wallnut();
            player.plant_purchased(50);
        }
        else if(x==3) {
            p = new CherryBomb();
            player.plant_purchased(150);
        }
        p.setTile(tile);
        list_of_plants.add(p);

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