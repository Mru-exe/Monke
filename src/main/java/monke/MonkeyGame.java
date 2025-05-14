package monke;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import monke.controllers.EndgameController;
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
 * Entry point class that runs the MonkeyGame application. It manages the primary game stage, scene transitions, and shutdown.
 * <p>
 * This class makes use of an {@link EventBus} to run its main methods based on events.
 */
public class MonkeyGame extends Application {
    private static final Logger logger = Logger.getLogger(MonkeyGame.class.getName());

    private Stage primaryStage;
    /**
     * Constructor for the MonkeyGame class. Initializes the logger and subscribes to game events.
     */
    public MonkeyGame(){
        EventBus.subscribe(GameEvent.EXIT_GAME, this::stop);
        EventBus.subscribe(GameEvent.OPEN_MAIN_MENU, this::openMenu);
        EventBus.subscribe(GameEvent.START_GAME, this::startGame);
        EventBus.subscribe(GameEvent.DIE, () -> this.endGame(EndgameController.EndgameType.LOSE));
        EventBus.subscribe(GameEvent.WIN, () -> this.endGame(EndgameController.EndgameType.WIN));
        logger.info("EventBus connected");
    }

    private static void initLogger(){
        try {
            InputStream stream = MonkeyGame.class.getClassLoader().getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            logger.warning("Could not load logging.properties file");
        }
        logger.info("Logger initialized");
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        for(String a : args) System.out.println(a);
        if(args.length > 0 && args[0].equals("debug")){
            initLogger();
        } else {
            Logger.getLogger("").setLevel(Level.OFF);
        }
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

    /**
     * Opens the main menu scene.
     */
    private void openMenu(){
        MainMenuController controller = new MainMenuController();
        this.primaryStage.setScene(controller.getView());
    }

    /**
     * Starts the game by loading a level and setting the game controller.
     */
    private void startGame(){
        GameLevel level = LevelLoader.loadLevel("default-level.json");
        if(level == null){
            logger.severe("Failed to load level");
            return;
        }
        GameController controller = new GameController(level);
        Platform.runLater(() -> this.primaryStage.setScene(controller.getView()));
    }

    /**
     * Ends the game and displays the endgame screen.
     * @param type the type of endgame (WIN or LOSE)
     */
    private void endGame(EndgameController.EndgameType type){
        EndgameController controller = new EndgameController(type);
        Platform.runLater(() -> this.primaryStage.setScene(controller.getView()));
    }
}
