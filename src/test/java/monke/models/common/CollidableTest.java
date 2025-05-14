package monke.models.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollidableTest {

    private static class TestCollidable implements Collidable {
        private final BoundingBox bounds;
        // for capturing resolve calls
        double lastDx, lastOverlapX, lastDy, lastOverlapY;
        boolean horizontalResolved, verticalResolved;
        boolean customCalled;

        TestCollidable(double x, double y, double w, double h) {
            bounds = new BoundingBox(x, y, w, h);
        }

        @Override
        public BoundingBox getBounds() {
            return bounds;
        }

        @Override
        public void resolveHorizontalCollision(double dx, double overlapX) {
            horizontalResolved = true;
            lastDx = dx;
            lastOverlapX = overlapX;
            // e.g. push this object out of collision:
            bounds.setX(bounds.getX() + (dx > 0 ? overlapX : -overlapX));
        }

        @Override
        public void resolveVerticalCollision(double dy, double overlapY) {
            verticalResolved = true;
            lastDy = dy;
            lastOverlapY = overlapY;
            bounds.setY(bounds.getY() + (dy > 0 ? overlapY : -overlapY));
        }

        @Override
        public void onCollisionCustom(Collidable other) {
            customCalled = true;
        }

        void resetFlags() {
            horizontalResolved = verticalResolved = customCalled = false;
        }
    }

    private TestCollidable a;
    private TestCollidable b;

    @BeforeEach
    void setUp() {
        // two 2×2 boxes near each other
        a = new TestCollidable(0, 0, 2, 2);
        b = new TestCollidable(1, 1, 2, 2);
        a.resetFlags();
        b.resetFlags();
    }

    @Test
    void testUpdateBounds() {
        a.updateBounds(5, 7);
        assertEquals(5, a.getBounds().getX(), 1e-9);
        assertEquals(7, a.getBounds().getY(), 1e-9);
    }

    @Test
    void testAreOverlapping_trueAndFalse() {
        // initially overlapping
        assertTrue(Collidable.areOverlapping(a, b));
        // move b far away
        b.updateBounds(100, 100);
        assertFalse(Collidable.areOverlapping(a, b));
    }

    @Test
    void testOverlapsConvenience() {
        assertTrue(a.overlaps(b));
        b.updateBounds(100, 100);
        assertFalse(a.overlaps(b));
    }

    @Test
    void testResolveCollision_horizontalPreference() {
        // Arrange: make horizontal overlap smaller than vertical by shifting b
        a.updateBounds(0, 0);
        b.updateBounds(1.5, 0); // shall produce smaller overlap on X
        a.resetFlags();

        // Act
        a.resolveCollision(b);

        // Assert custom hook called
        assertTrue(a.customCalled, "onCollisionCustom should be invoked first");

        // Should resolve horizontally
        assertTrue(a.horizontalResolved && !a.verticalResolved, "Should pick horizontal collision");
        // dx = centerA.x - centerB.x = (0+1) - (1.5+1) = 1 - 2.5 = -1.5
        // halfWidths = (2+2)/2 = 2 → overlapX = 2 - |dx| = 2 - 1.5 = 0.5
        assertEquals(-1.5, a.lastDx, 1e-9);
        assertEquals(0.5, a.lastOverlapX, 1e-9);
        // bounding box X should have moved by –overlapX = –0.5
        assertEquals(0 - 0.5, a.getBounds().getX(), 1e-9);
    }

    @Test
    void testResolveCollision_verticalPreference() {
        a.updateBounds(0, 0);
        b.updateBounds(0, 1.5); // small vertical overlap
        a.resetFlags();

        a.resolveCollision(b);

        assertTrue(a.customCalled, "onCollisionCustom should be invoked first");
        assertTrue(a.verticalResolved && !a.horizontalResolved, "Should pick vertical collision");
        // dy = (0+1) - (1.5+1) = 1 - 2.5 = -1.5; overlapY = 2 - 1.5 = 0.5
        assertEquals(-1.5, a.lastDy, 1e-9);
        assertEquals(0.5, a.lastOverlapY, 1e-9);
        assertEquals(0 - 0.5, a.getBounds().getY(), 1e-9);
    }

    @Test
    void testNoBoundsOnNull() {
        Collidable nullBounds = () -> null;
        assertFalse(Collidable.areOverlapping(nullBounds, a));
        assertFalse(Collidable.areOverlapping(a, nullBounds));
        // overlaps uses same logic
        assertFalse(nullBounds.overlaps(a));
    }
}
