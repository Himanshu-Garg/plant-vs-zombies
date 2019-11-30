package sample;
import javafx.scene.image.ImageView;

import java.util.List;

public class Plants {

    public String description;
    protected int unlock_at_level;
    protected int sun_cost;
    protected int time_to_buy;
    protected int time_to_attack;
    protected List<Zombies> zombies_on_field;
    ImageView img;
    protected int tile_no;

    public int getTile_no() {
        return tile_no;
    }

    public void setTile(int tile_no) {
        this.tile_no = tile_no;
    }

    public ImageView getImg() {
        return img;
    }

    public String getDescription() {
        return description;
    }

    public int getUnlock_at_level() {
        return unlock_at_level;
    }

    public int getSun_cost() {
        return sun_cost;
    }

    public int getTime_to_buy() {
        return time_to_buy;
    }

    public int getTime_to_attack() {
        return time_to_attack;
    }

    public List<Zombies> getZombies_on_field() {
        return zombies_on_field;
    }




}