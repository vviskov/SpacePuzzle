package hr.tvz.spacepuzzle;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by valentina on 19.5.2016..
 */
public class GameController {

    @FXML
    private ImageView image;


    public void start() {

        image.setImage(new Image("hr/tvz/spacepuzzle/image1.png", true));
    }
}
