package cs3500.animator.model.shapes;

import cs3500.animator.model.Position;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.view.visitors.ShapeVisitor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

/**
 * Represents a shape in an animation.
 */
public interface IShape {

  /**
   * Gets the {@code IShape} in its state at the given tick.
   *
   * @param tick the specified point in time.
   * @return a copy of the shape at the given tick.
   */
  IShape getShapeAtTick(int tick);

  /**
   * Adds a {@code Motion} to the list of motions for the shape.
   *
   * @param m The motion being added to the shape
   * @throws IllegalArgumentException if the motion being added is not compatible with existing
   *                                  motions (if they are the same type and overlap, or if there is
   *                                  an existing {@code Stationary} motion).
   */
  void addMotionToShape(Motion m) throws IllegalArgumentException;

  /**
   * Removes a {@code Motion} to the list of motions for the shape.
   *
   * @param m The motion being removed from the shape
   * @throws IllegalArgumentException if the motion is not part of the shape.
   */
  void removeMotionFromShape(Motion m) throws IllegalArgumentException;

  /**
   * Gets the width of the shape.
   *
   * @return The width of the shape.
   */
  double getWidth();

  /**
   * Sets the width of the {@code AbstractShape} to the given value.
   *
   * @param value the amount to set the width
   */
  void setWidth(double value) throws IllegalArgumentException;

  /**
   * Gets the height of the shape.
   *
   * @return the height of the shape
   */
  double getHeight();

  /**
   * Sets the height of the {@code AbstractShape} to the given value.
   *
   * @param value the amount to set the height
   */
  void setHeight(double value) throws IllegalArgumentException;

  /**
   * Gets the color of the shape.
   *
   * @return the {@code Color} of the shape
   */
  Color getColor();

  /**
   * Changes the color of the {@code Shape}.
   *
   * @param c the new color
   * @throws IllegalArgumentException if any value is less than zero or greater than 255
   */
  void setColor(Color c) throws IllegalArgumentException;

  /**
   * Gets the position of the shape.
   *
   * @return the {@code Position} of the shape
   */
  Position getPosition();

  /**
   * Sets the position of the {@code Shape} to the given position.
   *
   * @param pos the new position for the shape
   */
  void setPosition(Position pos);

  /**
   * Sets the visibility field of the shape to true.
   */
  void setVisible();

  /**
   * Gets the visibility status of the shape.
   *
   * @return false if not visible, true if visible
   */
  boolean getVisibility();

  /**
   * Gets the tick value of when the shape is visible.
   *
   * @return the value at which the shape is now displayed.
   */
  int getVisibleStartTick();

  /**
   * Sets the visibleStartTick field of the shape to the given integer.
   *
   * @param tick the value to be set to the shape's visibleStartTick field.
   */
  void setVisibleStartTick(int tick);

  /**
   * Gets a copy of a shape's list of motions.
   *
   * @return a copy of the list of motions of a shape.
   */
  List<Motion> getMotions();

  /**
   * Gets the shape's name.
   *
   * @return the name of the shape
   */
  String getName();

  /**
   * Gets the shape's type.
   *
   * @return the type of the shape.
   */
  String getType();

  /**
   * Creates a text output representing the shape and its motions in terms of seconds.
   *
   * @param ratio the number of ticks per second for the shape's motions
   * @return text representing the motions of the shape
   */
  String textOutput(double ratio);

  /**
   * Accepts a {@code ShapeVisitor}.
   *
   * @param v      the visitor being accepted
   * @param g      the Graphic to be updated
   * @param minPos the minimum position of acting as the origin in reference to the shape's location
   *               on the graphic
   */
  void accept(ShapeVisitor<IShape> v, Graphics2D g, Position minPos);
}

