package monke.utils;

import java.util.logging.Logger;

/**
 * This class is a bundle for instantiating any process running on a separate thread.
 */
public abstract class GameLoop implements Runnable {
    private static final Logger logger = Logger.getLogger(GameLoop.class.getName());

    private final int TARGET_FPS = 60;
    private final double TARGET_TIME = 1_000_000_000.0 / TARGET_FPS;

    private double lastTime = System.nanoTime();

    private Thread currentThread;
    private volatile boolean isRunning = false;

    /**
     * Starts the game loop in a separate thread
     */
    public void start() {
        if (!isRunning) {
            isRunning = true;
            currentThread = new Thread(this);
            currentThread.start();
        }
    }

    /**
     * Stops the game loop gracefully and waits for the thread to finish
     */
    public void stop() {
        isRunning = false;
        if (currentThread != null) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Main loop that runs the game logic
     */
    @Override
    public void run() {
        while (isRunning) {
            try {
                long now = System.nanoTime();
                if(now - lastTime >= TARGET_TIME){
                    double dt = TARGET_TIME / 1_000_000_00.0; //KEEP THIS LINE
                    process(dt);
                    lastTime += TARGET_TIME;
                } else {
                    currentThread.sleep(1); //
                }
            } catch (InterruptedException e) {
                isRunning = false;
                Thread.currentThread().interrupt();
                logger.severe("Game loop interrupted: " + e.getMessage());
            }
        }
    }

    /**
     * Abstract method containing the game logic to execute each frame
     */
    protected abstract void process(double dt);
}