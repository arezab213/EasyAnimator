package cs3500.animator.view;

import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.shapes.IShape;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Represents a visual view of an animation.
 */
public class VisualView extends JFrame implements AnimatorView {

  protected final double ticksPerSec;
  protected final IViewModel<IShape, Motion> model;
  protected final AnimatorPanel panel;
  protected Timer timer;
  protected int currentTick;

  /**
   * Constructs a {@code VisualView} object.
   *
   * @param ticksPerSec The amount of ticks corresponding to a second in real-time.
   * @param model       The model of the animation, in a form where only information can be accessed
   *                    but not modified.
   * @throws IllegalArgumentException if the model is null or if ticksPerSec is non Positive.
   */
  public VisualView(double ticksPerSec, IViewModel<IShape, Motion> model) {
    super();
    if (ticksPerSec <= 0) {
      throw new IllegalArgumentException("Ratio of ticks to seconds must be positive.");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model can not be null.");
    }
    this.ticksPerSec = ticksPerSec;
    this.model = model;
    this.setTitle("Visual View");
    this.setSize((int) (model.getMaxPos().getX() - model.getMinPos().getX()),
        (int) (model.getMaxPos().getY() - model.getMinPos().getY()));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Panel
    this.panel = new AnimatorPanel(model.getMinPos());
    this.panel
        .setPreferredSize(new Dimension((int) (model.getMaxPos().getX() - model.getMinPos().getX()),
            (int) (model.getMaxPos().getY() - model.getMinPos().getY())));
    this.add(this.panel, BorderLayout.CENTER);

    this.setLocation(250, 0);
    this.add(new JScrollPane(this.panel));
    this.timer = new Timer();
    this.currentTick = 1;
  }

  @Override
  public void render() {
    this.setVisible(true);

    this.timer.schedule(new VisualViewTimerTask(), 0, (int) (1000 / ticksPerSec));

  }

  @Override
  public void setButtonListeners(ActionListener actionEvent) {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void resetTimer(int tick) throws IllegalArgumentException, UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void cancelTimer() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public int getCurrentTick() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void toggleLoop() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void toggleOutline() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void toggleDiscrete() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void incrementSpeed() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void decrementSpeed() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  /**
   * Represents the timer task to create the components of the animation.
   */
  protected class VisualViewTimerTask extends TimerTask {

    //private int currentTick = 1;

    /**
     * Creates a {@code VisualViewTimerTask} object.
     */
    VisualViewTimerTask() {
      super();
    }

    @Override
    public void run() {
      List<IShape> currentShapes = model.getShapes(currentTick);
      if (currentTick > model.getFinalTick()) {
        timer.cancel();
      } else {
        panel.draw(currentShapes);
        currentTick++;
      }
    }
  }
}
