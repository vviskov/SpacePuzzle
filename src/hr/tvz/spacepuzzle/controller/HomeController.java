package hr.tvz.spacepuzzle.controller;

import hr.tvz.spacepuzzle.util.ResourceLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button btnStart;
    private Stage mainStage;

    public void start(Stage mainStage) {
        this.mainStage=mainStage;
    }

    public void onClickBtnSelectImage(ActionEvent actionEvent) throws IOException {

        FXMLLoader myLoader = ResourceLoader.loadView("ImageGalleryScreen");
        Parent root = myLoader.load();

        mainStage.setScene(new Scene(root, 1920, 1080));
        mainStage.show();

        ImageGalleryController controller = myLoader.getController();
        controller.start(mainStage);
    }
}
