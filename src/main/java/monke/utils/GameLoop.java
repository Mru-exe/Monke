package monke.utils;

import java.util.logging.Logger;

/**
 * This class is a bundle for instantiating any process running on a separate thread.
 */
public abstract class GameLoop implements Runnable {
    private static final Logger logger = Logger.getLogger(GameLoop.class.getName());

    protected final int TARGET_FPS = 60;
    protected final double TARGET_TIME = 1_000_000_000.0 / TARGET_FPS;

    private double lastTime = System.nanoTime();

    private volatile boolean isRunning = false;

    // Pause control
    private final Object pauseLock = new Object();
    private volatile boolean paused = false;

    public boolean isPaused() {
        return paused;
    }

    /**
     * Starts the game loop in a separate thread
     */
    public void start() {
        logger.finer("STARTING " + this);
        if (!isRunning) {
            isRunning = true;
            Thread currentThread = new Thread(this);
            currentThread.start();
        }
    }

    /**
     * Pauses the game loop in a thread-safe manner
     */
    public void pause() {
        logger.info("PAUSING");
        paused = true;
    }

    /**
     * Unpauses the game loop and notifies the running thread
     */
    public void unpause() {
        logger.info("UNPAUSING");
        synchronized (pauseLock) {
            paused = false;
            lastTime = System.nanoTime();
            pauseLock.notifyAll();
        }
    }

    /**
     * Stops the game loop gracefully and waits for the thread to finish
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Main loop that runs the game logic
     */
    @Override
    public void run() {
        while (isRunning) {
            try {
                // Handle pause
                synchronized (pauseLock) {
                    while (paused) {
                        pauseLock.wait();
                    }
                }
                long now = System.nanoTime();
                if(now - lastTime >= TARGET_TIME){
                    double dt = TARGET_TIME / 1_000_000_00.0; //KEEP THIS LINE
                    process(dt);
                    lastTime += TARGET_TIME;
                } else {
                    Thread.sleep(1); // Prevent cpu spinning
                }
            } catch (InterruptedException e) {
                isRunning = false;
                Thread.currentThread().interrupt();
                logger.severe("Game loop interrupted: " + e.getMessage());
            }
        }
    }

    public boolean isRunning(){
        return this.isRunning;
    }

    /**
     * Abstract method containing the game logic to execute each frame
     */
    protected abstract void process(double dt);
}