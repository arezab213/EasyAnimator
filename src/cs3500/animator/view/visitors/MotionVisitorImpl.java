package cs3500.animator.view.visitors;

import cs3500.animator.model.Position;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.shapes.IShape;

/**
 * Represents the implementation for a visitor that textually represents a motion's transformation
 * on a shape.
 */
public class MotionVisitorImpl implements MotionVisitor<IShape, Motion> {

  Position modelMinPos;

  /**
   * Constructs a {@code MotionVisitorImpl} object.
   *
   * @param modelMinPos the minimum position of the corresponding model that contains the motions
   *                    being visited and the shapes being transformed.
   */
  public MotionVisitorImpl(Position modelMinPos) {
    this.modelMinPos = modelMinPos;
  }

  @Override
  public String visitMove(Motion move, IShape shape, String initial) {
    String acc = initial;
    if (shape.getType().equals("rectangle")) {
      acc += "x\" from=\""
          + (shape.getShapeAtTick(move.getStartTick()).getPosition().getX() - modelMinPos.getX())
          + "\" to=\""
          + (shape.getShapeAtTick(move.getEndTick()).getPosition().getX() - modelMinPos.getX())
          + "\" fill=\"freeze\" />\n"
          + initial + "y\" from=\""
          + (shape.getShapeAtTick(move.getStartTick()).getPosition().getY() - modelMinPos.getY())
          + "\" to=\""
          + (shape.getShapeAtTick(move.getEndTick()).getPosition().getY() - modelMinPos.getY())
          + "\" fill=\"freeze\" />\n";
    }
    if (shape.getType().equals("ellipse")) {
      acc += "cx\" from=\""
          + (shape.getShapeAtTick(move.getStartTick()).getPosition().getX() + (shape.getWidth()
          / 2) - modelMinPos.getX())
          + "\" to=\""
          + (shape.getShapeAtTick(move.getEndTick()).getPosition().getX() + (shape.getWidth() / 2)
          - modelMinPos.getX())
          + "\" fill=\"freeze\" />\n"
          + initial + "cy\" from=\""
          + (shape.getShapeAtTick(move.getStartTick()).getPosition().getY() + (shape.getHeight()
          / 2) - modelMinPos.getY())
          + "\" to=\""
          + (shape.getShapeAtTick(move.getEndTick()).getPosition().getY() + (shape.getHeight() / 2)
          - modelMinPos.getY())
          + "\" fill=\"freeze\" />\n";
    }
    if (shape.getType().equals("plus")) {
      double startWidth = shape.getShapeAtTick(move.getStartTick()).getWidth();
      double endWidth = shape.getShapeAtTick(move.getEndTick()).getWidth();
      Position startPos = shape.getShapeAtTick(move.getStartTick()).getPosition();
      Position endPos = shape.getShapeAtTick(move.getEndTick()).getPosition();
      // start locations
      int[] xCoordinatesStart = new int[]{
          (int) (startPos.getX() + startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + 3 * startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + 3 * startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth - modelMinPos.getX()),
          (int) (startPos.getX() + 3 * startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + 3 * startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() - modelMinPos.getX()),
          (int) (startPos.getX() - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth / 4 - modelMinPos.getX())};
      int[] yCoordinatesStart = new int[]{
          (int) (startPos.getY() - modelMinPos.getY()),
          (int) (startPos.getY() - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + 3 * startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + 3 * startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth - modelMinPos.getY()),
          (int) (startPos.getY() + 3 * startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + 3 * startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() - modelMinPos.getY())};
      // end locations
      int[] xCoordinatesEnd = new int[]{
          (int) (endPos.getX() + endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + 3 * endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + 3 * endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth - modelMinPos.getX()),
          (int) (endPos.getX() + 3 * endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + 3 * endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() - modelMinPos.getX()),
          (int) (endPos.getX() - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth / 4 - modelMinPos.getX())};
      int[] yCoordinatesEnd = new int[]{
          (int) (endPos.getY() - modelMinPos.getY()),
          (int) (endPos.getY() - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + 3 * endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + 3 * endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth - modelMinPos.getY()),
          (int) (endPos.getY() + 3 * endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + 3 * endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() - modelMinPos.getY())};
      acc += "points\" from=\""
          + (xCoordinatesStart[0] + "," + yCoordinatesStart[0] + " "
          + xCoordinatesStart[1] + "," + yCoordinatesStart[1] + " "
          + xCoordinatesStart[2] + "," + yCoordinatesStart[2] + " "
          + xCoordinatesStart[3] + "," + yCoordinatesStart[3] + " "
          + xCoordinatesStart[4] + "," + yCoordinatesStart[4] + " "
          + xCoordinatesStart[5] + "," + yCoordinatesStart[5] + " "
          + xCoordinatesStart[6] + "," + yCoordinatesStart[6] + " "
          + xCoordinatesStart[7] + "," + yCoordinatesStart[7] + " "
          + xCoordinatesStart[8] + "," + yCoordinatesStart[8] + " "
          + xCoordinatesStart[9] + "," + yCoordinatesStart[9] + " "
          + xCoordinatesStart[10] + "," + yCoordinatesStart[10] + " "
          + xCoordinatesStart[11] + "," + yCoordinatesStart[11] + " "
          + xCoordinatesStart[12] + "," + yCoordinatesStart[12] + "\"")
          + " to=\""
          + (xCoordinatesEnd[0] + "," + yCoordinatesEnd[0] + " "
          + xCoordinatesEnd[1] + "," + yCoordinatesEnd[1] + " "
          + xCoordinatesEnd[2] + "," + yCoordinatesEnd[2] + " "
          + xCoordinatesEnd[3] + "," + yCoordinatesEnd[3] + " "
          + xCoordinatesEnd[4] + "," + yCoordinatesEnd[4] + " "
          + xCoordinatesEnd[5] + "," + yCoordinatesEnd[5] + " "
          + xCoordinatesEnd[6] + "," + yCoordinatesEnd[6] + " "
          + xCoordinatesEnd[7] + "," + yCoordinatesEnd[7] + " "
          + xCoordinatesEnd[8] + "," + yCoordinatesEnd[8] + " "
          + xCoordinatesEnd[9] + "," + yCoordinatesEnd[9] + " "
          + xCoordinatesEnd[10] + "," + yCoordinatesEnd[10] + " "
          + xCoordinatesEnd[11] + "," + yCoordinatesEnd[11] + " "
          + xCoordinatesEnd[12] + "," + yCoordinatesEnd[12] + "\"")
          + " fill=\"freeze\" />\n";
    }
    return acc;
  }

