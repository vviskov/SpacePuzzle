package hr.tvz.spacepuzzle.controller;


import hr.tvz.spacepuzzle.model.ImageWithPath;
import hr.tvz.spacepuzzle.util.ResourceLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by valentina on 19.5.2016..
 */
public class ImageGalleryController {
    private static final String IMAGE_FOLDER_PATH = System.getProperty("user.home") + "/SpacePuzzle";
    private static final List<String> ALLOWED_FILES_EXTENSIONS = Arrays.asList(".png", ".jpg", ".jpeg");

    @FXML
    public ListView<String> imageBrowser;

    @FXML
    public ImageView imagePreview;

    private Stage mainStage;

    public void onClickBtnEasyStart(ActionEvent actionEvent) throws IOException {
        startGame(3, 3);
    }

    public void onClickBtnMediumStart(ActionEvent actionEvent) throws IOException {
        startGame(4, 4);
    }

    public void onClickBtnHardStart(ActionEvent actionEvent) throws IOException {
        startGame(5, 5);
    }

    public void startGame(int cols, int rows) throws IOException {

        ImageWithPath image = (ImageWithPath) imagePreview.getImage();
        if (image == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pogreška");
            alert.setHeaderText(null);
            alert.setContentText("Slika mora biti odabrana");

            alert.showAndWait();
            return;
        }
        String imagePath = image.getURL();

        FXMLLoader myLoader = ResourceLoader.loadView("GameScreen");
        Parent root = myLoader.load();
        mainStage.setScene(new Scene(root, 1920, 1080));
        mainStage.show();

        GameController controller = myLoader.getController();
        controller.start(mainStage, imagePath, cols, rows);
    }

    public void start(Stage mainStage) {
        this.mainStage=mainStage;

        final List<Path> files=new ArrayList<>();
        try {
            final File imageFolder = new File(IMAGE_FOLDER_PATH);
            if(!imageFolder.exists()){
                imageFolder.mkdir();
            }
            Files.walkFileTree(Paths.get(IMAGE_FOLDER_PATH), new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(!attrs.isDirectory()){
                        final String fileName = file.toFile().getName();
                        final String extension = fileName.substring(fileName.lastIndexOf('.'));
                        if (ALLOWED_FILES_EXTENSIONS.contains(extension)) {
                            files.add(file);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> filePaths = new ArrayList<String>();
        for (Path file : files) {
            filePaths.add(file.toFile().toURI().toString());
        }

        createListView(filePaths);
    }

    private void createListView(List<String> files) {
        imageBrowser.setItems(FXCollections.observableArrayList(files));
        imageBrowser.setOnMouseClicked(event -> {
            String selectedImageURI = imageBrowser.getSelectionModel().getSelectedItem();
            imagePreview.setImage(new ImageWithPath(selectedImageURI));
            imagePreview.setFitWidth(1200);
            imagePreview.setFitHeight(720);
        });
        imageBrowser.setCellFactory(listView -> new ListCell<String>() {
            @Override
            public void updateItem(String filePath, boolean empty) {
                super.updateItem(filePath, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(null);
                    setGraphic(ImageWithPath.Builder.create(filePath).withSize(560, 315).build());
                }
            }
        });
    }
}
