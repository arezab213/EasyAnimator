package cs3500.animator.model;

import cs3500.animator.model.motions.ChangeColor;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.Move;
import cs3500.animator.model.motions.Scale;
import cs3500.animator.model.shapes.AnimatorEllipse;
import cs3500.animator.model.shapes.AnimatorPlus;
import cs3500.animator.model.shapes.AnimatorRectangle;
import cs3500.animator.model.shapes.IShape;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents an implementation of the {@code AnimatorModel}.
 */
public class EasyAnimator implements AnimatorModel<IShape, Motion> {

  protected final List<IShape> shapes;
  protected final List<SpeedInterval> intervals;
  protected int minX;
  protected int minY;
  protected int width;
  protected int height;

  // INVARIANT: There exists no overlapping motions of the same type for any shape within the model.

  // This invariant is ensured as all methods involving the addition of a motion utilize
  // a Motion method called isNotCompatible(), which correctly evaluates whether or not
  // two motions would be overlapping. Since the only way a motion can be added to a shape
  // or model implementation is if isNotCompatible() returns false, then the invariant
  // is properly maintained and enforced throughout the implementation.

  /**
   * Constructs an {@code EasyAnimator} object.
   *
   * @param shapes the shapes in the animation
   */
  public EasyAnimator(List<IShape> shapes) {
    this.shapes = shapes;
    this.minX = 0;
    this.minY = 0;
    this.width = 500;
    this.height = 500;
    this.intervals = new ArrayList<>();
  }

  /**
   * Convenience constructor for an {@code EasyAnimator} object with default values for all fields.
   */
  public EasyAnimator() {
    this.shapes = new ArrayList<>();
    this.minX = 0;
    this.minY = 0;
    this.width = 0;
    this.height = 0;
    this.intervals = new ArrayList<>();
  }

