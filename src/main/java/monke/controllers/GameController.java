package monke.controllers;

import javafx.application.Application;

import java.util.logging.Logger;

public class GameController implements Runnable {
    private static GameController instance;
    private static final Logger logger = Logger.getLogger(GameController.class.getName());

    private GameController() {
        //Initialize
    }

    /**
     * @return Singleton instance of GameController
     */
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    @Override
    public void run() {
        logger.info("Game logic thread started");
        try {
            //this.startGameLoop();
        } catch (Exception e){
            logger.warning("Failed to start game loop...");
            logger.finer(e.getMessage());
        }
        logger.info("Game logic thread stopped");
    }
}
