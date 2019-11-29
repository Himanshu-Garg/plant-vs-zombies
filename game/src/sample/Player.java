package sample;

import javafx.scene.layout.Pane;

public class Player {

    Level level;

    private int no_of_suns=50;

    public void sun_collected(int n) {
        no_of_suns+=n;
        level.update_no_of_suns(no_of_suns);
    }

    public void set_level(Level l) {
        level=l;
        level.update_no_of_suns(no_of_suns);
    }

}
