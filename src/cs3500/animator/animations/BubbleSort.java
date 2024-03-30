package cs3500.animator.animations;

import cs3500.animator.model.EasyAnimator;
import cs3500.animator.model.Position;
import cs3500.animator.model.motions.Move;
import cs3500.animator.model.shapes.AnimatorEllipse;
import cs3500.animator.model.shapes.IShape;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generates an animation to visually represent the bubble sort algorithm.
 */
public class BubbleSort {

  private final int numCircles;
  private final int largestDiameter;
  private final double diameterDifference;
  private final int spacing;
  private final int totalWidth;
  private final int totalHeight;
  private final double colorIncrement;
  private EasyAnimator model;

  /**
   * Constructs a {@code BubbleSort} object.
   *
   * @param numCircles The number of circles in the animation.
   * @throws IllegalArgumentException if the number of circles is less than 1 or greater than 20
   */
  public BubbleSort(int numCircles) throws IllegalArgumentException {
    this.largestDiameter = 40;
    int smallestDiameter = 15;
    if (numCircles < 1 || numCircles > largestDiameter - smallestDiameter) {
      throw new IllegalArgumentException("Invalid number of circles to sort.");
    }
    this.numCircles = numCircles;
    this.spacing = 5;
    this.diameterDifference = (double) (largestDiameter - smallestDiameter) / (numCircles - 1);
    this.totalHeight = 100;
    this.totalWidth = (numCircles * largestDiameter) + (spacing * (numCircles + 1));
    this.colorIncrement = (255.0 / numCircles);

    this.bubble();
  }

  /**
   * Constructs a list of circles in decreasing order of their size and assigns the list to the
   * {@code EasyAnimator} model field.
   */
  public void bubble() {

    List<IShape> circles = new ArrayList<>();
    for (int i = 0; i < numCircles; i++) {
      circles.add(new AnimatorEllipse("C" + i,
          largestDiameter - (diameterDifference * i),
          largestDiameter - (diameterDifference * i),
          new Color(0, 255, (int) (255 - i * colorIncrement)),
          new Position(spacing + (largestDiameter + spacing) * i,
              (double) totalHeight / 2), true));
    }

    Collections.shuffle(circles);
    for (int i = 0; i < numCircles; i++) {
      circles.get(i).setPosition(new Position(spacing + (largestDiameter + spacing) * i,
          (double) totalHeight / 4));
    }

    int startTick = 0;
    int endTick = 10;
    for (int i = 0; i < circles.size() - 1; i++) {
      for (int j = 0; j < circles.size() - i - 1; j++) {
        if (circles.get(j).getWidth() > circles.get(j + 1).getWidth()) {
          swapCircles(circles.get(j), circles.get(j + 1), startTick, endTick);
          Collections.swap(circles, j, j + 1);
          startTick += 10;
          endTick += 10;
        }
      }
    }

    for (IShape circle : circles) {
      if (circle.getMotions().size() == 0) {
        circle.addMotionToShape(new Move(1, 1, circle.getPosition()));
      }
    }
    this.model = new EasyAnimator(circles, 0, 0, totalWidth, totalHeight);
  }

  /**
   * Adds motions to two shapes in order to swap their positions.
   *
   * @param s1    The first shape in the swap
   * @param s2    The second shape in the swap
   * @param start The start tick of the swap
   * @param end   The end tick of the swap
   */
  public void swapCircles(IShape s1, IShape s2, int start, int end) {
    s1.addMotionToShape(new Move(start, end, s2.getShapeAtTick(start).getPosition()));
    s2.addMotionToShape(new Move(start, end, s1.getShapeAtTick(start).getPosition()));
  }

  /**
   * Generates a string output to represent the animation.
   *
   * @return the string representation of the animatiion
   */
  public String describeBubble() {
    StringBuilder modelAcc = new StringBuilder();
    modelAcc.append("canvas 0 0 ").append(this.totalWidth).append(" ").append(this.totalHeight)
        .append("\n\n");
    for (IShape shape : this.model.getShapes(0)) {
      modelAcc.append(shape.toString()).append("\n");
    }
    return modelAcc.toString();
  }
}
