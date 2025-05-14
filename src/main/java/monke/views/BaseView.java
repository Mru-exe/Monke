package monke.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import monke.utils.EventBus;
import monke.enums.GameEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

public abstract class BaseView extends Scene {
    private static final Logger logger = Logger.getLogger(BaseView.class.getName());

    public BaseView(){
        super(new BorderPane(), 1520, 855);
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
            logger.severe(e.getMessage());
        }
        return new StackPane();
    }

    protected void loadFont(String fileName){
        logger.finer("Loading font: " + fileName);
        try (InputStream fontStream = getClass().getResourceAsStream("/" + fileName)) {
            if (fontStream == null) {
                logger.warning("Font file not found: " + fileName);
                throw new IOException("Font file not found: " + fileName);
            }
            Font.loadFont(fontStream, 12);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    protected InputStream getResourceStream(String s){
        try {
            return getClass().getResourceAsStream(s);
        } catch (Exception e){
            logger.warning("Resource not found: " + s);
            logger.severe(e.getMessage());
            EventBus.publish(GameEvent.OPEN_MAIN_MENU);
        }
        return null;
    }
}
