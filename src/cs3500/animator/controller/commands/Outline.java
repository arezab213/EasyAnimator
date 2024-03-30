package cs3500.animator.controller.commands;

import cs3500.animator.view.AnimatorView;

/**
 * Represents changing an animation so that all the shapes are represented as outlines.
 */
public class Outline implements AnimatorCommand {

  @Override
  public void executeCommand(AnimatorView view) {
    view.toggleOutline();
  }
}
