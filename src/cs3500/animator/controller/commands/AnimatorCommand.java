package cs3500.animator.controller.commands;

import cs3500.animator.view.AnimatorView;

/**
 * Represents a command to be given to the animation.
 */
public interface AnimatorCommand {

  /**
   * Executes the command on the given {@code AnimatorModel}.
   *
   * @param view the model that the command is to be executed on.
   */
  void executeCommand(AnimatorView view);
}
