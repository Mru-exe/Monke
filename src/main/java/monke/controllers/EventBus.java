package monke.controllers;

import monke.MonkeyGame;
import monke.enums.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
TODO:
- Complete javadoc
 */

/**
 * Singleton class to manage events in the game.
 */
public class EventBus {
    private static final Logger logger = Logger.getLogger(EventBus.class.getName());

     //HashMap to store event listeners - concurrent makes it thread-safe.
    private static final ConcurrentHashMap<GameEvent, List<Consumer<Object>>> eventListeners = new ConcurrentHashMap<>();

    //Static class initialization
    static {
        logger.setParent(logger.getLogger(MonkeyGame.class.getName()));
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(true);
    }
    private EventBus() {}


    /**
     * Subscribes a listener to a specific event.
     * @param event
     * @param listener
     */
    public static void subscribe(GameEvent event, Runnable listener) {
        logger.fine(listener + " Subscribing to event: " + event);
        eventListeners.computeIfAbsent(event, e -> new ArrayList<>()).add(args -> listener.run());
    }

    /**
     * @hidden - There is no need to pass args to the listener yet.
     */
    public static void subscribe(GameEvent event, Consumer<Object> listener) {
        logger.fine(listener + " Subscribing to event: " + event);
        eventListeners.computeIfAbsent(event, e -> new ArrayList<>()).add(listener);
    }

    public static void unsubscribe(GameEvent event, Consumer<Object> listener) {
        List<Consumer<Object>> listeners = eventListeners.get(event);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    public static synchronized void publish(GameEvent event, Object args) {
        logger.fine("Publishing event: " + event);
        List<Consumer<Object>> listeners = eventListeners.get(event);
        if (listeners != null) {
            for (Consumer<Object> listener : listeners) {
                listener.accept(args);
            }
        }
    }
    public static synchronized void publish(GameEvent event) {
        logger.fine("Publishing event: " + event);
        List<Consumer<Object>> listeners = eventListeners.get(event);
        if (listeners != null) {
            for (Consumer<Object> listener : listeners) {
                listener.accept(null);
            }
        }
    }
}
