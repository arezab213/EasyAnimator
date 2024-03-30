package cs3500.animator.adapter;

import cs3500.animator.model.shapes.AnimatorRectangle;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.provider.model.shapes.ShapeVisitor;
import cs3500.animator.provider.model.shapes.VisitableShape;

/**
 * Represents an rectangle with all the functionalities of an {@code AnimatorRectangle} with the
 * addition of the {@code VisitableShape} accept method.
 */
public class RectangleAdapter extends AnimatorRectangle
    implements VisitableShape<RectangleAdapter, EllipseAdapter> {

  /**
   * Constructs a {@code RectangleAdapter} object with the same specifications as an {@code
   * IShape}.
   *
   * @param shape The shape the this {@code RectangleAdapter} will adapt.
   */
  public RectangleAdapter(IShape shape) {
    super(shape.getName(), shape.getWidth(), shape.getHeight(), shape.getColor(),
        shape.getPosition(), shape.getVisibility(), shape.getVisibleStartTick());
    this.motionList = shape.getMotions();
  }

  @Override
  public void accept(ShapeVisitor<RectangleAdapter, EllipseAdapter> visitor) throws Exception {
    visitor.visitRectangle(this);
  }
}
