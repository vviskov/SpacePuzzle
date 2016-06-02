package hr.tvz.spacepuzzle;


import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

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

    public void start() {

        Image originalImage = new Image("hr/tvz/spacepuzzle/image1.png");
        //Image originalImage = new Image("http://www.unoosa.org/res/timeline/index_html/space-2.jpg");
        mainImage.setImage(originalImage);

        mainGrid.setGridLinesVisible(true);
        int numCols = 5;
        int numRows = 5;

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

        int width = (int) (originalImage.getWidth() / 5);
        int height = (int) (originalImage.getHeight() / 5);

        for (int j = 0; j <= 4; j++) {
            for (int i = 0; i <= 4; i++) {
                int x = i * width;
                int y = j * height;
                WritableImage chunk = new WritableImage(reader, x, y, width, height);
                chunkList.add(new PuzzlePiece(5 * j + i, chunk));
            }
        }

        Collections.shuffle(chunkList);

        double y = 0;
        double x = 0;
        int correction = 0;
        for (int i = 0; i < chunkList.size(); i++) {
            PuzzlePiece puzzlePiece = chunkList.get(i);
            puzzlePiece.setSize(240, 135);
            DraggableImageView imageView = new DraggableImageView(puzzlePiece);
            imageView.setCache(true);
            imageView.setFitWidth(180);
            imageView.setFitHeight(101);

            if (i % 3 == 0 && i != 0) {
                y += 110;
                x = 0;
                correction += 3;
            }

            Label label = new Label(puzzlePiece.getPosition().toString());
            label.setTextFill(Paint.valueOf("blue"));
            Group group = new Group(imageView, label);
            x = 190 * (i - correction);
            group.relocate(x, y);

            mainPane.getChildren().add(group);
        }

    }

    private void addPane(int colIndex, int rowIndex, int numCols) {
        Pane pane = new Pane();
        pane.setMaxSize(240, 135);
        pane.setMinSize(240, 135);
        pane.setOnMouseEntered(e -> {
            PuzzlePiece piece = ChunkInfo.activePiece;
            DraggableImageView activeImage = ChunkInfo.activeImageView;
            if (piece != null) {
                System.out.printf("Mouse enetered cell [%d, %d], %d %n", colIndex, rowIndex, piece.getPosition());
                if(piece.getPosition() == (rowIndex * numCols + colIndex)) {
                    System.out.println("jeeej");
                    pane.getChildren().add(piece.createImageView());
                    ((Group) activeImage.getParent()).getChildren().remove(activeImage);
                    activeImage.setMouseTransparent(true);
                }
            }
        });
        mainGrid.add(pane, colIndex, rowIndex);
    }

}
