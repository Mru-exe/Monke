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

    public GameView(GameController controller) {
        super();
        logger.finer("GameView initialized");

        this.gameController = controller;
        this.getRoot().setStyle("-fx-background-color: #3f3f3f;");

        addHandlers();
    }

    public void addGameObjects(Collection<GameObject> objects){
        logger.info("Loading "+ objects.size() + " sprites");
        for (GameObject go : objects) {
            Group sprite = go.getFxSprite();
            this.getBasePane().getChildren().add(sprite);
        }
    }

    public void startRenderingLoop(Collection<GameObject> sprites){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (GameObject go : sprites) {
                    Group fxSprite = go.getFxSprite();
                    fxSprite.setTranslateX(go.getX());
                    fxSprite.setTranslateY(go.getY());
                }
            }
        };
        logger.fine("Starting rendering loop");
        timer.start();
    }


    private void addHandlers() {
        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (!activeKeys.contains(event.getCode())) {
                activeKeys.add(event.getCode());
                gameController.handleInput(String.valueOf(event.getCode()), false);
            }
        });
        this.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            activeKeys.remove(event.getCode());
            gameController.handleInput(String.valueOf(event.getCode()), true);
        });
    }
}
