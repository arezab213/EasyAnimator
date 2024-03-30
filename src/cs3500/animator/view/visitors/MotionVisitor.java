package cs3500.animator.view.visitors;

/**
 * Represents a visitor for textually representing a motion's transformation on a shape.
 */
public interface MotionVisitor<K, L> {

  /**
   * Visits a Move motion and textually represents its transformations on the given shape.
   *
   * @param move  The Move motion
   * @param shape The shape being transformed by the move
   * @return A string representing the affected attributes of the shape from the Move.
   */
  String visitMove(L move, K shape, String initial);

  /**
   * Visits a ChangeColor motion and textually represents its transformations on the given shape.
   *
   * @param changeColor The ChangeColor motion
   * @param shape       The shape being transformed by the move * @return A
   * @return A string representing the affected attributes of the shape from the ChangeColor.
   */
  String visitChangeColor(L changeColor, K shape, String initial);

  /**
   * Visits a Scale motion and textually represents its transformation on the given shape.
   *
   * @param scale The Scale motion
   * @param shape The shape bing transformed by the scale
   * @return A string representing the affected attributes of the shape from the Scale
   */
  String visitScale(L scale, K shape, String initial);
}
