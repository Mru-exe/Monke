package monke.controllers;


import javafx.scene.Scene;
import monke.enums.Command;
import monke.enums.GameEvent;
import monke.models.GameLevel;
import monke.models.base.GameObject;
import monke.models.common.Updatable;
import monke.models.entities.Player;
import monke.utils.EventBus;
import monke.utils.GameLoop;
import monke.utils.InputHandler;
import monke.utils.SpriteFactory;
import monke.views.GameView;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class GameController {
    private static final Logger logger = Logger.getLogger(GameController.class.getName());
    private final SpriteFactory spriteFactory = new SpriteFactory(true);

    private final Set<GameObject> activeSprites = new HashSet<>();

    private final GameLoop logicThread = new GameLoop(){
        @Override
        protected void process() {
            for (GameObject go : activeSprites) {
                if (go instanceof Updatable) {
                    ((Updatable) go).update();
                }
            }
        }
    };

    private final GameLevel level;
    private final GameView view;
    private final Player player;

    public GameController(GameLevel level) {
        this.view = new GameView(this);
//        this.level = level;
        this.level = new GameLevel("TestLevel", 0, 0);

        Player player = new Player(0,100,10,10);
        spriteFactory.applySpriteToModel(player);

        Platform ground = new Platform(0, 100, 190, 10);
        spriteFactory.applySpriteToModel(ground);

        this.player = player;
        this.activeSprites.add(player);
        this.pushSpritesToView(activeSprites);

        logger.finer("GameController initialized");
        view.startRenderingLoop(activeSprites);
        this.logicThread.start();
    }

    private void pushSpritesToView(Collection<GameObject> sprites){
        logger.finer("Pushing sprites to view");
        this.view.addGameObjects(sprites);
    }

    public void handleInput(String key, boolean release){
        Player player = this.player;

        Command cmd = InputHandler.parse(key, release);

        switch (cmd){
            case Command.PLAYER_LEFT -> player.setVelX(-5);
            case Command.PLAYER_RIGHT -> player.setVelX(5);
            case Command.PLAYER_JUMP -> player.setVelY(-5);
            case Command.PLAYER_STOP -> player.setVelX(0);
            case Command.GAME_QUIT -> {
                logger.finer("Game quit");
                logicThread.stop();
                EventBus.publish(GameEvent.OPEN_MAIN_MENU);
            }
        }
    }

    public Scene getView() {
        return this.view;
    }

//    public void pauseGame(){
//        logger.finer("Game paused");
//        EventBus.publish(GameEvent.PAUSE_GAME);
//    }
//
//    public void resumeGame(){
//        logger.finer("Game resumed");
//        EventBus.publish(GameEvent.RESUME_GAME);
//    }
}
