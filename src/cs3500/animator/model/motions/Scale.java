package cs3500.animator.model.motions;

import cs3500.animator.model.shapes.IShape;
import cs3500.animator.view.visitors.MotionVisitor;
import java.util.Objects;

/**
 * Represents the modification of a shape's size.
 */
public class Scale extends AbstractMotion {

  private final double xFactor;
  private final double yFactor;
  private double originalWidth;
  private double originalHeight;


  /**
   * Constructs a {@code Scale} object.
   *
   * @param tStart  the start tick of the scale
   * @param tEnd    the end tick of the scale
   * @param xFactor the factor to scale width by
   * @param yFactor the factor to scale the height
   * @throws IllegalArgumentException if either scale factor is non-positive
   */
  public Scale(int tStart, int tEnd,
      double xFactor, double yFactor) throws IllegalArgumentException {
    super(tStart, tEnd);
    this.xFactor = xFactor;
    this.yFactor = yFactor;
    this.originalWidth = 1;
    this.originalHeight = 1;
  }

  @Override
  public void changeShape(IShape shape, int tick) throws IllegalArgumentException {
    if (!(tStart <= tick && tEnd >= tick)) {
      throw new IllegalArgumentException("Inputted tick does not correspond to this motion.");
    }
    this.originalWidth = shape.getWidth();
    this.originalHeight = shape.getHeight();
    double finalWidth = this.originalWidth * this.xFactor;
    double finalHeight = this.originalHeight * this.yFactor;

    // Determines amount of ticks after the start of the move
    double interval = this.tEnd - this.tStart;
    int tickDif = tick - this.tStart;

    // Values to represent x and y growth per tick
    double xgpt = tickDif * (finalWidth - this.originalWidth) / interval;
    double ygpt = tickDif * (finalHeight - this.originalHeight) / interval;

    shape.setWidth(shape.getWidth() + xgpt);
    shape.setHeight(shape.getHeight() + ygpt);
  }

  @Override
  public boolean isNotCompatible(Motion m) {
    if (!(m instanceof Scale)) {
      return false;
    } else {
      AbstractMotion a = (AbstractMotion) m;
      return (((a.tStart >= this.tStart) && (a.tStart < this.tEnd))
          || ((a.tEnd > this.tStart) && (a.tEnd <= this.tEnd)));
    }
  }

  @Override
  public String accept(MotionVisitor<IShape, Motion> v, IShape shape, String initial) {
    return v.visitScale(this, shape, initial);
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof Scale)) {
      return false;
    }
    Scale a = (Scale) that;

    return ((this.tStart == a.tStart)
        && (this.tEnd == a.tEnd)
        && (this.xFactor == a.xFactor)
        && (this.yFactor == a.yFactor)
        && (this.originalWidth == a.originalWidth)
        && (this.originalHeight == a.originalHeight));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.tStart, this.tEnd, this.xFactor, this.yFactor, this.originalWidth,
        this.originalHeight);
  }


}
