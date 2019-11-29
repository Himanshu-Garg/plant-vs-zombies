package sample;

import java.util.List;

public class Zombies extends Character {
    protected int attack_value;
    protected int speed;
    protected List<Plants> plants_on_field;

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

    public void attack(Character c)
    {
        c.setHp(c.getHp()-attack_value);
    }

    public void move()
    {

    }


}



