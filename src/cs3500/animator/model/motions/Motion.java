package cs3500.animator.model.motions;

import cs3500.animator.model.shapes.IShape;
import cs3500.animator.view.visitors.MotionVisitor;

/**
 * Represents a manipulation of a {@code Shape}.
 */
public interface Motion {

  /**
   * Changes the given shape to its state at a specified tick.
   *
   * @param shape the provided {@code Shape}
   * @param tick  the provided tick
   * @throws IllegalArgumentException if the given tick is not within this motions time interval.
   */
  void changeShape(IShape shape, int tick) throws IllegalArgumentException;

  /**
   * Determines if the given motion is compatible with this motion (does not overlap).
   *
   * @param motion the motion being compared
   * @return whether the motions are the same type and the time periods of the two motions overlap
   */
  boolean isNotCompatible(Motion motion);

  /**
   * Determines if the given tick is contained in this motion.
   *
   * @param time the given tick
   * @return whether or not the tick is contained in the motion.
   */
  boolean timeContains(int time);

  /**
   * Retrieves the start tick of this motion.
   *
   * @return the start time
   */
  int getStartTick();

  /**
   * Retrieves the end tick of this motion.
   *
   * @return the end time
   */
  int getEndTick();

  /**
   * Accepts a {@code MotionVisitor}.
   *
   * @param v     the visitor being accepted
   * @param shape the shape to be modified by this motion
   * @return String representing a motion's transformation on a shape
   */
  String accept(MotionVisitor<IShape, Motion> v, IShape shape, String initial);
}
