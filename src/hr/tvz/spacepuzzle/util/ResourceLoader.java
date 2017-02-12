package hr.tvz.spacepuzzle.util;

import hr.tvz.spacepuzzle.Main;
import javafx.fxml.FXMLLoader;

import java.net.URISyntaxException;

/**
 * Created by valentina on 12.2.2017..
 */
public final class ResourceLoader {

    private ResourceLoader(){}

    public static FXMLLoader loadView(final String view){
        return new FXMLLoader(Main.class.getResource("view/" + view + ".fxml"));
    }

    public static String loadSound(final String sound) throws URISyntaxException {
        return Main.class.getResource("sounds/"+sound+".mp3").toURI().toString();
    }

}
