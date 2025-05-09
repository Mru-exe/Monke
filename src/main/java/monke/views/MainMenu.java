package monke.views;

import javafx.scene.Parent;
import javafx.scene.text.Font;
import monke.controllers.MainMenuController;

import java.io.IOException;
import java.io.InputStream;

import java.util.logging.Logger;

public class MainMenu extends BaseView {
    private static final Logger logger = Logger.getLogger(MainMenu.class.getName()+"View");
    private final MainMenuController controller;

    public MainMenu(MainMenuController controller) {
        super();
        this.controller = controller;

        this.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());

        Parent root = super.loadFXML("/views/MainMenu.fxml", controller);
        this.loadFont("PressStart2P.ttf");

        this.setRoot(root);
    }

    private void loadFont(String fileName){
        logger.finer("Loading font: " + fileName);
        try (InputStream fontStream = getClass().getResourceAsStream("/" + fileName)) {
            if (fontStream == null) {
                logger.warning("Font file not found: " + fileName);
                throw new IOException("Font file not found: " + fileName);
            }
            Font.loadFont(fontStream, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}