package cs3500.animator.adapter;

import cs3500.animator.model.Position;
import cs3500.animator.provider.view.renderers.VisualShapeRenderer;
import cs3500.animator.view.visitors.ShapeVisitor;
import cs3500.animator.view.visitors.ShapeVisitorImpl;
import java.awt.Graphics2D;

/**
 * Class to render shapes of the animation for the visual view.
 */
public class VisualShapeRendererImpl
    implements VisualShapeRenderer<RectangleAdapter, EllipseAdapter> {

  private int currentTick;
  private final ShapeVisitor shapeVisitor;
  private Graphics2D output;

  /**
   * Contructs a {@code VisualShapeRendererImpl} and sets the tick value to 0.
   */
  public VisualShapeRendererImpl() {
    this.currentTick = 0;
    this.shapeVisitor = new ShapeVisitorImpl();
  }

  @Override
  public void resetTick() {
    this.currentTick = 0;
  }

  @Override
  public int nextTick() {
    this.currentTick += 1;
    return this.currentTick;
  }

  @Override
  public int getTick() {
    return this.currentTick;
  }

  @Override
  public void setOutput(Graphics2D graphics2D) throws NullPointerException {
    this.output = graphics2D;
  }

  @Override
  public void visitRectangle(RectangleAdapter rectangleAdapter) {
    rectangleAdapter = new RectangleAdapter(rectangleAdapter.getShapeAtTick(currentTick));
    this.shapeVisitor.visitRectangle(rectangleAdapter, this.output,
        new Position(0, 0));
  }

  @Override
  public void visitEllipse(EllipseAdapter ellipseAdapter)  {
    ellipseAdapter = new EllipseAdapter(ellipseAdapter.getShapeAtTick(currentTick));
    this.shapeVisitor.visitEllipse(ellipseAdapter, this.output,
        new Position(0, 0));
  }
}
