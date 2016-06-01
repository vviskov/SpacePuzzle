package hr.tvz.spacepuzzle;

import javafx.scene.image.WritableImage;

/**
 * Created by valentina on 2.6.2016..
 */
public class PuzzlePiece {
    private Integer position;
    private WritableImage image;

    public PuzzlePiece(int position, WritableImage image) {
        this.position = position;
        this.image = image;
    }

    public Integer getPosition() {
        return position;
    }

    public WritableImage getImage() {
        return image;
    }
}
