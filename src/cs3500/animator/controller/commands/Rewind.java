package cs3500.animator.controller.commands;

import cs3500.animator.view.AnimatorView;

/**
 * Represents rewinding an animation back to the beginning.
 */
public class Rewind implements AnimatorCommand {

  @Override
  public void executeCommand(AnimatorView view) {
    view.resetTimer(1);
  }
}
