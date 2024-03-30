package cs3500.animator.model.shapes;

import cs3500.animator.model.Position;
import cs3500.animator.model.motions.Motion;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract shape.
 */
public abstract class AbstractShape implements IShape {

  protected String name;
  protected String shapeType;
  protected double width;
  protected double height;
  protected Color color;
  protected Position pos;
  protected boolean isVisible;
  protected int visibleStartTick;
  protected List<Motion> motionList;


  /**
   * Constructs an {@code AbstractShape} object.
   *
   * @param name             the name of the shape
   * @param width            the width of the shape
   * @param height           the height of the shape
   * @param color            the {@code Color} of the shape
   * @param pos              the {@code Position} of the shape's center
   * @param isVisible        whether or not the shape is visible
   * @param visibleStartTick the tick at which the shape starts to be visible
   * @throws IllegalArgumentException if the width or height are not positive.
   */
  public AbstractShape(String name, double width, double height, Color color, Position pos,
      boolean isVisible, int visibleStartTick)
      throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive");
    }
    this.name = name;
    this.width = width;
    this.height = height;
    this.color = color;
    this.pos = pos;
    this.isVisible = isVisible;
    this.visibleStartTick = visibleStartTick;
    this.motionList = new ArrayList<>();
  }

  /**
   * Constructs an {@code AbstractShape} object. Does not specify the starting visibility tick for
   * testing purposes.
   *
   * @param name      the name of the shape
   * @param width     the width of the shape
   * @param height    the height of the shape
   * @param color     the {@code Color} of the shape
   * @param pos       the {@code Position} of the shape's center
   * @param isVisible whether or not the shape is visible
   * @throws IllegalArgumentException if the width or height are not positive.
   */
  public AbstractShape(String name, double width, double height, Color color, Position pos,
      boolean isVisible)
      throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive");
    }
    this.name = name;
    this.width = width;
    this.height = height;
    this.color = color;
    this.pos = pos;
    this.isVisible = isVisible;
    this.visibleStartTick = -1;
    this.motionList = new ArrayList<>();
  }

  /**
   * Constructs an {@code AbstractShape} with default field values.
   *
   * @param name the name to be assigned to the shape.
   */
  public AbstractShape(String name) {
    this.name = name;
    this.width = 100;
    this.height = 100;
    this.color = Color.BLACK;
    this.pos = new Position(0, 0);
    this.isVisible = false;
    this.visibleStartTick = -1;
    this.motionList = new ArrayList<>();
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
  public void removeMotionFromShape(Motion m) {
    this.motionList.remove(m);
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public void setWidth(double value) throws IllegalArgumentException {
    this.width = value;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public void setHeight(double value) throws IllegalArgumentException {
    this.height = value;
  }

  @Override
  public void setVisible() {
    this.isVisible = true;
  }

  @Override
  public boolean getVisibility() {
    return this.isVisible;
  }

  @Override
  public int getVisibleStartTick() {
    return this.visibleStartTick;
  }

  @Override
  public void setVisibleStartTick(int tick) {
    this.visibleStartTick = tick;
  }

  @Override
  public Color getColor() {
    return new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue());
  }

  @Override
  public void setColor(Color c) throws IllegalArgumentException {
    this.color = c;
  }

  @Override
  public Position getPosition() {
    return new Position(this.pos.getX(), this.pos.getY());
  }

  @Override
  public void setPosition(Position pos) {
    this.pos = pos;
  }

  @Override
  public List<Motion> getMotions() {
    return new ArrayList<>(this.motionList);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getType() {
    return this.shapeType;
  }

  @Override
  public String toString() {
    StringBuilder acc = new StringBuilder("shape " + this.name + " " + this.shapeType + "\n");
    if (this.motionList.isEmpty()) {
      return acc.toString();
    }

    // Creates list of ticks where no motion occurs
    List<Motion> motionsSorted = this.sortMotions();
    List<Integer> stationaryTicks = new ArrayList<>();
    for (int i = 1; i <= this.getMaxTick() - 1; i++) {
      if (!this.isMotionHappening(i)) {
        stationaryTicks.add(i);
      }
    }

    // Outputs string
    int stationaryIndex = 0;
    int counterLastMotion = 0;
    for (int i = 0; i <= this.motionList.size() + counterLastMotion - 1; i++) {
      int motionIndex = i - counterLastMotion;
      if (stationaryIndex >= stationaryTicks.size()
          || (motionsSorted.get(motionIndex).getStartTick()
          <= stationaryTicks.get(stationaryIndex))) {
        Motion motion = motionsSorted.get(motionIndex);
        motion.changeShape(this, motion.getStartTick());
        acc.append("motion ").append(this.name).append(" ").append(motion.getStartTick())
            .append(" ").append(Math.round(this.pos.getX())).append(" ")
            .append(Math.round(this.pos.getY())).append(" ").append(Math.round(this.width))
            .append(" ").append(Math.round(this.height)).append(" ").append(this.color.getRed())
            .append(" ").append(this.color.getGreen()).append(" ").append(this.color.getBlue())
            .append("   ");
        motion.changeShape(this, motion.getEndTick());
        acc.append(motion.getEndTick()).append(" ").append(Math.round(this.pos.getX())).append(" ")
            .append(Math.round(this.pos.getY())).append(" ").append(Math.round(this.width))
            .append(" ").append(Math
            .round(this.height)).append(" ").append(this.color.getRed()).append(" ")
            .append(this.color.getGreen()).append(" ").append(this.color.getBlue()).append("\n");

      } else {
        int stationaryStart = stationaryTicks.get(stationaryIndex);
        while ((stationaryTicks.size() > stationaryIndex + 1)
            && stationaryTicks.get(stationaryIndex)
            == stationaryTicks.get(stationaryIndex + 1) - 1) {
          stationaryIndex++;
        }
        int stationaryEnd = stationaryTicks.get(stationaryIndex) + 1;

        acc.append("motion ").append(this.name).append(" ").append(stationaryStart).append(" ")
            .append(Math.round(this.pos.getX())).append(" ").append(Math.round(this.pos.getY()))
            .append(" ").append(Math.round(this.width)).append(" ").append(Math.round(this.height))
            .append(" ").append(this.color.getRed()).append(" ").append(this.color.getGreen())
            .append(" ").append(this.color.getBlue()).append("   ");
        acc.append(stationaryEnd).append(" ").append(Math.round(this.pos.getX())).append(" ")
            .append(Math.round(this.pos.getY())).append(" ").append(Math.round(this.width))
            .append(" ").append(Math
            .round(this.height)).append(" ").append(this.color.getRed()).append(" ")
            .append(this.color.getGreen()).append(" ").append(this.color.getBlue()).append("\n");
        counterLastMotion += 1;

        stationaryIndex++;

      }
    }
    return acc.toString();
  }

  @Override
  public String textOutput(double ratio) {
    StringBuilder acc = new StringBuilder("shape " + this.name + " " + this.shapeType + "\n");
    if (this.motionList.isEmpty()) {
      return acc.toString();
    }

    // Creates list of ticks where no motion occurs
    List<Motion> motionsSorted = this.sortMotions();
    List<Integer> stationaryTicks = new ArrayList<>();
    for (int i = 1; i <= this.getMaxTick() - 1; i++) {
      if (!this.isMotionHappening(i)) {
        stationaryTicks.add(i);
      }
    }

    // Outputs string
    int stationaryIndex = 0;
    int counterLastMotion = 0;
    for (int i = 0; i <= this.motionList.size() + counterLastMotion - 1; i++) {
      int motionIndex = i - counterLastMotion;
      if (stationaryIndex >= stationaryTicks.size()
          || (motionsSorted.get(motionIndex).getStartTick()
          <= stationaryTicks.get(stationaryIndex))) {
        Motion motion = motionsSorted.get(motionIndex);
        motion.changeShape(this, motion.getStartTick());
        acc.append("motion ").append(this.name).append(" ")
            .append(String.format("%.2f", (motion.getStartTick() / ratio))).append(" ")
            .append(Math.round(this.pos.getX())).append(" ").append(Math.round(this.pos.getY()))
            .append(" ").append(Math.round(this.width)).append(" ").append(Math.round(this.height))
            .append(" ").append(this.color.getRed()).append(" ").append(this.color.getGreen())
            .append(" ").append(this.color.getBlue()).append("   ");
        motion.changeShape(this, motion.getEndTick());
        acc.append(String.format("%.2f", (motion.getEndTick() / ratio))).append(" ")
            .append(Math.round(this.pos.getX())).append(" ").append(Math.round(this.pos.getY()))
            .append(" ").append(Math.round(this.width)).append(" ").append(Math
            .round(this.height)).append(" ").append(this.color.getRed()).append(" ")
            .append(this.color.getGreen()).append(" ").append(this.color.getBlue()).append("\n");

      } else {
        int stationaryStart = stationaryTicks.get(stationaryIndex);
        while ((stationaryTicks.size() > stationaryIndex + 1)
            && stationaryTicks.get(stationaryIndex)
            == stationaryTicks.get(stationaryIndex + 1) - 1) {
          stationaryIndex++;
        }
        int stationaryEnd = stationaryTicks.get(stationaryIndex) + 1;

        acc.append("motion ").append(this.name).append(" ")
            .append(String.format("%.2f", (stationaryStart / ratio))).append(" ")
            .append(Math.round(this.pos.getX())).append(" ").append(Math.round(this.pos.getY()))
            .append(" ").append(Math.round(this.width)).append(" ").append(Math.round(this.height))
            .append(" ").append(this.color.getRed()).append(" ").append(this.color.getGreen())
            .append(" ").append(this.color.getBlue()).append("   ");
        acc.append(String.format("%.2f", (stationaryEnd / ratio))).append(" ")
            .append(Math.round(this.pos.getX())).append(" ").append(Math.round(this.pos.getY()))
            .append(" ").append(Math.round(this.width)).append(" ").append(Math
            .round(this.height)).append(" ").append(this.color.getRed()).append(" ")
            .append(this.color.getGreen()).append(" ").append(this.color.getBlue()).append("\n");
        counterLastMotion += 1;

        stationaryIndex++;

      }
    }
    return acc.toString();
  }


  /**
   * Gets the maximum tick value for all the motions of the shape.
   *
   * @return the end tick of the last chronological motion for the shape
   */
  private int getMaxTick() {
    int maxValue = 0;
    for (Motion motion : this.motionList) {
      if (motion.getEndTick() > maxValue) {
        maxValue = motion.getEndTick();
      }
    }
    return maxValue;
  }

  /**
   * Gets the motion with the minimum start tick value for all the motions of the shape.
   *
   * @return the motion with the first chronological start tick for the shape
   */
  private Motion getMotionWithMinStart(List<Motion> motions) {
    int minValue = this.getMaxTick();
    Motion currentMin = motions.get(0);
    for (Motion motion : motions) {
      if (motion.getStartTick() < minValue) {
        minValue = motion.getStartTick();
        currentMin = motion;
      }
    }
    return currentMin;
  }

  /**
   * Sorts a list of motions in chronological order.
   *
   * @return a sorted copy of the list of motions.
   */
  protected List<Motion> sortMotions() {
    List<Motion> motionsCopy = new ArrayList<>(this.motionList);
    List<Motion> sortedAcc = new ArrayList<>();
    for (int i = 0; i < this.motionList.size(); i++) {
      Motion currentMin = this.getMotionWithMinStart(motionsCopy);
      motionsCopy.remove(currentMin);
      sortedAcc.add(currentMin);
    }
    return sortedAcc;
  }

  /**
   * Determines if the shape has any motion that occurs at the given tick.
   *
   * @return True if there is a motion occurring, otherwise false.
   */
  private boolean isMotionHappening(int tick) {
    for (Motion motion : this.motionList) {
      if (tick >= motion.getStartTick() && tick < motion.getEndTick()) {
        return true;
      }
    }
    return false;
  }
}
