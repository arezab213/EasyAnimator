package cs3500.animator.view;

import java.awt.event.ActionListener;

/**
 * Represents a view for an {@code AnimatorModel}.
 */
public interface AnimatorView {

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   */
  void render();

  /**
   * Sets the {@code ActionListeners} for all the buttons in the view.
   *
   * @param actionEvent the {@code ActionListener} for the buttons
   * @throws UnsupportedOperationException if the view does not have buttons
   */
  void setButtonListeners(ActionListener actionEvent) throws UnsupportedOperationException;

  /**
   * Sets a new timer for the view starting at the given tick value.
   *
   * @param tick the tick that the view will be set to.
   * @throws IllegalArgumentException if the given tick is negative.
   */
  void resetTimer(int tick) throws IllegalArgumentException, UnsupportedOperationException;

  /**
   * Cancels the timer of the view.
   *
   * @throws UnsupportedOperationException if the view does not have buttons
   */
  void cancelTimer() throws UnsupportedOperationException;

  /**
   * Gets the current tick of the view.
   *
   * @throws UnsupportedOperationException if the view does not have buttons
   */
  int getCurrentTick() throws UnsupportedOperationException;

  /**
   * Toggles the boolean looping field of an interactive view.
   *
   * @throws UnsupportedOperationException if the view does not have buttons
   */
  void toggleLoop() throws UnsupportedOperationException;

  /**
   * Toggles the display mode of the shapes of an animation between "fill" and "outline".
   *
   * @throws UnsupportedOperationException if the view does not have buttons
   */
  void toggleOutline() throws UnsupportedOperationException;

  /**
   * Toggles the animation to show discrete frames at the beginnings and ends of motions rather than
   * continuous frames.
   *
   * @throws UnsupportedOperationException if the view does not have buttons
   */
  void toggleDiscrete() throws UnsupportedOperationException;

  /**
   * Increments the ticksPerSec field of the view by 10 if it is at or above 11, 2 if otherwise.
   *
   * @throws UnsupportedOperationException if the view does not have buttons
   */
  void incrementSpeed() throws UnsupportedOperationException;

  /**
   * Decrements the ticksPerSec field of the view by 10 if it is at or above 11, 2 if it is less
   * than 11 and greater than or equal to 3.
   *
   * @throws UnsupportedOperationException if the view does not have buttons
   */
  void decrementSpeed() throws UnsupportedOperationException;
}
