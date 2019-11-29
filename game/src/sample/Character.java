package sample;

abstract public class Character {
    protected int tile_no;
    protected int hp;

    public int getTile_no() {
        return tile_no;
    }

    public int getHp() {
        return hp;
    }

    abstract public void attack(Character c);

    public void setHp(int hp) {
        this.hp = hp;
    }
}
