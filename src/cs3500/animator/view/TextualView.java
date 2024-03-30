package cs3500.animator.view;

import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Renders a textual representation of the {@code AnimatorModel}.
 */
public class TextualView implements AnimatorView {

  private final double ticksPerSec;
  private final IViewModel<?, ?> model;
  private Appendable ap;

  /**
   * Constructs a {@code TextualView} object.
   *
   * @param ticksPerSec the amount of ticks corresponding to second.
   * @param model       the model that this view represents
   * @param ap          the appendable being used for this view
   * @throws IllegalArgumentException if the model is null or if ticksPerSec is non Positive.
   */
  public TextualView(double ticksPerSec, IViewModel<?, ?> model, Appendable ap) {
    if (ticksPerSec <= 0) {
      throw new IllegalArgumentException("Ratio of ticks to seconds must be positive.");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model can not be null.");
    }
    this.ticksPerSec = ticksPerSec;
    this.model = model;
    this.ap = ap;
  }

  /**
   * Constructs a {@code TextualView} object with default parameters.
   *
   * @param model the model that this view represents
   */
  public TextualView(IViewModel<?, ?> model) {
    this.ticksPerSec = 1.0;
    this.model = model;
  }

  @Override
  public String toString() {
    String modelAcc = "";
    modelAcc += "canvas " + this.model.getMinPos().getX() + " " + this.model.getMinPos().getY()
        + " " + (this.model.getMaxPos().getX() - this.model.getMinPos().getX())
        + " " + (this.model.getMaxPos().getY() - this.model.getMinPos().getY()) + "\n"
        + model.describe(this.ticksPerSec);
    return modelAcc;
  }

  @Override
  public void render() {
    try {
      this.ap.append(this.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Could not generate output");
    }
  }

  @Override
  public void setButtonListeners(ActionListener actionEvent) {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void resetTimer(int tick) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void cancelTimer() {
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
}
