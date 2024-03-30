package cs3500.animator.model.motions;

import cs3500.animator.model.Position;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.view.visitors.MotionVisitor;
import java.util.Objects;


/**
 * Represents a shape's movement to a new position.
 */
public class Move extends AbstractMotion {

  private final Position finalPos;
  private Position originalPos;

  /**
   * Constructs a {@code Move} object.
   *
   * @param tStart   the tick of the start of the move
   * @param tEnd     the tick of the end of the move
   * @param finalPos the final position of the shape after moving
   */
  public Move(int tStart, int tEnd, Position finalPos) {
    super(tStart, tEnd);
    this.originalPos = new Position(0, 0);
    this.finalPos = finalPos;
  }

  @Override
  public void changeShape(IShape shape, int tick) throws IllegalArgumentException {
    if (!(tStart <= tick && tEnd >= tick)) {
      throw new IllegalArgumentException("Inputted tick does not correspond to this motion.");
    }
    this.originalPos = shape.getPosition();
    // Determines the distance to be moved per tick in the x and y directions.
    double dxpt = ((this.finalPos.getX() - this.originalPos.getX()) / (this.tEnd - this.tStart));
    double dypt = ((this.finalPos.getY() - this.originalPos.getY()) / (this.tEnd - this.tStart));

    // Determines amount of ticks after the start of the move
    int tickDif = tick - this.tStart;

    // Determines the change in x and change in y for the tick difference
    double dx = tickDif * dxpt;
    double dy = tickDif * dypt;

    // updates the shape's location
    shape.setPosition(new Position(originalPos.getX() + dx, originalPos.getY() + dy));
  }

  @Override
  public boolean isNotCompatible(Motion m) {
    if (!(m instanceof Move)) {
      return false;
    } else {
      AbstractMotion a = (AbstractMotion) m;
      return (((a.tStart >= this.tStart) && (a.tStart < this.tEnd))
          || ((a.tEnd > this.tStart) && (a.tEnd <= this.tEnd)));
    }
  }

  @Override
  public String accept(MotionVisitor<IShape, Motion> v, IShape shape, String initial) {
    return v.visitMove(this, shape, initial);
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof Move)) {
      return false;
    }
    Move a = (Move) that;

    return ((this.tStart == a.tStart)
        && (this.tEnd == a.tEnd)
        && (this.originalPos.equals(a.originalPos))
        && (this.finalPos.equals(a.finalPos)));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.tStart, this.tEnd, this.originalPos, this.finalPos);
  }
}
