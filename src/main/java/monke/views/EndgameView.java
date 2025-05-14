package monke.views;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import monke.controllers.EndgameController;

import java.util.logging.Logger;

/**
 * This class is responsible for rendering the endgame screen.
 */
public class EndgameView extends BaseView {
    private static final Logger logger = Logger.getLogger(EndgameView.class.getName());

    /**
     * Constructor for EndgameView. Automatically loads CSS and FXML files.
     * @param controller the controller for this view.
     */
    public EndgameView(EndgameController controller) {
        super();

        this.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());

        Parent root = super.loadFXML("/views/Endgame.fxml", controller);
        super.loadFont("PressStart2P.ttf");

        this.setRoot(root);
    }

    /**
     * Sets the message on the main label.
     * @param text
     */
    public void setLabelMessage(String text) {
        Label label = (Label) this.getRoot().lookup("#messageLabe");
        if (label != null) {
            label.setText(text);
        } else {
            logger.warning("Label with fx:id 'labelId' not found in the FXML.");
        }
    }

    /**
     * Hides a node by its fx:id.
     * @param id
     */
    public void hideNodeById(String id){
        Node node = this.getRoot().lookup("#" + id);
        if (node == null) {
            logger.warning("Node with fx:id '" + id + "' not found in the FXML.");
            return;
        }
        node.setVisible(false);
    }

    /**
     * Shows a node by its fx:id.
     * @param id
     */
    public void showNodeById(String id){
        Node node = this.getRoot().lookup("#" + id);
        if (node == null) {
            logger.warning("Node with fx:id '" + id + "' not found in the FXML.");
            return;
        }
        node.setVisible(true);
    }
}