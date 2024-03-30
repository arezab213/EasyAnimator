package cs3500.animator.controller.commands;

import cs3500.animator.view.AnimatorView;

/**
 * Represents playing an animation.
 */
public class Play implements AnimatorCommand {

  @Override
  public void executeCommand(AnimatorView view) {
    view.resetTimer(view.getCurrentTick());
  }
}
