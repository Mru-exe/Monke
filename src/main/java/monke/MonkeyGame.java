package monke;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import monke.controllers.MainMenuController;
import monke.utils.EventBus;
import monke.controllers.GameController;
import monke.utils.LevelLoader;
import monke.enums.GameEvent;
import monke.models.GameLevel;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;

/**
 * Main class for the Monkey Game.
 */
public class MonkeyGame extends Application {
    private static final Logger logger = Logger.getLogger(MonkeyGame.class.getName());

    private Stage primaryStage;
    private static String selectedLevelFile;

    public MonkeyGame(){
        try {
            InputStream stream = MonkeyGame.class.getClassLoader().getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            logger.warning("Could not load logging.properties file");
        }
        logger.info("Logger initialized");

        EventBus.subscribe(GameEvent.EXIT_GAME, this::stop);
        EventBus.subscribe(GameEvent.OPEN_MAIN_MENU, this::openMenu);
        EventBus.subscribe(GameEvent.START_GAME, this::startGame);
    }

    public static void main(String[] args) {
        logger.info("Starting the Game...");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Monkey Game");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        this.openMenu();
        primaryStage.show();
    }

    @Override
    public void stop(){
        Platform.runLater(() -> {
            logger.info("Stopping the Game...");
            Platform.exit();
            System.exit(0);
        });
    }

    private String getSelectedLevelFile() {
        return "Default";
    }

    private void openMenu(){
        MainMenuController controller = new MainMenuController();
        this.primaryStage.setScene(controller.getView());
    }

    private void startGame(){
        GameLevel level = LevelLoader.loadLevel("default-level.json");
        GameController controller = new GameController(level);
        this.primaryStage.setScene(controller.getView());
    }
}
