package controllertests;

import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.Controller;
import cs3500.animator.view.AnimatorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the controller for the animation by using a mock InteractiveView.
 */
public class ControllerTest {

  StringBuilder sb;
  JButton play;
  JButton pause;
  JButton rewind;
  JToggleButton loop;
  JToggleButton outline;
  JToggleButton discrete;
  JButton speedIncrease;
  JButton speedDecrease;
  AnimatorView view;
  Controller controller;

  @Before
  public void initConditions() {
    sb = new StringBuilder();
    rewind = new JButton("");
    pause = new JButton("");
    play = new JButton("");
    loop = new JToggleButton("");
    outline = new JToggleButton("");
    discrete = new JToggleButton("");
    speedIncrease = new JButton("");
    speedDecrease = new JButton("");
    view = new ViewMock(sb, play, pause, rewind, loop, outline, discrete, speedIncrease,
        speedDecrease);
    controller = new Controller(view);
  }

  @Test
  // tests that the controller properly invokes the correct
  // view methods when its method go is called
  public void testGo() {
    controller.setUpView();
    assertEquals("setButtonListeners\nrendered\n", sb.toString());
  }

  // Tests that the controller correctly invokes the resetTimer
  // method on the view when the rewind action event occurs,
  // by calling executeCommand on the Rewind command
  @Test
  public void testRewind() {
    ActionEvent rewind = new ActionEvent(this.rewind, 0, "rewind");
    controller.actionPerformed(rewind);
    assertEquals("resetTimer\n", sb.toString());
  }

  // Tests that the controller correctly invokes the cancelTimer
  // method on the view when the pause action event occurs,
  // by calling executeCommand on the Pause command
  @Test
  public void testPause() {
    ActionEvent pause = new ActionEvent(this.pause, 0, "pause");
    controller.actionPerformed(pause);
    assertEquals("cancelTimer\n", sb.toString());
  }

  // Tests that the controller correctly invokes the getCurrentTick and resetTimer
  // methods on the view when the play action event occurs,
  // by calling executeCommand on the Play command
  @Test
  public void testPlay() {
    ActionEvent play = new ActionEvent(this.play, 0, "play");
    controller.actionPerformed(play);
    assertEquals("getCurrentTick\nresetTimer\n", sb.toString());
  }

  // Tests that the controller correctly invokes the toggleLoop
  // method on the view when the loop action event occurs,
  // by calling executeCommand on the Loop command
  @Test
  public void testLoop() {
    ActionEvent loop = new ActionEvent(this.loop, 0, "loop");
    controller.actionPerformed(loop);
    assertEquals("toggleLoop\n", sb.toString());
  }

  // Tests that the controller correctly invokes the toggleOutline
  // method on the view when the outline action event occurs,
  // by calling executeCommand on the Outline command
  @Test
  public void testOutline() {
    ActionEvent outline = new ActionEvent(this.outline, 0, "outline");
    controller.actionPerformed(outline);
    assertEquals("toggleOutline\n", sb.toString());
  }

  // Tests that the controller correctly invokes the toggleDiscrete
  // method on the view when the discrete action event occurs,
  // by calling executeCommand on the Discrete command
  @Test
  public void testDiscrete() {
    ActionEvent discrete = new ActionEvent(this.discrete, 0, "discrete");
    controller.actionPerformed(discrete);
    assertEquals("toggleDiscrete\n", sb.toString());
  }

  // Tests that the controller correctly invokes the incrementSpeed
  // method on the view when the increaseSpeed action event occurs,
  // by calling executeCommand on the IncreaseSpeed command
  @Test
  public void testIncreaseSpeed() {
    ActionEvent speedIncrease = new ActionEvent(this.speedIncrease, 0, "increaseSpeed");
    controller.actionPerformed(speedIncrease);
    assertEquals("incrementSpeed\n", sb.toString());
  }

  // Tests that the controller correctly invokes the decrementSpeed
  // method on the view when the decreaseSpeed action event occurs,
  // by calling executeCommand on the DecrementSpeed command
  @Test
  public void testDecreaseSpeed() {
    ActionEvent speedDecrease = new ActionEvent(this.speedDecrease, 0, "decreaseSpeed");
    controller.actionPerformed(speedDecrease);
    assertEquals("decrementSpeed\n", sb.toString());
  }

  /**
   * Mock class for testing purposes representing an InteractiveView that updates its log whenever a
   * class method is invoked. By assessing the string contents of the log, the controller's
   * functionality can be tested.
   */
  public static class ViewMock implements AnimatorView {

    public StringBuilder log;
    public JButton play;
    public JButton pause;
    public JButton rewind;
    public JToggleButton loop;
    public JToggleButton outline;
    public JToggleButton discrete;
    public JButton speedIncrease;
    public JButton speedDecrease;

    /**
     * Constructs a {@code ViewMock} object by adding all the JButtons as parameters.
     *
     * @param log           the log to keep track of methods called
     * @param play          the play button
     * @param pause         the pause button
     * @param rewind        the rewind button
     * @param loop          the loop button
     * @param speedIncrease the speed increase button
     * @param speedDecrease the speed decrease button
     */
    public ViewMock(StringBuilder log, JButton play, JButton pause, JButton rewind,
        JToggleButton loop, JToggleButton outline, JToggleButton discrete, JButton speedIncrease,
        JButton speedDecrease) {
      this.log = Objects.requireNonNull(log);
      this.play = play;
      this.pause = pause;
      this.rewind = rewind;
      this.loop = loop;
      this.outline = outline;
      this.discrete = discrete;
      this.speedIncrease = speedIncrease;
      this.speedDecrease = speedDecrease;
    }


    @Override
    public void render() {
      log.append("rendered\n");
    }

    @Override
    public void setButtonListeners(ActionListener actionEvent)
        throws UnsupportedOperationException {
      this.rewind.addActionListener(actionEvent);
      this.pause.addActionListener(actionEvent);
      this.play.addActionListener(actionEvent);
      this.loop.addActionListener(actionEvent);
      this.speedIncrease.addActionListener(actionEvent);
      this.speedDecrease.addActionListener(actionEvent);
      log.append("setButtonListeners\n");
    }

    @Override
    public void resetTimer(int tick)
        throws IllegalArgumentException, UnsupportedOperationException {
      log.append("resetTimer\n");
    }

    @Override
    public void cancelTimer() throws UnsupportedOperationException {
      log.append("cancelTimer\n");
    }

    @Override
    public int getCurrentTick() throws UnsupportedOperationException {
      log.append("getCurrentTick\n");
      return 0;
    }

    @Override
    public void toggleLoop() throws UnsupportedOperationException {
      log.append("toggleLoop\n");
    }

    @Override
    public void toggleOutline() throws UnsupportedOperationException {
      log.append("toggleOutline\n");
    }

    @Override
    public void toggleDiscrete() throws UnsupportedOperationException {
      log.append("toggleDiscrete\n");
    }

    @Override
    public void incrementSpeed() throws UnsupportedOperationException {
      log.append("incrementSpeed\n");
    }

    @Override
    public void decrementSpeed() throws UnsupportedOperationException {
      log.append("decrementSpeed\n");
    }
  }
}