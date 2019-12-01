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

public class    Level {

    Player player;
    Pane lawn_parent;
    boolean level_complete;
    volatile List<Plants> list_of_plants;
    volatile List<Zombies> list_of_zombies;
    volatile List<PeaShooter> list_of_shooters;
    volatile List<Sunflower> list_of_sunflowers;
    volatile List<ImageView> list_of_peas;
    List<Zombies> zombies_on_screen;
    List<Double> time;
    Text no_of_suns=new Text(210,42,"50");
    volatile HashMap<Zombies,Plants> stopped_zombies;
    volatile List<Lawnmower> list_of_lm;
    volatile List<Zombies> row1=new ArrayList<Zombies>();
    volatile List<Zombies> row2=new ArrayList<Zombies>();
    volatile List<Zombies> row3=new ArrayList<Zombies>();
    volatile List<Zombies> row4=new ArrayList<Zombies>();
    volatile List<Zombies> row5=new ArrayList<Zombies>();
    volatile Boolean level_failed;
    int num_of_zombies_killed;
    int level_no;

    Level(Player p, Pane lp) {
        num_of_zombies_killed=0;
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
        list_of_lm=new ArrayList<Lawnmower>();
        list_of_lm.add(new Lawnmower(107,57,lp,5));
        list_of_lm.add(new Lawnmower(105,158,lp,4));
        list_of_lm.add(new Lawnmower(106,265,lp,3));//106
        list_of_lm.add(new Lawnmower(103,370,lp,2));
        list_of_lm.add(new Lawnmower(102,475,lp,1));
        row1=new ArrayList<Zombies>();
        row2=new ArrayList<Zombies>();
        row3=new ArrayList<Zombies>();
        row4=new ArrayList<Zombies>();
        row5=new ArrayList<Zombies>();
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
                            int row=zombies_on_screen.get(zombies_on_screen.size()-1).getRow_number();
                            if(row==1)
                                row1.add(zombies_on_screen.get(zombies_on_screen.size()-1));
                            else if(row==2)
                                row2.add(zombies_on_screen.get(zombies_on_screen.size()-1));
                            else if(row==3)
                                row3.add(zombies_on_screen.get(zombies_on_screen.size()-1));
                            else if(row==4)
                                row4.add(zombies_on_screen.get(zombies_on_screen.size()-1));
                            else if(row==5)
                                row5.add(zombies_on_screen.get(zombies_on_screen.size()-1));

                            zombies_on_screen.get(zombies_on_screen.size()-1).move();
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

                        if(y_of_zombie.contains(25.0)) {
                            arr[4] = 1;
                        }
                        if(y_of_zombie.contains(126.0)) {
                            arr[3] = 1;
                        }
                        if(y_of_zombie.contains(239.0)) {
                            arr[2] = 1;
                        }
                        if(y_of_zombie.contains(321.0)) {
                            arr[1] = 1;
                        }
                        if(y_of_zombie.contains(441.0)) {
                            arr[0] = 1;
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
                        TimeUnit.MILLISECONDS.sleep(100);
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
                    check_victory();
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    }
                    catch (InterruptedException e) { }
                }
            }
        });
        t6.setDaemon(true);
        t6.start();
    }

    public void check_victory() {
        for(int i=0;i<zombies_on_screen.size();i++) {
            if(zombies_on_screen.get(i).getZombie_image().getBoundsInParent().getMinX()==80) {
                level_complete=true;
                level_failed=true;
                //LEVEL Failed zombies ate your brains
                //Code by HIMANSHU
            }
        }
    }
    public void check_plant_zombie_coll() {
        for(int i=0;i<zombies_on_screen.size();i++) {
            for (int j = 0; j < list_of_plants.size(); j++) {
                ImageView zom=zombies_on_screen.get(i).getZombie_image();
                ImageView pl=list_of_plants.get(j).getImg();
                if(zom.getBoundsInParent().getMinX()-pl.getBoundsInParent().getMinX()<85 &&
                        zom.getBoundsInParent().getMinX()-pl.getBoundsInParent().getMinX()>0 &&
                        zom.getBoundsInParent().getMinY()-pl.getBoundsInParent().getMinY()<0 &&
                        -zom.getBoundsInParent().getMinY()+pl.getBoundsInParent().getMinY()<80 &&
                        pl.getImage()!=player.getImg()) {

                    zombies_on_screen.get(i).getTt().stop();
                    stopped_zombies.put(zombies_on_screen.get(i), list_of_plants.get(j));
                }
            }

            List<Lawnmower> to_be_moved=new ArrayList<Lawnmower>();
            ImageView zom=zombies_on_screen.get(i).getZombie_image();
            Lawnmower lmgr=check_lm_exists(zombies_on_screen.get(i).getRow_number());
            if(0<=zom.getBoundsInParent().getMinX() && zom.getBoundsInParent().getMinX()<=180 && lmgr!=null) {
                System.out.println("rchd");
                to_be_moved.add(lmgr);
            }

            for(int j=0;j<to_be_moved.size();j++) {
                list_of_lm.remove(to_be_moved.get(j));
                to_be_moved.get(j).move();
                int row=to_be_moved.get(j).getRow_number();
                if(row==1) {
                    for (int k = 0; k < row1.size(); k++) {
                        row1.get(k).hit_by_pea(10000, 0);
                    }
                    row1.clear();
                }
                if(row==2) {
                    for (int k = 0; k < row2.size(); k++) {
                        row2.get(k).hit_by_pea(10000, 0);
                    }
                    row2.clear();
                }
                if(row==3) {
                    for (int k = 0; k < row3.size(); k++) {
                        row3.get(k).hit_by_pea(10000, 0);
                    }
                    row3.clear();
                }
                if(row==4) {
                    for (int k = 0; k < row4.size(); k++) {
                        row4.get(k).hit_by_pea(10000, 0);
                    }
                    row4.clear();
                }
                if(row==5) {
                    for (int k = 0; k < row5.size(); k++) {
                        row5.get(k).hit_by_pea(10000, 0);
                    }
                    row5.clear();
                }
            }
        }
    }
    public Lawnmower check_lm_exists(int r) {
        for(int i=0;i<list_of_lm.size();i++) {
            if(list_of_lm.get(i).getRow_number()==r)
                return list_of_lm.get(i);
        }
        return null;

    }

    public void check_stopped_zombies() {
        List<Zombies> rem_zombies=new ArrayList<Zombies>();
        stopped_zombies.forEach((zom,pl)-> {
            if(pl.getImg().getImage()==player.getImg()) {
                zom.getTt().play();
                rem_zombies.add(zom);
            }
            else {
                zom.attack_plant(pl);
            }
        });
        for(int i=0;i<rem_zombies.size();i++) {
            stopped_zombies.remove(rem_zombies.get(i));
        }

    }

    public void zombie_killed(Zombies zom,int f) {
        num_of_zombies_killed+=1;

        zombies_on_screen.remove(zom);
        if(f==1) {
            int row=zom.getRow_number();
            if(row==1)
                row1.remove(zom);
            else if(row==2)
                row2.remove(zom);
            else if(row==3)
                row3.remove(zom);
            else if(row==4)
                row4.remove(zom);
            else if(row==5)
                row5.remove(zom);
        }
        if(num_of_zombies_killed==list_of_zombies.size()) {
            level_complete=true;
            level_failed=false;
            int l=level_no;
            //Level won- give reward
            //Code BY HIMANSHU
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
                        } catch (InterruptedException e) { }
                        list_of_peas.remove(pea);
                        pea.setVisible(false);
                        pea.setDisable(true);
                        zombie.hit_by_pea(PeaShooter.getAttack_value(),1);
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

    public void plant_removed(int tile,int x) {
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
            p = new Sunflower(i,this);
            list_of_sunflowers.add((Sunflower) p);
            player.plant_purchased(50);
        }
        else if(x==1) {
            p = new PeaShooter(lawn_parent,i,tile,this);
            list_of_shooters.add((PeaShooter)p);
            player.plant_purchased(100);
        }
        else if(x==2) {
            p = new Wallnut(i,this);
            player.plant_purchased(50);
        }
        else if(x==3) {
            p = new CherryBomb();
            player.plant_purchased(150);
        }
        p.setTile(tile);
        list_of_plants.add(p);
        p.setPlayer(player);
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