package cs3500.animator.view.visitors;

import cs3500.animator.model.Position;
import java.awt.Graphics2D;

/**
 * Represents a visitor for drawing a {@code IShape} onto a graphic.
 */
public interface ShapeVisitor<K> {

  /**
   * Visits a {@code AnimatorRectangle} and draws it onto the given graphic.
   *
   * @param r      The rectangle class being visited
   * @param g      The {@code Graphics2D} that will represent the shape
   * @param minPos The shape's reference point for the origin
   */
  void visitRectangle(K r, Graphics2D g, Position minPos);

  /**
   * Visits a {@code AnimatorEllipse} and draws it onto the given graphic.
   *
   * @param e      The ellipse class being visited
   * @param g      The {@code Graphics2D} that will represent the shape
   * @param minPos the shape's reference point for the origin
   */
  void visitEllipse(K e, Graphics2D g, Position minPos);

  /**
   * Visits a {@code AnimatorPlus} and draws it onto the given graphic.
   *
   * @param p      The plus sign class being visited
   * @param g      The {@code Graphics2D} that will represent the shape
   * @param minPos the shape's reference point for the origin
   */
  void visitPlus(K p, Graphics2D g, Position minPos);
}
