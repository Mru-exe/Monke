package monke.utils;

import monke.enums.GameEvent;
import monke.enums.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton class to manage events in the game.
 * @implNote Only support parameterless events for now.
 */
public class EventBus {
    private static final Logger logger = Logger.getLogger(EventBus.class.getName());

     //HashMap to store event listeners - concurrent makes it thread-safe.
    private static final ConcurrentHashMap<GameEvent, List<Runnable>> eventListeners = new ConcurrentHashMap<>();

    private EventBus() {}


    /**
     * Subscribes a listener to a specific event.
     * @param event - The event to subscribe to.
     * @param listener - The function to be called when the event is published.
     */
    public static void subscribe(GameEvent event, Runnable listener) {
        logger.finest(listener + " Subscribing to event: " + event);
        eventListeners.computeIfAbsent(event, e -> new ArrayList<>()).add(listener);
    }

    public static void unsubscribe(GameEvent event, Runnable listener) {
        List<Runnable> listeners = eventListeners.get(event);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    /**
     * Publishes an event to all subscribed listeners.
     * @param event - The event to publish.
     */
    public static synchronized void publish(GameEvent event) {
        logger.finer("Publishing event: " + event);
        List<Runnable> listeners = eventListeners.get(event);
        if (listeners != null) {
            for (Runnable listener : listeners) {
                listener.run();
            }
        }
    }
}
