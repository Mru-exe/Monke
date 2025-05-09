package monke.views;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import monke.controllers.GameController;
import monke.models.base.GameObject;
import monke.utils.SpriteFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class GameView extends BaseView {
    private static final Logger logger = Logger.getLogger(GameView.class.getName());

    private final Set<KeyCode> activeKeys = new HashSet<>(); //to handle key hold
    private final GameController gameController;

    private AnimationTimer renderingThread;

    public GameView(GameController controller) {
        super();
        logger.finer("GameView initialized");

        this.gameController = controller;
        this.getRoot().setStyle("-fx-background-color: #3f3f3f;");

        addHandlers();
    }

    /**
     * Pushes the sprites to the base pane and starts the rendering thread.
     * @param objects Collection of game objects to be rendered.
     *
     * Repeated calls to this method will re-initialize the rendering thread.
     */
    public void pushSprites(Collection<GameObject> objects){
        logger.info("Loading "+ objects.size() + " sprites");
        for (GameObject go : objects) {
            if(go.getFxSprite() != null) {
                Group sprite = go.getFxSprite();
                this.getBasePane().getChildren().add(sprite);
            }
        }
        renderingThread = new AnimationTimer() {
            @Override
            public void handle(long l) {
                for (GameObject go : objects) {
                    Group fxSprite = go.getFxSprite();
                    if(fxSprite == null) {
                        continue;
                    }
                    fxSprite.setTranslateX(go.getX());
                    fxSprite.setTranslateY(go.getY());
                }
            }
        };
    }

    public void startRenderingThread(){
        renderingThread.start();
    }
    public void pauseRenderingThread(){
        renderingThread.stop();
    }

    private void addHandlers() {
        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            gameController.handleInput(String.valueOf(event.getCode()), false);
        });
        this.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            gameController.handleInput(String.valueOf(event.getCode()), true);
        });
    }
}
