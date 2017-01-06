package hr.tvz.spacepuzzle;

import javafx.scene.image.Image;

/**
 * Created by vviskov on 06.01.2017..
 */
public class ImageWithPath extends Image {
    private final String url;

    public ImageWithPath(String url) {
        super(url);
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
