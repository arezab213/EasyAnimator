package cs3500.animator.adapter;

import cs3500.animator.model.AnimationBuilder;
import cs3500.animator.model.EasyAnimator;
import cs3500.animator.model.Position;
import cs3500.animator.model.motions.ChangeColor;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.Move;
import cs3500.animator.model.motions.Scale;
import cs3500.animator.model.shapes.AnimatorEllipse;
import cs3500.animator.model.shapes.AnimatorRectangle;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.provider.model.EasyAnimatorImmutableModel;
import cs3500.animator.provider.model.shapes.VisitableShape;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an adapter to convert from the original model implementation to the implementation
 * provided in the provider package.
 */
public class AnimatorModelAdapter extends EasyAnimator
    implements EasyAnimatorImmutableModel<VisitableShape<RectangleAdapter, EllipseAdapter>> {

  /**
   * Constructs an {@code AnimatorModelAdapter} object.
   */
  public AnimatorModelAdapter() {
    super();
  }

  @Override
  public int getLeftmostX() {
    return this.minX;
  }

  @Override
  public int getTopmostY() {
    return this.minY;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getNumTicks() throws IllegalStateException {
    return this.getFinalTick();
  }

  @Override
  public List<VisitableShape<RectangleAdapter, EllipseAdapter>> getShapes() {
    List<VisitableShape<RectangleAdapter, EllipseAdapter>> resultingShapes = new ArrayList<>();
    for (IShape shape : this.shapes) {
      VisitableShape<RectangleAdapter, EllipseAdapter> providerShape;
      if (shape.getType().equals("rectangle")) {
        providerShape = new RectangleAdapter(shape);
      } else {
        providerShape = new EllipseAdapter(shape);
      }
      resultingShapes.add(providerShape);
    }
    return resultingShapes;
  }

  @Override
  public String toString() {
    StringBuilder modelAcc = new StringBuilder();
    for (IShape shape : this.shapes) {
      modelAcc.append(shape.toString()).append("\n");
    }
    return modelAcc.toString();
  }

  /**
   * Class to build a new {@code AnimationModel}.
   */
  public static final class Builder implements
      AnimationBuilder<EasyAnimatorImmutableModel<VisitableShape<RectangleAdapter,
          EllipseAdapter>>> {

    AnimatorModelAdapter model;

    /**
     * Constructs a new {@code Builder}.
     */
    public Builder() {
      this.model = new AnimatorModelAdapter();
    }

    @Override
    public EasyAnimatorImmutableModel<VisitableShape<RectangleAdapter, EllipseAdapter>> build() {
      return this.model;
    }

    @Override
    public AnimationBuilder<EasyAnimatorImmutableModel<VisitableShape<RectangleAdapter,
        EllipseAdapter>>> setBounds(int x, int y,
        int width, int height) {
      this.model.minX = x;
      this.model.minY = y;
      this.model.width = width;
      this.model.height = height;
      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorImmutableModel<VisitableShape<RectangleAdapter,
        EllipseAdapter>>> declareShape(String name, String type) {

      IShape generatedShape;
      if (type.equals("rectangle")) {
        generatedShape = new AnimatorRectangle(name);
      } else if (type.equals("ellipse")) {
        generatedShape = new AnimatorEllipse(name);
      } else {
        throw new IllegalArgumentException("Shape type not supported.");
      }
      for (IShape shape : model.shapes) {
        if (shape.getName().equals(name)) {
          throw new IllegalArgumentException(
              "There currently already exists a shape with the given name.");
        }
      }
      model.addShape(generatedShape);
      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorImmutableModel<VisitableShape<RectangleAdapter,
        EllipseAdapter>>> addMotion(String name, int t1,
        int x1, int y1, int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2,
        int w2, int h2, int r2, int g2, int b2) {
      boolean shapeInModel = false;
      boolean isDefault = false;
      IShape currentShape = null;
      for (IShape shape : model.shapes) {
        if (shape.getName().equals(name)) {
          shapeInModel = true;
          currentShape = shape;
          if (shape.getMotions().isEmpty()) {
            isDefault = true;
          }
          break;
        }
      }
      if (!shapeInModel) {
        throw new IllegalArgumentException("Shape with the given name does not exist in the model");
      }
      this.determineMotion(currentShape, isDefault, t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2,
          h2, r2,
          g2, b2);

      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorImmutableModel<VisitableShape<RectangleAdapter,
        EllipseAdapter>>> declareSlowMo(
        int startTick, int endTick, int speed) {
      this.model.addSpeedInterval(startTick, endTick, speed);
      return this;
    }

    /**
     * Determines what motion would correspond to the given integer arguments, if at all, and adds
     * the motion(s) to the shape at the given index in the model.
     *
     * @param t1 The start time of this transformation
     * @param x1 The initial x-position of the shape
     * @param y1 The initial y-position of the shape
     * @param w1 The initial width of the shape
     * @param h1 The initial height of the shape
     * @param r1 The initial red color-value of the shape
     * @param g1 The initial green color-value of the shape
     * @param b1 The initial blue color-value of the shape
     * @param t2 The end time of this transformation
     * @param x2 The final x-position of the shape
     * @param y2 The final y-position of the shape
     * @param w2 The final width of the shape
     * @param h2 The final height of the shape
     * @param r2 The final red color-value of the shape
     * @param g2 The final green color-value of the shape
     * @param b2 The final blue color-value of the shape
     */
    private void determineMotion(IShape shape, boolean isDefault, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1,
        int b1,
        int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
      boolean posChange = false;
      boolean sizeChange = false;
      boolean colorChange = false;

      if (isDefault) {
        shape.setPosition(new Position(x1, y1));
        shape.setColor(new Color(r1, g1, b1));
        shape.setWidth(w1);
        shape.setHeight(h1);
      }

      if (shape.getVisibleStartTick() != -1) {
        if (t1 < shape.getVisibleStartTick()) {
          shape.setVisibleStartTick(t1);
        }
      } else {
        shape.setVisibleStartTick(t1);
      }

      if (x1 != x2 || y1 != y2) {
        posChange = true;
      }
      if ((w1 - w2 != 0) || (h1 - h2 != 0)) {
        sizeChange = true;
      }
      if (r1 != r2 || g1 != g2 || b1 != b2) {
        colorChange = true;
      }

      if (posChange) {
        Motion motion;
        motion = new Move(t1, t2, new Position(x2, y2));
        shape.addMotionToShape(motion);
      }
      if (sizeChange) {
        Motion motion;
        motion = new Scale(t1, t2, ((double) w2 / (double) w1), ((double) h2 / (double) h1));
        shape.addMotionToShape(motion);
      }
      if (colorChange) {
        Motion motion;
        motion = new ChangeColor(t1, t2, new Color(r2, g2, b2));
        shape.addMotionToShape(motion);
      }
    }
  }
}
