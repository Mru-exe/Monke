package monke.controllers;

import monke.enums.EventType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton class for managing event distribution.
 */
public class EventManager {
    private ConcurrentHashMap<EventType, List<Method>> subscribers;

    private static EventManager instance;

    /**
     * @return Singleton instance of EventManager
     */
    public static synchronized EventManager getInstance() {
        if (instance == null) instance = new EventManager();
        return instance;
    }

    public EventManager() {
        subscribers = new ConcurrentHashMap<>();
    }

    public void subscribe(EventType eventType, Method method) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(method);
    }

    public void unsubscribe(EventType eventType, Method method) {
        List<Method> methods = subscribers.get(eventType);
        if (methods != null) {
            methods.remove(method);
        }
    }

    public void publish(EventType eventType, Object... args) {
        List<Method> methods = subscribers.get(eventType);
        if (methods != null) {
            for (Method method : methods) {
                try {
                    method.invoke(null, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
