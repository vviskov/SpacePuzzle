
package hr.tvz.spacepuzzle;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * Created by valentina on 1.6.2016..
 */
public class DraggableImageView extends ImageView {
    private double mouseX;
    private double mouseY;
    private PuzzlePiece piece;

    public DraggableImageView(PuzzlePiece piece) {
        super(piece.getImage());
        this.piece = piece;

        setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
            System.out.println("transparent true");
            this.setMouseTransparent(true);
            ChunkInfo.activePiece = piece;
            ChunkInfo.activeImageView = this;
        });

        setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;
            relocate(getLayoutX() + deltaX, getLayoutY() + deltaY);
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        setOnMouseReleased(event -> {
            Platform.runLater(() -> {
                System.out.println("transparent false");
                ChunkInfo.activePiece = null;
                ChunkInfo.activeImageView = null;
                this.setMouseTransparent(false);
            });
        });
    }
}
