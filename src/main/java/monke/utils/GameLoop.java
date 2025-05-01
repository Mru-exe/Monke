package monke.utils;

import java.util.logging.Logger;

/**
 * This class is a bundle for instantiating any process running on a separate thread.
 * TODO: complete javadoc
 */
public abstract class GameLoop implements Runnable {
    private static final Logger logger = Logger.getLogger(GameLoop.class.getName());

    private final int TARGET_FPS = 60;
    private final double TARGET_TIME = 1_000_000_000.0 / TARGET_FPS;

    private double lastTime = System.nanoTime();

    private Thread currentThread;
    private volatile boolean isRunning = false;
    private volatile boolean isPaused = false;
    private final Object pauseLock = new Object();

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
        resumeGame(); // Wake up the thread if it's paused
        if (currentThread != null) {
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Toggles pause state of the game loop
     */
    public void pause() {
        isPaused = !isPaused;
        if (!isPaused) {
            synchronized (pauseLock) {
                pauseLock.notifyAll();
            }
        }
    }

    /**
     * Resumes the game if paused
     */
    private void resumeGame() {
        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }

    /**
     * Main loop that runs the game logic
     * @implNote May go back to using thread.sleep(16)
     */
    @Override
    public void run() {
        while (isRunning) {
            try {
                synchronized (pauseLock) {
                    while (isPaused) {
                        pauseLock.wait();
                    }
                }

                long now = System.nanoTime();
                if(now - lastTime >= TARGET_TIME){
                    process();
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
    protected abstract void process();

    /**
     * Optional method to handle rendering (can be split into separate method)
     */
    protected void render() {
        // Default empty implementation
    }
}