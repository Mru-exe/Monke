package monke.models.common;

public interface Positionable {

    int getX();

    int getY();

    /**
     * Sets the X and Y coordinates of the object.
     *
     * @param x the new X position
     * @param y the new Y position
     */
    void setCoords(int x, int y);
}
