package cs3500.animator.controller;

/**
 * The controller interface for an animation.
 */
public interface AnimatorController {

  /**
   * Sets the button listeners of the controller and calls the AnimatorView method render on the
   * corresponding view.
   */
  void setUpView();
}
