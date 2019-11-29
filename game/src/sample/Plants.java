package sample;
import java.util.List;

public class Plants {
    public String description;
    protected int unlock_at_level;
    protected int sun_cost;
    protected int time_to_buy;
    protected int time_to_attack;
    protected List<Zombies> zombies_on_field;

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
