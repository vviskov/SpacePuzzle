package hr.tvz.spacepuzzle;

import hr.tvz.spacepuzzle.controller.HomeController;
import hr.tvz.spacepuzzle.util.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Space Puzzle");
        FXMLLoader myLoader = ResourceLoader.loadView("HomeScreen");
        Parent root = myLoader.load();
        HomeController controller = (HomeController) myLoader.getController();
        controller.start(primaryStage);
        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
