package cs3500.animator.model.shapes;

import cs3500.animator.model.Position;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.view.visitors.ShapeVisitor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

/**
 * Represents the shape of a plus sign.
 */
public class AnimatorPlus extends AbstractShape {

  /**
   * Constructs a {@code AnimatorPlus} object.
   *
   * @param width            the width of the plus sign
   * @param height           the height of the plus sign
   * @param color            the {@code Color} of the plus sign
   * @param pos              the {@code Position} of the plus sign
   * @param isVisible        whether or not the plus sign is visible
   * @param visibleStartTick the tick at which the shape starts to be visible
   * @throws IllegalArgumentException if the given dimensions of the plus are not square.
   */
  public AnimatorPlus(String name, double width, double height, Color color,
      Position pos, boolean isVisible, int visibleStartTick) throws IllegalArgumentException {
    super(name, width, height, color, pos, isVisible, visibleStartTick);
    if (width != height) {
      throw new IllegalArgumentException("Dimensions must be square.");
    }
    this.shapeType = "plus";
  }

  /**
   * Constructs an {@code AnimatorEllipse} with default field values.
   *
   * @param name the name to be assigned to the ellipse.
   */
  public AnimatorPlus(String name) {
    super(name);
    this.shapeType = "plus";
  }

  @Override
  public IShape getShapeAtTick(int tick) {
    IShape copyShape = new AnimatorPlus(this.name, this.getWidth(), this.getHeight(),
        this.getColor(),
        this.getPosition(), this.isVisible, this.visibleStartTick);
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
  public void addMotionToShape(Motion m) throws IllegalArgumentException {
    for (Motion motion : this.motionList) {
      if (motion.isNotCompatible(m)) {
        throw new IllegalArgumentException("Motions of the same type must not overlap");
      }
    }
    this.motionList.add(m);
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof AnimatorPlus)) {
      return false;
    }
    AnimatorPlus a = (AnimatorPlus) that;

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
    v.visitPlus(this, g, minPos);
  }
}
