package cs3500.animator.view;

import cs3500.animator.model.SpeedInterval;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.shapes.IShape;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * Represents an interactive view of an animation.
 */
public class InteractiveView extends VisualView {

  private final JButton play;
  private final JButton pause;
  private final JButton rewind;
  private final JToggleButton loop;
  private final JToggleButton outline;
  private final JToggleButton discrete;
  private final JButton speedIncrease;
  private final JButton speedDecrease;
  private double variableTicksPerSec;
  private boolean isLooping = false;
  private boolean isPaused = true;
  private boolean isDiscrete = false;
  private boolean inInterval = false;
  private double originalSpeed = 0;

  /**
   * Constructs an {@code InteractiveView} object.
   *
   * @param variableTicksPerSec the amount of ticks corresponding to second.
   * @param model               the model that this view represents
   */
  public InteractiveView(double variableTicksPerSec, IViewModel<IShape, Motion> model) {
    super(variableTicksPerSec, model);
    setTitle("Interactive Animator");
    this.variableTicksPerSec = variableTicksPerSec;

    // Panel to manipulate the animation
    JPanel playBackPanel = new JPanel();
    playBackPanel.setLayout(new BoxLayout(playBackPanel, BoxLayout.PAGE_AXIS));

    JPanel startStopPanel = new JPanel();
    startStopPanel.setLayout(new FlowLayout());

    // Rewind button
    this.rewind = new JButton("↲");
    this.rewind.setActionCommand("rewind");
    startStopPanel.add(rewind);

    // Pause button
    this.pause = new JButton("||");
    this.pause.setActionCommand("pause");
    startStopPanel.add(pause);

    // Play button
    this.play = new JButton("▶");
    this.play.setActionCommand("play");
    startStopPanel.add(play);

    // Loop button
    this.loop = new JToggleButton("⟲");
    this.loop.setActionCommand("loop");
    startStopPanel.add(loop);

    // Outline button
    this.outline = new JToggleButton("Outline");
    this.outline.setActionCommand("outline");
    startStopPanel.add(outline);

    // Discrete playing button
    this.discrete = new JToggleButton("Discrete");
    this.discrete.setActionCommand("discrete");
    startStopPanel.add(discrete);

    playBackPanel.add(startStopPanel);

    JPanel speedPanel = new JPanel();
    speedPanel.setLayout(new FlowLayout());
    JLabel speedLabel = new JLabel("Modify Animation Speed:");
    speedPanel.add(speedLabel);
    this.speedIncrease = new JButton("＋");
    this.speedIncrease.setActionCommand("increaseSpeed");
    this.speedDecrease = new JButton("－");
    this.speedDecrease.setActionCommand("decreaseSpeed");
    speedPanel.add(speedDecrease);
    speedPanel.add(speedIncrease);
    playBackPanel.add(speedPanel);

    this.add(playBackPanel, BorderLayout.SOUTH);

    this.pack();
  }

  @Override
  public void render() {
    this.setVisible(true);
    List<IShape> currentShapes = model.getShapes(currentTick);
    panel.draw(currentShapes);
  }

  @Override
  public void resetTimer(int tick) throws IllegalArgumentException {
    if (tick < 0) {
      throw new IllegalArgumentException("Inputted tick can not be negative.");
    }
    this.currentTick = tick;
    this.isPaused = false;
    this.timer.cancel();
    this.timer = new Timer();
    if (isDiscrete) {
      this.timer.schedule(new InteractiveViewTimerTask(), 1000, 1000);
    } else {
      this.timer.schedule(new InteractiveViewTimerTask(), 0, (int) (1000 / variableTicksPerSec));
    }
  }

  @Override
  public void cancelTimer() throws UnsupportedOperationException {
    this.isPaused = true;
    this.timer.cancel();
  }

  @Override
  public int getCurrentTick() throws UnsupportedOperationException {
    return this.currentTick;
  }

