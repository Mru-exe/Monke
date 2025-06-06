package monke.views;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import monke.controllers.GameController;
import monke.models.base.GameObject;

import java.util.*;
import java.util.logging.Logger;

/**
 * GameView class is responsible for rendering the main game screen.
 */
public class GameView extends BaseView {
    private static final Logger logger = Logger.getLogger(GameView.class.getName());

    private final GameController gameController;

    private final Set<Group> allSprites = new HashSet<>();
    private final Queue<GameObject> destroyQueue = new LinkedList<>();

    private AnimationTimer renderingThread;

    /**
     * Constructor for GameView. Automatically loads CSS and FXML files.
     * @param controller the controller for this view.
     */
    public GameView(GameController controller) {
        super();
        logger.finer("GameView initialized");

        this.gameController = controller;
        this.getRoot().setStyle("-fx-background-image: url('/background.png');");
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
                        logger.finer("New sprite: " + go);
                        allSprites.add(go.getFxSprite());
                        getBasePane().getChildren().add(go.getFxSprite());
                    }
                    Group fxSprite = go.getFxSprite();
                    if(fxSprite == null) {
                        logger.warning("Sprite is null: " + go);
                        continue;
                    }
                    fxSprite.setTranslateX(go.getX());
                    fxSprite.setTranslateY(go.getY());
                    checkForCleanup(go);
                }
            }
        };
    }

    /**
     * Checks if the sprite is outside the screen bounds and removes it if so.
     * Additionally, removes all sprites in the destroy queue.
     * @param go GameObject to check.
     */
    private void checkForCleanup(GameObject go){
        Node sprite = go.getFxSprite();
        double absX = Math.abs(sprite.getTranslateX());
        double absY = Math.abs(sprite.getTranslateY());

        if(absX > 1.5*this.getWidth() || absY > 1.5*this.getHeight() || absX < 0 || absY < 0){
            this.getBasePane().getChildren().remove(sprite);
            gameController.getLevel().destroyObject(go);
            logger.fine("Killed sprite: " + go);
        }
        //remove all sprites in destroy queue
        while(!destroyQueue.isEmpty()){
            GameObject goToDestroy = destroyQueue.poll();
            if(goToDestroy.getFxSprite() != null) {
                allSprites.remove(goToDestroy.getFxSprite());
                this.getBasePane().getChildren().remove(goToDestroy.getFxSprite());
                logger.fine("Removed sprite: " + goToDestroy);
            }
        }
    }

    /**
     * Adds a sprite to the destroy queue.
     * @param go GameObject to be removed.
     */
    public void removeSprite(GameObject go){
        this.destroyQueue.add(go);
    }

    public void startRenderingThread(){
        renderingThread.start();
    }
    public void stopRenderingThread(){
        renderingThread.stop();
    }
}
