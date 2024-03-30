package cs3500.animator.model;

import cs3500.animator.view.IViewModel;

/**
 * The model for the Easy Animator Application. Represents the state and functionality of an
 * animation.
 */
public interface AnimatorModel<K, L> extends IViewModel<K, L> {

  /**
   * Adds a shape to the animation.
   *
   * @param shape The shape being added.
   */
  void addShape(K shape);

  /**
   * Removes a shape from the animation.
   *
   * @param shape The shape being removed.
   */
  void removeShape(K shape);

  /**
   * Adds a motion to the given shape.
   *
   * @param index  The index of the shape being modified
   * @param motion The modification of the shape to be added
   * @throws IllegalArgumentException if the shape index is invalid or if the motion is not
   *                                  compatible
   */
  void addMotionToModel(int index, L motion) throws IllegalArgumentException;

  /**
   * Removes a motion from the given shape.
   *
   * @param index  The index of the shape being modified
   * @param motion The modification of the shape to be removed
   * @throws IllegalArgumentException if the shape index is invalid or if the motion is not does not
   *                                  exist for the shape.
   */
  void removeMotionFromModel(int index, L motion) throws IllegalArgumentException;

  /**
   * Adds a SpeedInterval to the model's list of intervals, by taking in three integers used to
   * construct the interval.
   *
   * @param startTick the tick beginning the speed interval.
   * @param endTick   the tick ending the speed interval.
   * @param speed     the speed of the animation during this interval
   */
  void addSpeedInterval(int startTick, int endTick, int speed);
}