  /**
   * Constructs an {@code EasyAnimator} object.
   *
   * @param shapes The shapes of the animation
   * @param minX   The minimum x coordinate of the model
   * @param minY   The minimum y coordinate of the model
   * @param width  The width of the model
   * @param height The height of the model
   * @throws IllegalArgumentException if the width or height is negative.
   */
  public EasyAnimator(List<IShape> shapes, int minX, int minY, int width, int height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("model width and height must be positive");
    }
    this.shapes = shapes;
    this.minX = minX;
    this.minY = minY;
    this.width = width;
    this.height = height;
    this.intervals = new ArrayList<>();
  }

  @Override
  public List<IShape> getShapes(int time) {
    List<IShape> resultingShapes = new ArrayList<>();
    for (IShape shape : this.shapes) {
      resultingShapes.add(shape.getShapeAtTick(time));
    }
    return resultingShapes;
  }

  @Override
  public String describe(double ratio) {
    StringBuilder modelAcc = new StringBuilder();
    for (IShape shape : this.shapes) {
      modelAcc.append(shape.textOutput(ratio)).append("\n");
    }
    return modelAcc.toString();
  }

  @Override
  public void addShape(IShape shape) {
    this.shapes.add(shape);
  }

  @Override
  public void removeShape(IShape shape) {
    this.shapes.remove(shape);
  }

  @Override
  public void addMotionToModel(int index, Motion motion)
      throws IllegalArgumentException {
    this.checkInvalidIndex(index);
    this.shapes.get(index).addMotionToShape(motion);
  }

  @Override
  public void removeMotionFromModel(int index, Motion motion) throws IllegalArgumentException {
    this.checkInvalidIndex(index);
    if (this.shapes.get(index).getMotions().contains(motion)) {
      this.shapes.get(index).removeMotionFromShape(motion);
    } else {
      throw new IllegalArgumentException("Motion does not exist for the given shape.");
    }
  }

  @Override
  public void addSpeedInterval(int startTick, int endTick, int speed) {
    SpeedInterval currentInterval = new SpeedInterval(startTick, endTick, speed);
    for (SpeedInterval interval : this.intervals) {
      if (startTick >= interval.getStartTick() && startTick <= interval.getEndTick()
          || endTick >= interval.getStartTick() && endTick <= interval.getEndTick()) {
        throw new IllegalArgumentException("Speed intervals must not overlap");
      }
    }
    this.intervals.add(currentInterval);
  }

  @Override
  public List<Motion> getMotions(int index) throws IllegalArgumentException {
    this.checkInvalidIndex(index);
    return new ArrayList<>(this.shapes.get(index).getMotions());
  }

  @Override
  public IShape getShape(int index, int tick) throws IllegalArgumentException {
    this.checkInvalidIndex(index);
    return this.shapes.get(index).getShapeAtTick(tick);
  }


  /**
   * Checks if a given int is a valid index in the model's list of shapes.
   *
   * @param index The index for the list of shapes
   * @throws IllegalArgumentException if the index is invalid
   */
  private void checkInvalidIndex(int index) throws IllegalArgumentException {
    if (index > this.shapes.size() - 1) {
      throw new IllegalArgumentException("Invalid index");
    }
  }

  @Override
  public Position getMinPos() {
    return new Position(this.minX, this.minY);
  }

  @Override
  public Position getMaxPos() {
    return new Position(this.minX + width, this.minY + height);
  }

  @Override
  public int getFinalTick() {
    int maxTick = 1;
    for (IShape shape : this.shapes) {
      for (Motion motion : shape.getMotions()) {
        if (motion.getEndTick() > maxTick) {
          maxTick = motion.getEndTick();
        }
      }
    }
    return maxTick;
  }

  @Override
  public int getNextDiscreteTick(int currentTick) {
    int nextTick = this.getFinalTick();
    for (IShape shape : this.shapes) {
      for (Motion motion : shape.getMotions()) {
        if (motion.getStartTick() > currentTick && motion.getStartTick() < nextTick) {
          nextTick = motion.getStartTick();
        }
        if (motion.getEndTick() > currentTick && motion.getEndTick() < nextTick) {
          nextTick = motion.getEndTick();
        }
      }
    }
    return nextTick;
  }

  @Override
  public List<SpeedInterval> getSpeedIntervals() {
    List<SpeedInterval> resultingIntervals = new ArrayList<>();
    for (SpeedInterval interval : this.intervals) {
      resultingIntervals.add(new SpeedInterval(interval.getStartTick(),
          interval.getEndTick(), interval.getSpeed()));
    }
    return resultingIntervals;
  }

  /**
   * Class to build a new {@code AnimationModel}.
   */
  public static final class Builder implements AnimationBuilder<AnimatorModel<IShape, Motion>> {

    EasyAnimator model;

    /**
     * Constructs a new {@code Builder}.
     */
    public Builder() {
      this.model = new EasyAnimator();
    }

    @Override
    public AnimatorModel<IShape, Motion> build() {
      return this.model;
    }

    @Override
    public AnimationBuilder<AnimatorModel<IShape, Motion>> setBounds(int x, int y,
        int width, int height) {
      this.model.minX = x;
      this.model.minY = y;
      this.model.width = width;
      this.model.height = height;
      return this;
    }

    @Override
    public AnimationBuilder<AnimatorModel<IShape, Motion>> declareShape(String name, String type) {

      IShape generatedShape;
      if (type.equals("rectangle")) {
        generatedShape = new AnimatorRectangle(name);
      } else if (type.equals("ellipse")) {
        generatedShape = new AnimatorEllipse(name);
      } else if (type.equals("plus")) {
        generatedShape = new AnimatorPlus(name);
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
    public AnimationBuilder<AnimatorModel<IShape, Motion>> addMotion(String name, int t1,
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
    public AnimationBuilder<AnimatorModel<IShape, Motion>> declareSlowMo(int startTick, int endTick,
        int speed) {
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
