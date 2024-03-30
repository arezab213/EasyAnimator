package cs3500.animator.controller.commands;

import cs3500.animator.view.AnimatorView;

/**
 * Represents looping an animation.
 */
public class DecreaseSpeed implements AnimatorCommand {

  @Override
  public void executeCommand(AnimatorView view) {
    view.decrementSpeed();
  }
}
