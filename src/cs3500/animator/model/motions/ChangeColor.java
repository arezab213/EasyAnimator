package cs3500.animator.model.motions;

import cs3500.animator.model.shapes.IShape;
import cs3500.animator.view.visitors.MotionVisitor;
import java.awt.Color;
import java.util.Objects;

/**
 * Represents a shape's change of color.
 */
public class ChangeColor extends AbstractMotion {

  private final Color endColor;
  private Color originalColor;

  /**
   * Constructs a ChangeColor object with the given color to be changed to.
   *
   * @param tStart the start tick for the color change
   * @param tEnd   the end tick for the color change
   * @param ec     the end color
   */
  public ChangeColor(int tStart, int tEnd, Color ec) {
    super(tStart, tEnd);
    this.originalColor = new Color(0, 0, 0);
    this.endColor = ec;
  }

  @Override
  public void changeShape(IShape shape, int tick) throws IllegalArgumentException {
    if (!(tStart <= tick && tEnd >= tick)) {
      throw new IllegalArgumentException("Inputted tick does not correspond to this motion.");
    }
    this.originalColor = shape.getColor();
    // Calculates the differences between the original and end color
    // for each color component
    int redDif = this.endColor.getRed() - this.originalColor.getRed();
    int greenDif = this.endColor.getGreen() - this.originalColor.getGreen();
    int blueDif = this.endColor.getBlue() - this.originalColor.getBlue();

    int interval = this.tEnd - this.tStart;
    int tickDif = tick - this.tStart;

    // Calculates the change in each color per tick
    double rcpt = (double) redDif / (double) interval;
    double gcpt = (double) greenDif / (double) interval;
    double bcpt = (double) blueDif / (double) interval;

    Color newColor = new Color(((int) Math.round(this.originalColor.getRed() + (rcpt * tickDif))),
        ((int) Math.round(this.originalColor.getGreen() + (gcpt * tickDif))),
        ((int) Math.round(this.originalColor.getBlue() + (bcpt * tickDif))));

    shape.setColor(newColor);
  }

  @Override
  public boolean isNotCompatible(Motion m) {
    if (!(m instanceof ChangeColor)) {
      return false;
    } else {
      AbstractMotion a = (AbstractMotion) m;
      return (((a.tStart >= this.tStart) && (a.tStart < this.tEnd))
          || ((a.tEnd > this.tStart) && (a.tEnd <= this.tEnd)));
    }
  }

  @Override
  public String accept(MotionVisitor<IShape, Motion> v, IShape shape, String initial) {
    return v.visitChangeColor(this, shape, initial);
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof ChangeColor)) {
      return false;
    }
    ChangeColor a = (ChangeColor) that;

    return
        ((this.tStart == a.tStart) && (this.tEnd == a.tEnd) && (this.endColor.equals(a.endColor))
            && (this.originalColor.equals(a.originalColor)));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.tStart, this.tEnd, this.endColor, this.originalColor);
  }
}
