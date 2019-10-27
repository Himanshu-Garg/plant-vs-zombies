package sample;

import javafx.fxml.FXML;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class in_game_menu_controller {

    @FXML
    private ImageView back;

    @FXML
    private ImageView restart;

    @FXML
    private ImageView save;

    @FXML
    private ImageView exit;

    private Stage lawn_window;

    @FXML
    void back_clicked(MouseEvent event) {

        // un-blur the lawn window
        BoxBlur blur = new BoxBlur(5,5,5);
        lawn_window.getScene().getRoot().setEffect(null);

        // following line is used to get the stage information...
        Stage window = (Stage)back.getScene().getWindow();
        window.close();
    }

    @FXML
    void back_entered(MouseEvent event) {
        glow_image(back);
    }

    @FXML
    void back_exited(MouseEvent event) {
        unglow_image(back);
    }

    @FXML
    void exit_clicked(MouseEvent event) {

    }

    @FXML
    void exit_entered(MouseEvent event) {
        glow_image(exit);
    }

    @FXML
    void exit_exited(MouseEvent event) {
        unglow_image(exit);
    }

    @FXML
    void restart_clicked(MouseEvent event) {

    }

    @FXML
    void restart_entered(MouseEvent event) {
        glow_image(restart);
    }

    @FXML
    void restart_exited(MouseEvent event) {
        unglow_image(restart);
    }

    @FXML
    void save_clicked(MouseEvent event) {

    }

    @FXML
    void save_entered(MouseEvent event) {
        glow_image(save);
    }

    @FXML
    void save_exited(MouseEvent event) {
        unglow_image(save);
    }

    // self-defined functions

    private void glow_image(ImageView i){
        Glow glow = new Glow();
        glow.setLevel(0.7);
        i.setEffect(glow);
    }

    private void unglow_image(ImageView i){
        Glow glow = new Glow();
        glow.setLevel(0);
        i.setEffect(glow);
    }

    void setLawn_window(Stage l) {
        lawn_window = l;
    }

    // self-defined functions ends here ------



}











