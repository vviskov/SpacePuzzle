package hr.tvz.spacepuzzle;

import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

/**
 * Created by valentina on 2.6.2016..
 */
public class PuzzlePiece {
    private Integer position;
    private WritableImage image;
    private int width;
    private int height;

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public ImageView createImageView(){
        ImageView view = new ImageView(getImage());
        view.setFitWidth(getWidth());
        view.setFitHeight(getHeight());
        return view;
    }
}