  @Override
  public void setButtonListeners(ActionListener actionEvent) {
    this.rewind.addActionListener(actionEvent);
    this.pause.addActionListener(actionEvent);
    this.play.addActionListener(actionEvent);
    this.loop.addActionListener(actionEvent);
    this.outline.addActionListener(actionEvent);
    this.discrete.addActionListener(actionEvent);
    this.speedIncrease.addActionListener(actionEvent);
    this.speedDecrease.addActionListener(actionEvent);
  }

  @Override
  public void toggleLoop() throws UnsupportedOperationException {
    isLooping = !isLooping;
  }

  @Override
  public void toggleOutline() throws UnsupportedOperationException {
    this.panel.toggleOutline();
  }

  @Override
  public void toggleDiscrete() throws UnsupportedOperationException {
    isDiscrete = !isDiscrete;
    if (isDiscrete) {
      this.timer.cancel();
      this.timer = new Timer();
      this.timer.schedule(new InteractiveViewTimerTask(), 1000, 1000);
    } else {
      this.timer.cancel();
      this.timer = new Timer();
      this.timer.schedule(new InteractiveViewTimerTask(), (int) (1000 / variableTicksPerSec),
          (int) (1000 / variableTicksPerSec));
    }
  }

  @Override
  public void incrementSpeed() throws UnsupportedOperationException {
    if (!isDiscrete && !inInterval) {
      if (variableTicksPerSec >= 11) {
        this.variableTicksPerSec += 10;
      } else {
        this.variableTicksPerSec += 2;
      }
      if (!this.isPaused) {
        this.timer.cancel();
        this.timer = new Timer();
        this.timer.schedule(new InteractiveViewTimerTask(), (int) (1000 / variableTicksPerSec),
            (int) (1000 / variableTicksPerSec));
      }
    }
  }

  @Override
  public void decrementSpeed() throws UnsupportedOperationException {
    if (!isDiscrete && !inInterval) {
      if (variableTicksPerSec >= 11) {
        this.variableTicksPerSec -= 10;
      } else {
        if (variableTicksPerSec >= 3) {
          this.variableTicksPerSec -= 2;
        }
      }
      if (!this.isPaused) {
        this.timer.cancel();
        this.timer = new Timer();
        this.timer.schedule(new InteractiveViewTimerTask(), (int) (1000 / variableTicksPerSec),
            (int) (1000 / variableTicksPerSec));
      }
    }
  }


  /**
   * Returns the speed of the current {@code SpeedInterval} if the current tick is in an interval
   * and returns 0 otherwise.
   */
  private void setIntervalSpeed() {
    for (SpeedInterval interval : this.model.getSpeedIntervals()) {
      if (currentTick == interval.getStartTick() && !inInterval) {
        inInterval = true;
        originalSpeed = variableTicksPerSec;
        variableTicksPerSec = interval.getSpeed();
        timer.cancel();
        timer = new Timer();
        timer.schedule(new InteractiveViewTimerTask(), 0, (1000 / (int) variableTicksPerSec));
        break;
      }
    }
  }

  private class InteractiveViewTimerTask extends TimerTask {

    InteractiveViewTimerTask() {
      super();
    }


    @Override
    public void run() {

      List<IShape> currentShapes;
      if (currentTick > model.getFinalTick()) {
        if (isLooping) {
          resetTimer(1);
        } else {
          timer.cancel();
        }
      } else {
        if (!inInterval) {
          setIntervalSpeed();
        } else {
          for (SpeedInterval interval : model.getSpeedIntervals()) {
            if (currentTick == interval.getEndTick()) {
              inInterval = false;
              variableTicksPerSec = originalSpeed;
              timer.cancel();
              timer = new Timer();
              timer.schedule(new InteractiveViewTimerTask(),
                  0, (int) (1000 / variableTicksPerSec));
              break;
            }
          }
        }
        if (isDiscrete) {
          currentTick = model.getNextDiscreteTick(currentTick);
        }
        currentShapes = model.getShapes(currentTick);
        panel.draw(currentShapes);
        currentTick++;
      }
    }
  }
}