  @Override
  public String visitChangeColor(Motion changeColor, IShape shape, String initial) {
    String acc = initial;
    acc += "fill\" from=\""
        + "rgb(" + shape.getShapeAtTick(changeColor.getStartTick()).getColor().getRed()
        + "," + shape.getShapeAtTick(changeColor.getStartTick()).getColor().getGreen()
        + "," + shape.getShapeAtTick(changeColor.getStartTick()).getColor().getBlue() + ")\" "
        + "to=\""
        + "rgb(" + shape.getShapeAtTick(changeColor.getEndTick()).getColor().getRed()
        + "," + shape.getShapeAtTick(changeColor.getEndTick()).getColor().getGreen()
        + "," + shape.getShapeAtTick(changeColor.getEndTick()).getColor().getBlue() + ")\" "
        + "fill=\"freeze\" />\n";
    return acc;
  }

  @Override
  public String visitScale(Motion scale, IShape shape, String initial) {
    String acc = initial;
    if (shape.getType().equals("rectangle")) {
      acc += "width\" from=\""
          + (shape.getShapeAtTick(scale.getStartTick()).getWidth())
          + "\" to=\""
          + (shape.getShapeAtTick(scale.getEndTick()).getWidth())
          + "\" fill=\"freeze\" />\n"
          + initial + "height\" from=\""
          + (shape.getShapeAtTick(scale.getStartTick()).getHeight())
          + "\" to=\""
          + (shape.getShapeAtTick(scale.getEndTick()).getHeight())
          + "\" fill=\"freeze\" />\n";
    }
    if (shape.getType().equals("ellipse")) {
      acc += "rx\" from=\""
          + ((shape.getShapeAtTick(scale.getStartTick()).getWidth()) / 2)
          + "\" to=\""
          + ((shape.getShapeAtTick(scale.getEndTick()).getWidth()) / 2)
          + "\" fill=\"freeze\" />\n"
          + initial + "ry\" from=\""
          + ((shape.getShapeAtTick(scale.getStartTick()).getHeight()) / 2)
          + "\" to=\""
          + ((shape.getShapeAtTick(scale.getEndTick()).getHeight()) / 2)
          + "\" fill=\"freeze\" />\n";
    }
    if (shape.getType().equals("plus")) {
      double startWidth = shape.getShapeAtTick(scale.getStartTick()).getWidth();
      double endWidth = shape.getShapeAtTick(scale.getEndTick()).getWidth();
      Position startPos = shape.getShapeAtTick(scale.getStartTick()).getPosition();
      Position endPos = shape.getShapeAtTick(scale.getEndTick()).getPosition();
      // start locations
      int[] xCoordinatesStart = new int[]{
          (int) (startPos.getX() + startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + 3 * startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + 3 * startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth - modelMinPos.getX()),
          (int) (startPos.getX() + 3 * startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + 3 * startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() - modelMinPos.getX()),
          (int) (startPos.getX() - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth / 4 - modelMinPos.getX()),
          (int) (startPos.getX() + startWidth / 4 - modelMinPos.getX())};
      int[] yCoordinatesStart = new int[]{
          (int) (startPos.getY() - modelMinPos.getY()),
          (int) (startPos.getY() - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + 3 * startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + 3 * startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth - modelMinPos.getY()),
          (int) (startPos.getY() + 3 * startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + 3 * startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() + startWidth / 4 - modelMinPos.getY()),
          (int) (startPos.getY() - modelMinPos.getY())};
      // end locations
      int[] xCoordinatesEnd = new int[]{
          (int) (endPos.getX() + endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + 3 * endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + 3 * endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth - modelMinPos.getX()),
          (int) (endPos.getX() + 3 * endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + 3 * endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() - modelMinPos.getX()),
          (int) (endPos.getX() - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth / 4 - modelMinPos.getX()),
          (int) (endPos.getX() + endWidth / 4 - modelMinPos.getX())};
      int[] yCoordinatesEnd = new int[]{
          (int) (endPos.getY() - modelMinPos.getY()),
          (int) (endPos.getY() - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + 3 * endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + 3 * endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth - modelMinPos.getY()),
          (int) (endPos.getY() + 3 * endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + 3 * endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() + endWidth / 4 - modelMinPos.getY()),
          (int) (endPos.getY() - modelMinPos.getY())};
      acc += "points\" from=\""
          + (xCoordinatesStart[0] + "," + yCoordinatesStart[0] + " "
          + xCoordinatesStart[1] + "," + yCoordinatesStart[1] + " "
          + xCoordinatesStart[2] + "," + yCoordinatesStart[2] + " "
          + xCoordinatesStart[3] + "," + yCoordinatesStart[3] + " "
          + xCoordinatesStart[4] + "," + yCoordinatesStart[4] + " "
          + xCoordinatesStart[5] + "," + yCoordinatesStart[5] + " "
          + xCoordinatesStart[6] + "," + yCoordinatesStart[6] + " "
          + xCoordinatesStart[7] + "," + yCoordinatesStart[7] + " "
          + xCoordinatesStart[8] + "," + yCoordinatesStart[8] + " "
          + xCoordinatesStart[9] + "," + yCoordinatesStart[9] + " "
          + xCoordinatesStart[10] + "," + yCoordinatesStart[10] + " "
          + xCoordinatesStart[11] + "," + yCoordinatesStart[11] + " "
          + xCoordinatesStart[12] + "," + yCoordinatesStart[12] + "\"")
          + " to=\""
          + (xCoordinatesEnd[0] + "," + yCoordinatesEnd[0] + " "
          + xCoordinatesEnd[1] + "," + yCoordinatesEnd[1] + " "
          + xCoordinatesEnd[2] + "," + yCoordinatesEnd[2] + " "
          + xCoordinatesEnd[3] + "," + yCoordinatesEnd[3] + " "
          + xCoordinatesEnd[4] + "," + yCoordinatesEnd[4] + " "
          + xCoordinatesEnd[5] + "," + yCoordinatesEnd[5] + " "
          + xCoordinatesEnd[6] + "," + yCoordinatesEnd[6] + " "
          + xCoordinatesEnd[7] + "," + yCoordinatesEnd[7] + " "
          + xCoordinatesEnd[8] + "," + yCoordinatesEnd[8] + " "
          + xCoordinatesEnd[9] + "," + yCoordinatesEnd[9] + " "
          + xCoordinatesEnd[10] + "," + yCoordinatesEnd[10] + " "
          + xCoordinatesEnd[11] + "," + yCoordinatesEnd[11] + " "
          + xCoordinatesEnd[12] + "," + yCoordinatesEnd[12] + "\"")
          + " fill=\"freeze\" />\n";
    }
    return acc;
  }
}
