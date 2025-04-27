package monke.views;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.logging.Logger;


/**
 * GameWindow is responsible for creating and managing the game window.
 * It initializes the window and handles rendering.
 */
public class GameWindow extends Application implements Runnable {
    private static GameWindow instance;
    private static final Logger logger = Logger.getLogger(GameWindow.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Monke Foolery");
        primaryStage.show();
    }

    @Override
    public void run() {
        logger.info("Rendering thread started");
        try {
            Application.launch();
        } catch (Exception e){
            logger.warning("JavaFX Application failed to launch");
            logger.severe(e.getMessage());
        }
    }

    public static GameWindow getInstance(){
        if(instance == null){
            instance = new GameWindow();
        }
        return instance;
    }

    public GameWindow() {}
}
