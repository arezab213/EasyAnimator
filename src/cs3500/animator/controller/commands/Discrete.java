package cs3500.animator.controller.commands;

import cs3500.animator.view.AnimatorView;

/**
 * Represents setting an animation to show only the discrete frames for the starts and ends of
 * motions rather than continuous play.
 */
public class Discrete implements AnimatorCommand {

  @Override
  public void executeCommand(AnimatorView view) {
    view.toggleDiscrete();
  }
}
