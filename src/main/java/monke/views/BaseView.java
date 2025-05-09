package monke.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import monke.utils.EventBus;
import monke.enums.GameEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

public abstract class BaseView extends Scene {
    private static final Logger logger = Logger.getLogger(GameView.class.getName());

    private FXMLLoader loader = new FXMLLoader(getClass().getResource("MyView.fxml"));

    private Pane basePane = new Pane();

    public BaseView(){
        super(new BorderPane(), 960, 720);
    }

    protected Pane getBasePane() {
        return ((Pane) this.getRoot());
    }

    protected Parent loadFXML(String fileName, Object controller) {
        logger.finer("Loading FXML file: " + fileName);
        URL fxmlURL = getClass().getResource(fileName);
        try{
            if(fxmlURL == null) {
                logger.warning("FXML file not found: " + fileName);
                throw new IOException("FXML file not found: " + fileName);
            }
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            fxmlLoader.setController(controller);
            return fxmlLoader.load();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new StackPane();
    }

    public InputStream getResourceStream(String s){
        try {
            InputStream o = getClass().getResourceAsStream(s);
            return o;
        } catch (Exception e){
            logger.warning("Resource not found: " + s);
            e.printStackTrace();
            EventBus.publish(GameEvent.OPEN_MAIN_MENU);
        }
        return null;
    }
}
