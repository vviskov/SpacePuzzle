package hr.tvz.spacepuzzle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by valentina on 1.6.2016..
 */
public class DraggableImageView  extends ImageView {
    private double mouseX ;
    private double mouseY ;
    public DraggableImageView(Image image) {
        super(image);

        setOnMousePressed(event -> {
            mouseX = event.getSceneX() ;
            mouseY = event.getSceneY() ;
        });

        setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseX ;
            double deltaY = event.getSceneY() - mouseY ;
            relocate(getLayoutX() + deltaX, getLayoutY() + deltaY);
            mouseX = event.getSceneX() ;
            mouseY = event.getSceneY() ;
        });
    }
}
