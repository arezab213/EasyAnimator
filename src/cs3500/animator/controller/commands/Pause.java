package cs3500.animator.controller.commands;

import cs3500.animator.view.AnimatorView;

/**
 * Represents pausing an animation.
 */
public class Pause implements AnimatorCommand {

  @Override
  public void executeCommand(AnimatorView view) {
    view.cancelTimer();
  }
}
