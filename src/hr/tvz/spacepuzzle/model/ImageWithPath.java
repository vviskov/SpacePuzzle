package hr.tvz.spacepuzzle.model;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    public static class Builder {
        private ImageView imageView;

        private Builder(String filePath){
            this.imageView = new ImageView(filePath);
        }

        public static Builder create(String filePath){
            return new Builder(filePath);
        }

        public Builder withSize(int width, int height) {
            this.imageView.setFitWidth(width);
            this.imageView.setFitHeight(height);
            return this;
        }

        public ImageView build() {
            return this.imageView;
        }
    }
}
