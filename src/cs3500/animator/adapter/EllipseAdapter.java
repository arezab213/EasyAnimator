package cs3500.animator.adapter;

import cs3500.animator.model.shapes.AnimatorEllipse;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.provider.model.shapes.ShapeVisitor;
import cs3500.animator.provider.model.shapes.VisitableShape;

/**
 * Represents an ellipse with all the functionalities of an {@code AnimatorEllipse} with the
 * addition of the {@code VisitableShape} accept method.
 */
public class EllipseAdapter extends AnimatorEllipse
    implements VisitableShape<RectangleAdapter, EllipseAdapter> {

  /**
   * Constructs a {@code EllipseAdapter} object with the same specifications as an {@code IShape}.
   *
   * @param shape The shape the this {@code EllipseAdapter} will adapt.
   */
  public EllipseAdapter(IShape shape) {
    super(shape.getName(), shape.getWidth(), shape.getHeight(), shape.getColor(),
        shape.getPosition(), shape.getVisibility(), shape.getVisibleStartTick());
    this.motionList = shape.getMotions();
  }

  @Override
  public void accept(ShapeVisitor<RectangleAdapter, EllipseAdapter> visitor) throws Exception {
    visitor.visitEllipse(this);
  }
}
