package monke.views;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import monke.controllers.GameController;
import monke.models.base.GameObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class GameView extends BaseView {
    private static final Logger logger = Logger.getLogger(GameView.class.getName());

    private final GameController gameController;

    private final Set<Group> allSprites = new HashSet<>();

    private AnimationTimer renderingThread;

    public GameView(GameController controller) {
        super();
        logger.finer("GameView initialized");

        this.gameController = controller;
        this.getRoot().setStyle("-fx-background-color: #3f3f3f;");
    }

    /**
     * Pushes the sprites to the base pane and starts the rendering thread.
     * @param objects Collection of game objects to be rendered.
     *
     * Repeated calls to this method will re-initialize the rendering thread.
     */
    public void initSprites(Collection<GameObject> objects){
        logger.info("Loading "+ objects.size() + " sprites");
        for (GameObject go : objects) {
            if(go.getFxSprite() != null) {
                Group sprite = go.getFxSprite();
                allSprites.add(sprite);
            }
        }
        this.getBasePane().getChildren().clear();
        this.getBasePane().getChildren().addAll(allSprites);
        renderingThread = new AnimationTimer() {
            @Override
            public void handle(long l) {
                for (GameObject go : gameController.getLevel().getGameObjects()) {
                    if(!allSprites.contains(go.getFxSprite())) {
                        logger.info("New sprite: " + go.toString());
                        allSprites.add(go.getFxSprite());
                        getBasePane().getChildren().add(go.getFxSprite());
                    }
                    Group fxSprite = go.getFxSprite();
                    if(fxSprite == null) {
                        logger.warning("Sprite is null: " + go.toString());
                        continue;
                    }
                    fxSprite.setTranslateX(go.getX());
                    fxSprite.setTranslateY(go.getY());
                    checkForCleanup(go);
                }
            }
        };
    }

    private void checkForCleanup(GameObject go){
        Node sprite = go.getFxSprite();
        double absX = Math.abs(sprite.getTranslateX());
        double absY = Math.abs(sprite.getTranslateY());

        if(absX > 1.5*this.getWidth() || absY > 1.5*this.getHeight() || absX < 0 || absY < 0){
            this.getBasePane().getChildren().remove(sprite);
            gameController.getLevel().destroyObject(go);
            logger.info("Killed sprite: " + go.toString());
        }
    }

    public void startRenderingThread(){
        renderingThread.start();
    }
    public void pauseRenderingThread(){
        renderingThread.stop();
    }
}
