package monke.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import monke.MonkeyGame;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class MainMenu extends Scene {
    private static final Logger logger = Logger.getLogger(MainMenu.class.getName());


    public MainMenu() {
        super(new Group(), 800, 600);
        Parent root = this.getFXML("views/MainMenu.fxml");
        super.setRoot(root);
        logger.finest("Main Menu scene initialized");
    }

    private Parent getFXML(String fileName){
        logger.finer("Loading FXML file: " + fileName);
        URL fxmlURL = getClass().getResource("/" + fileName);
        try{
            if(fxmlURL == null) {
                logger.warning("FXML file not found: " + fileName);
                throw new IOException("FXML file not found: " + fileName);
            }
            return FXMLLoader.load(fxmlURL);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new StackPane();
    }
}