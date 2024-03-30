package cs3500.animator.model;

import java.util.Objects;

/**
 * Represents a position in the x y coordinate plane.
 */
public class Position {

  private double x;
  private double y;

  /**
   * Constructs a {@code Position} object.
   *
   * @param x the x coordinate
   * @param y th y coordinate
   */
  public Position(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the x coordinate of the {@code Position}.
   *
   * @return the x coordinate
   */
  public double getX() {
    return this.x;
  }

  /**
   * Sets the x coordinate to a given value.
   *
   * @param x the new x coordinate
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Returns the y coordinate of the {@code Position}.
   *
   * @return the y coordiante
   */
  public double getY() {
    return this.y;
  }

  /**
   * Sets the y coordinate to a given value.
   *
   * @param y the new y coordinate
   */
  public void setY(double y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof Position)) {
      return false;
    }
    Position a = (Position) that;

    return ((Math.abs(this.x - a.x) < 0.01)
        && (Math.abs(this.y - a.y) < 0.01));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
