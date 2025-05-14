package monke.models.common;

public interface Updatable {
     /**
      * Updates the state of the object based on the time delta.
      * @param dt Time delta since the last update.
      */
     void update(double dt);
}
