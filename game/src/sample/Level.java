package sample;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
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
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;
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
    List<Zombies> zombies_on_screen;
    List<Double> time;
    Text no_of_suns=new Text(210,42,"50");
    HashMap<Zombies,Plants> stopped_zombies;

    Level(Player p, Pane lp) {
        zombies_on_screen=new ArrayList<Zombies>();
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
        stopped_zombies=new HashMap<Zombies,Plants>();
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
                    Runnable updater=new Runnable() {
                        @Override
                        public void run() {
                            zombies_on_screen.add(new zombiemover(list_of_zombies).get_j_zombie());
                            zombies_on_screen.get(zombies_on_screen.size()-1).move();
                            for(int i=0;i<list_of_plants.size();i++)
                                zombies_on_screen.get(zombies_on_screen.size()-1).plant_added(list_of_plants.get(i));
                        }
                    };
                    try {
                        TimeUnit.MILLISECONDS.sleep((int)(time.get(j)*1000));
                    }
                    catch (InterruptedException e) { }
                    Platform.runLater(updater);
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
                        List<Double> y_of_zombie=new ArrayList<Double>();
                        for(int j=0;j<zombies_on_screen.size();j++) {
                            y_of_zombie.add(zombies_on_screen.get(j).getZombie_image().getLayoutY());
                        }

                        int[] arr={0,0,0,0,0};
                        if(y_of_zombie.contains(239.0)) {
                            arr[2] = 1;
                        }

                        for(int i=0;i<list_of_shooters.size();i++) {
                            if(arr[list_of_shooters.get(i).getTile_no()/9]==1)
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
                        TimeUnit.MILLISECONDS.sleep(1000);
                    }
                    catch (InterruptedException e) {
                    }
                }
            }
        });
        t5.setDaemon(true);
        t5.start();

        Thread t6=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!level_complete) {
                    check_plant_zombie_coll();
                    check_stopped_zombies();
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    }
                    catch (InterruptedException e) { }
                }
            }
        });
        t6.setDaemon(true);
        t6.start();
    }

    public void check_plant_zombie_coll() {
        for(int i=0;i<zombies_on_screen.size();i++) {
            for (int j = 0; j < list_of_plants.size(); j++) {
                ImageView zom=zombies_on_screen.get(i).getZombie_image();
                ImageView pl=list_of_plants.get(j).getImg();
                //System.out.println(zom.getBoundsInParent().getMinX()+" "+pl.getBoundsInParent().getMinX());
                //System.out.println(zom.getBoundsInParent().getMinY()+" "+pl.getBoundsInParent().getMinY());
                if(zom.getBoundsInParent().getMinX()-pl.getBoundsInParent().getMinX()<80 &&
                        zom.getBoundsInParent().getMinY()-pl.getBoundsInParent().getMinY()<0 &&
                        -zom.getBoundsInParent().getMinY()+pl.getBoundsInParent().getMinY()<80 &&
                        pl.getImage()!=player.getImg())

                    zombies_on_screen.get(i).getTt().stop();
                Duration t=zombies_on_screen.get(i).getTt().getCurrentTime();
                double mili=t.toMillis();
                zombies_on_screen.get(i).setMili(mili);
                stopped_zombies.put(zombies_on_screen.get(i),list_of_plants.get(j));

            }
        }
    }

    public void check_stopped_zombies() {
        List<Zombies> rem_zombies=new ArrayList<Zombies>();
        stopped_zombies.forEach((zom,pl)-> {
            if(pl.getImg().getImage()==player.getImg()) {
                zom.getTt().play();
                rem_zombies.add(zom);
            }
        });
        for(int i=0;i<rem_zombies.size();i++) {
            stopped_zombies.remove(rem_zombies.get(i));
        }

    }
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

        ObservableBooleanValue colliding = Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return pea.localToScene(pea.getBoundsInLocal()).intersects(list_of_zombies.get(0).getZombie_image().localToScene(list_of_zombies.get(0).getZombie_image().getBoundsInLocal()));
            }
        },pea.boundsInParentProperty(),list_of_zombies.get(0).getZombie_image().boundsInParentProperty());

        colliding.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) pea.setVisible(false);
            }
        });

        //list_of_peas.add(pea);
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
            p = new PeaShooter(lawn_parent,i,tile);
            list_of_shooters.add((PeaShooter)p);
            player.plant_purchased(100);
        }
        else if(x==2) {
            p = new Wallnut(i);
            player.plant_purchased(50);
        }
        else if(x==3) {
            p = new CherryBomb();
            player.plant_purchased(150);
        }
        p.setTile(tile);
        list_of_plants.add(p);
        aware_zombie(p);
    }

    public void aware_zombie(Plants p) {
        for(int i=0;i<list_of_zombies.size();i++) {
            list_of_zombies.get(i).plant_added(p);
        }

    }

}


class zombiemover
{
    int pos;
    static List<Zombies> loz;
    static int j=-1;
    zombiemover(List<Zombies> l) {
        j+=1;
        loz=new ArrayList<Zombies>(l);
    }

    public static Zombies get_j_zombie() {
        return loz.get(j);
    }
}