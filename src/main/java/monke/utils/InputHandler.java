package monke.utils;

import monke.enums.Command;
import monke.enums.GameEvent;

import java.util.*;
import java.util.logging.Logger;

public class InputHandler {
    private static final Logger logger = Logger.getLogger(InputHandler.class.getName());

    private final Set<Command> pressed  = EnumSet.noneOf(Command.class);
    private final Set<Command> previous = EnumSet.noneOf(Command.class);

    public synchronized void update() {
        previous.clear();
        previous.addAll(pressed);
    }

    public synchronized void press(Command cmd) {
        if(cmd == Command.TOGGLE_PAUSE) {
            EventBus.publish(GameEvent.TOGGLE_PAUSE);
        }
        pressed.add(cmd);
        logger.finest("Registerd: " + cmd);
    }

    public synchronized void release(Command cmd) {
        pressed.remove(cmd);
        logger.finest("Unregistered: " + cmd);
    }

    public synchronized boolean isPressed(Command cmd) {
        return pressed.contains(cmd);
    }
}
