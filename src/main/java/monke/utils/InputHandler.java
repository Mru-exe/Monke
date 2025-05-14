package monke.utils;

import monke.enums.Command;
import monke.enums.GameEvent;

import java.util.*;
import java.util.logging.Logger;

/**
 * Handles input commands for the game.
 * This class manages the state of pressed commands and allows for checking their status.
 */
public class InputHandler {
    private static final Logger logger = Logger.getLogger(InputHandler.class.getName());

    private final Set<Command> pressed  = EnumSet.noneOf(Command.class);
    private final Set<Command> previous = EnumSet.noneOf(Command.class);

    /**
     * Updates the handler by removing all commands that are not currently pressed.
     */
    public synchronized void update() {
        previous.clear();
        previous.addAll(pressed);
    }

    /**
     * Registers a command as pressed.
     * @param cmd The command to register.
     */
    public synchronized void press(Command cmd) {
        if(cmd == Command.TOGGLE_PAUSE) {
            EventBus.publish(GameEvent.TOGGLE_PAUSE);
        }
        pressed.add(cmd);
        logger.finest("Registered: " + cmd);
    }

    /**
     * Unregisters a command as pressed.
     * @param cmd The command to unregister.
     */
    public synchronized void release(Command cmd) {
        pressed.remove(cmd);
        logger.finest("Unregistered: " + cmd);
    }

    /**
     * Checks if a command is currently pressed.
     * @param cmd The command to check.
     * @return True if the command is pressed, false otherwise.
     */
    public synchronized boolean isPressed(Command cmd) {
        return pressed.contains(cmd);
    }
}
