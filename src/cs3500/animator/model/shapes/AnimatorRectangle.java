package cs3500.animator.model.shapes;

import cs3500.animator.model.Position;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.view.visitors.ShapeVisitor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

/**
 * Represents a rectangle.
 */
public class AnimatorRectangle extends AbstractShape {

  /**
   * Constructs a {@code AnimatorRectangle} object.
   *
   * @param width            the width of the rectangle
   * @param height           the height of the rectangle
   * @param color            the {@code Color} of the rectangle
   * @param pos              the {@code Position} of the rectangle
   * @param isVisible        whether or not the rectangle is visible
   * @param visibleStartTick the tick at which the shape starts to be visible
   */
  public AnimatorRectangle(String name, double width, double height, Color color, Position pos,
      boolean isVisible, int visibleStartTick)
      throws IllegalArgumentException {
    super(name, width, height, color, pos, isVisible, visibleStartTick);
    this.shapeType = "rectangle";
  }

  /**
   * Constructs a {@code AnimatorRectangle} object. Does not specify the visibleStartTick for
   * testing purposes.
   *
   * @param width     the width of the rectangle
   * @param height    the height of the rectangle
   * @param color     the {@code Color} of the rectangle
   * @param pos       the {@code Position} of the rectangle
   * @param isVisible whether or not the rectangle is visible
   */
  public AnimatorRectangle(String name, double width, double height, Color color, Position pos,
      boolean isVisible)
      throws IllegalArgumentException {
    super(name, width, height, color, pos, isVisible);
    this.shapeType = "rectangle";
  }

  /**
   * Constructs an {@code AnimatorRectangle} with default field values.
   *
   * @param name the name to be assigned to the rectangle.
   */
  public AnimatorRectangle(String name) {
    super(name);
    this.shapeType = "rectangle";
  }

  @Override
  public IShape getShapeAtTick(int tick) {
    IShape copyShape = new AnimatorRectangle(this.name, this.getWidth(), this.getHeight(),
        this.getColor(), this.getPosition(), this.isVisible, this.visibleStartTick);

    for (Motion motion : this.sortMotions()) {
      if (motion.getEndTick() < tick) {
        motion.changeShape(copyShape, motion.getEndTick());
      } else if (motion.timeContains(tick) || motion.getEndTick() == tick) {
        motion.changeShape(copyShape, tick);
      }
    }
    for (Motion motion : this.motionList) {
      copyShape.addMotionToShape(motion);
    }
    if (copyShape.getVisibleStartTick() <= tick) {
      copyShape.setVisible();
    }
    return copyShape;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof AnimatorRectangle)) {
      return false;
    }
    AnimatorRectangle a = (AnimatorRectangle) that;

    boolean firstParams = ((this.name.equals(a.name))
        && (this.shapeType.equals(a.shapeType))
        && (Math.abs(this.width - a.width) < 0.01)
        && (Math.abs(this.height - a.height) < 0.01)
        && (this.color.equals(a.color))
        && (this.pos.equals(a.pos))
        && (this.isVisible == a.isVisible));

    boolean motions;
    if (this.motionList.size() != a.motionList.size()) {
      motions = false;
    } else {
      motions = true;
      for (int i = 0; i < this.motionList.size(); i++) {
        if (!this.motionList.get(i).equals(a.motionList.get(i))) {
          motions = false;
          break;
        }
      }
    }
    return firstParams && motions;
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(this.name, this.shapeType, this.getWidth(), this.height, this.color, this.pos,
            this.isVisible, this.motionList);
  }

  @Override
  public void accept(ShapeVisitor<IShape> v, Graphics2D g, Position minPos) {
    v.visitRectangle(this, g, minPos);
  }
}
