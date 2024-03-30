package cs3500.animator.view;

import cs3500.animator.model.Position;
import cs3500.animator.model.SpeedInterval;
import java.util.List;

/**
 * Represents the connection between the model and view.
 */
public interface IViewModel<K, L> {

  /**
   * Gets all the current shapes at the given time.
   *
   * @param time the given time in ticks
   * @return a list of all the current shapes
   */
  List<K> getShapes(int time);

  /**
   * Produces a description of what the animation will look like.
   *
   * @param ratio the real time ratio describing the amount of ticks per second.
   * @return a string describing the animation
   */
  String describe(double ratio);

  /**
   * Gets the list of motions for a given shape.
   *
   * @param index the index of the shape containing the desiring list of motions
   * @return the list of motions for the given shape
   * @throws IllegalArgumentException if the shape index is invalid
   */
  List<L> getMotions(int index) throws IllegalArgumentException;

  /**
   * Gets the shape at the given index of the model at the given tick.
   *
   * @param index The index of the shape
   * @param tick  The tick associated with the state of the desired shape
   * @return The shape as it exists at the given tick
   * @throws IllegalArgumentException if the shape index is invalid
   */
  K getShape(int index, int tick) throws IllegalArgumentException;

  /**
   * Gets the minPos for this model.
   *
   * @return the {@code Position} representing the minimum position of the model.
   */
  Position getMinPos();

  /**
   * Gets the maxPos for this model.
   *
   * @return the {@code Position} representing the maximum position of the model.
   */
  Position getMaxPos();

  /**
   * Gets the greatest tick value for any motion of any shape in the model.
   *
   * @return the greatest tick value for the model
   */
  int getFinalTick();

  /**
   * Gets the next tick that represents the start or end tick of a model's motion.
   *
   * @param currentTick the current tick to see where the next start or end tick is.
   * @return The next start or end tick of a motion.
   */
  int getNextDiscreteTick(int currentTick);

  /**
   * Gets a copy of the model's list of {@code SpeedIntervals}.
   *
   * @return A copy list of the model's list of {@code SpeedIntervals}.
   */
  List<SpeedInterval> getSpeedIntervals();
}
