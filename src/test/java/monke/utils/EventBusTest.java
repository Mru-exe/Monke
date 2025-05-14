package monke.utils;

import monke.enums.GameEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EventBus}.
 */
class EventBusTest {

    private AtomicInteger counter;
    private Runnable listenerA;
    private Runnable listenerB;

    @BeforeEach
    void setUp() {
        counter = new AtomicInteger(0);
        listenerA = () -> counter.addAndGet(1);
        listenerB = () -> counter.addAndGet(10);
        // ensure clean state: unsubscribe any previous (in case tests run in same JVM)
        EventBus.unsubscribe(GameEvent.DIE, listenerA);
        EventBus.unsubscribe(GameEvent.DIE, listenerB);
    }

    @AfterEach
    void tearDown() {
        // cleanup listeners
        EventBus.unsubscribe(GameEvent.DIE, listenerA);
        EventBus.unsubscribe(GameEvent.DIE, listenerB);
    }

    @Test
    void testSubscribeAndPublish_singleListener() {
        EventBus.subscribe(GameEvent.DIE, listenerA);
        assertEquals(0, counter.get());

        EventBus.publish(GameEvent.DIE);
        assertEquals(1, counter.get(), "ListenerA should be invoked once");
    }

    @Test
    void testPublish_multipleListeners_invokedInOrder() {
        EventBus.subscribe(GameEvent.DIE, listenerA);
        EventBus.subscribe(GameEvent.DIE, listenerB);

        EventBus.publish(GameEvent.DIE);
        // listenerA adds 1, listenerB adds 10
        assertEquals(11, counter.get(), "Both listeners should be invoked");
    }

    @Test
    void testUnsubscribe_preventsInvocation() {
        EventBus.subscribe(GameEvent.DIE, listenerA);
        EventBus.subscribe(GameEvent.DIE, listenerB);

        // remove listenerA
        EventBus.unsubscribe(GameEvent.DIE, listenerA);

        EventBus.publish(GameEvent.DIE);
        // only listenerB should run
        assertEquals(10, counter.get());
    }

    @Test
    void testPublish_noListeners_noException() {
        // no subscription for GameEvent.LEVEL_UP
        assertDoesNotThrow(() -> EventBus.publish(GameEvent.OPEN_MAIN_MENU));
    }

    @Test
    void testSubscribeMultipleTimes_listenerRunsMultipleTimes() {
        EventBus.subscribe(GameEvent.DIE, listenerA);
        EventBus.subscribe(GameEvent.DIE, listenerA);

        EventBus.publish(GameEvent.DIE);
        // listenerA added twice, so should run twice
        assertEquals(2, counter.get());
    }
}
