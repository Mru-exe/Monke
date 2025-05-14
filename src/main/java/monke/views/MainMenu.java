package monke.views;

import javafx.scene.Parent;
import monke.controllers.MainMenuController;

import java.util.logging.Logger;

/**
 * This class is responsible for rendering the main menu screen.
 */
public class MainMenu extends BaseView {
    private static final Logger logger = Logger.getLogger(MainMenu.class.getName()+"View");

    /**
     * Constructor for MainMenu. Automatically loads CSS and FXML files.
     * @param controller the controller for this view.
     */
    public MainMenu(MainMenuController controller) {
        super();

        this.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());

        Parent root = super.loadFXML("/views/MainMenu.fxml", controller);
        this.loadFont("PressStart2P.ttf");

        this.setRoot(root);
    }
}