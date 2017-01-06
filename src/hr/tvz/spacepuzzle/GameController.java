package hr.tvz.spacepuzzle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by valentina on 19.5.2016..
 */
public class GameController {

    @FXML
    private ImageView mainImage;

    @FXML
    private Pane mainPane;

    @FXML
    private GridPane mainGrid;

    @FXML
    private ToggleButton btnSoundMute;

    private int numCols;
    private int numRows;

    private MediaPlayer wrongSound;
    private MediaPlayer correctSound;
    private Stage mainStage;

    public void start(Stage mainStage, String imagePath) {
        this.mainStage = mainStage;

        try {
            String wrongSoundUri = GameController.class.getResource("sounds/wrong.mp3").toURI().toString();
            String correctSoundUri = GameController.class.getResource("sounds/correct.mp3").toURI().toString();
            wrongSound = new MediaPlayer(new Media(wrongSoundUri));
            correctSound = new MediaPlayer(new Media(correctSoundUri));
        } catch (Exception e) {
            System.out.println("Fail to load sound. Sounds disabled.");
            e.printStackTrace();
        }

        Image originalImage = new Image(imagePath);
        //Image originalImage = new Image("http://www.unoosa.org/res/timeline/index_html/space-2.jpg");
        mainImage.setImage(originalImage);

        mainGrid.setGridLinesVisible(true);

        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            mainGrid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            mainGrid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                addPane(i, j, numCols);
            }
        }

        PixelReader reader = originalImage.getPixelReader();
        List<PuzzlePiece> chunkList = new ArrayList<>();

        int width = (int) (originalImage.getWidth() / numCols);
        int height = (int) (originalImage.getHeight() / numRows);

        for (int j = 0; j <= numCols-1; j++) {
            for (int i = 0; i <= numRows-1; i++) {
                int x = i * width;
                int y = j * height;
                WritableImage chunk = new WritableImage(reader, x, y, width, height);
                chunkList.add(new PuzzlePiece(numCols * j + i, chunk));
            }
        }

        Collections.shuffle(chunkList);

        double y = 0;
        double x = 0;
        int correction = 0;

        int puzzlePieceWidth = 1200 / numCols;
        int puzzlePieceHeight = 720 / numRows;
        double puzzlePieceScale = 0.7;
        int puzzlePiecePadding = 5;

        for (int i = 0; i < chunkList.size(); i++) {
            PuzzlePiece puzzlePiece = chunkList.get(i);
            puzzlePiece.setSize(puzzlePieceWidth, puzzlePieceHeight);
            DraggableImageView imageView = new DraggableImageView(puzzlePiece);
            imageView.setCache(true);
            imageView.setFitWidth(puzzlePieceWidth * puzzlePieceScale);
            imageView.setFitHeight(puzzlePieceHeight * puzzlePieceScale);

            int rowScale = (numCols == 3 || numCols == 4) ? 2 : 3;
            if (i % rowScale == 0 && i != 0) {
                y += Math.floor(puzzlePieceHeight * puzzlePieceScale) + puzzlePiecePadding;
                x = 0;
                correction += rowScale;
            }

            Group group = new Group(imageView);
            x = (Math.floor(puzzlePieceWidth * puzzlePieceScale)+ puzzlePiecePadding) * (i - correction);
            group.relocate(x, y);

            mainPane.getChildren().add(group);
        }

    }

    private void addPane(int colIndex, int rowIndex, int numCols) {
        Pane pane = new Pane();
        pane.setMaxSize(1200/numCols, 720/numRows);
        pane.setMinSize(1200/numCols, 720/numRows);
        pane.setOnMouseEntered(e -> {
            PuzzlePiece piece = ChunkInfo.activePiece;
            DraggableImageView activeImage = ChunkInfo.activeImageView;
            if (piece != null) {
//                System.out.printf("Mouse enetered cell [%d, %d], %d %n", colIndex, rowIndex, piece.getPosition());
                if(piece.getPosition() == (rowIndex * numCols + colIndex)) {
                    pane.getChildren().add(piece.createImageView());
                    ((Group) activeImage.getParent()).getChildren().remove(activeImage);
                    activeImage.setMouseTransparent(true);
                    if( correctSound != null && !btnSoundMute.isSelected() ) {
                        correctSound.seek(correctSound.getStartTime());
                        correctSound.play();
                    }
                }else {
                    if( wrongSound != null && !btnSoundMute.isSelected() ) {
                        wrongSound.seek(wrongSound.getStartTime());
                        wrongSound.play();
                    }
                }
            }
        });
        mainGrid.add(pane, colIndex, rowIndex);
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void onBtnReturnHome(ActionEvent actionEvent) throws IOException {

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
        Parent root = myLoader.load();
        mainStage.setScene(new Scene(root, 1920, 1080));
        mainStage.show();
        HomeController controller = (HomeController) myLoader.getController();
        controller.start(mainStage);
    }
}
