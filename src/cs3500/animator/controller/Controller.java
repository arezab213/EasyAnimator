package cs3500.animator.controller;

import cs3500.animator.controller.commands.AnimatorCommand;
import cs3500.animator.controller.commands.DecreaseSpeed;
import cs3500.animator.controller.commands.Discrete;
import cs3500.animator.controller.commands.IncreaseSpeed;
import cs3500.animator.controller.commands.Loop;
import cs3500.animator.controller.commands.Outline;
import cs3500.animator.controller.commands.Pause;
import cs3500.animator.controller.commands.Play;
import cs3500.animator.controller.commands.Rewind;
import cs3500.animator.view.AnimatorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a controller implementation for the animation.
 */
public class Controller implements AnimatorController, ActionListener {

  private final AnimatorView view;

  /**
   * Constructs a {@code Controller} object.
   *
   * @param view the view representing the animation
   */
  public Controller(AnimatorView view) {

    this.view = view;
  }

  @Override
  public void setUpView() {
    try {
      this.view.setButtonListeners(this);
      this.view.render();
    } catch (UnsupportedOperationException e) {
      this.view.render();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    AnimatorCommand cmd;

    switch (e.getActionCommand()) {
      case "rewind":
        cmd = new Rewind();
        break;
      case "pause":
        cmd = new Pause();
        break;
      case "play":
        cmd = new Play();
        break;
      case "loop":
        cmd = new Loop();
        break;
      case "outline":
        cmd = new Outline();
        break;
      case "discrete":
        cmd = new Discrete();
        break;
      case "increaseSpeed":
        cmd = new IncreaseSpeed();
        break;
      case "decreaseSpeed":
        cmd = new DecreaseSpeed();
        break;
      default:
        cmd = null;
        break;
    }
    if (cmd != null) {
      cmd.executeCommand(view);
    }
  }
}
