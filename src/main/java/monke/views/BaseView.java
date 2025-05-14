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

/**
 * Base class for all views in the application.
 */
public abstract class BaseView extends Scene {
    private static final Logger logger = Logger.getLogger(BaseView.class.getName());

    public BaseView(){
        super(new BorderPane(), 1520, 855);
    }

    /**
     * @return the root element cast as a Pane.
     */
    protected Pane getBasePane() {
        return ((Pane) this.getRoot());
    }

    /**
     * Loads an FXML file and sets the controller.
     * @param fileName the name of the FXML file to load.
     * @param controller the controller to set for the FXML file.
     * @return the loaded Parent object.
     */
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

    /**
     * Loads a font from the 'resources' folder.
     * @param fileName the name of the font file to load.
     */
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

    /**
     * Loads a resource from the 'resources' folder.
     * @param s the path to the resource to load.
     * @return the InputStream of the resource.
     */
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
