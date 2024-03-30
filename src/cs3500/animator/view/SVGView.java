package cs3500.animator.view;

import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.view.visitors.MotionVisitor;
import cs3500.animator.view.visitors.MotionVisitorImpl;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents a Scalable Vector Graphics view of an animation.
 */
public class SVGView implements AnimatorView {

  private final double ticksPerSec;
  private final IViewModel<IShape, Motion> model;
  private final Appendable ap;
  private final StringBuilder acc;


  /**
   * Constructs an {@code SVGView} object.
   *
   * @param ticksPerSec the amount of ticks corresponding to second.
   * @param model       the model that this view represents
   * @param ap          the appendable being used for this text-based view
   * @throws IllegalArgumentException if the model is null or if ticksPerSec is non Positive.
   */
  public SVGView(double ticksPerSec, IViewModel<IShape, Motion> model, Appendable ap) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    if (ticksPerSec <= 0) {
      throw new IllegalArgumentException("Ratio of ticks to seconds must be positive.");
    }
    this.ap = ap;
    this.acc = new StringBuilder();
    this.model = model;
    this.ticksPerSec = ticksPerSec;
  }

  /**
   * Produces a textual description of the animation that is compliant with the SVG file format,
   * through the use of a stringBuilder.
   *
   * @return A StringBuilder for textual description of the animation in with the SVG file format.
   */
  private StringBuilder formatSVG() {
    this.acc.append("<svg width=\"")
        .append(this.model.getMaxPos().getX() - this.model.getMinPos().getX())
        .append("\" height=\"")
        .append(this.model.getMaxPos().getY() - this.model.getMinPos().getY())
        .append("\" version=\"1.1\"\nxmlns=\"http://www.w3.org/2000/svg\">\n");

    for (IShape shape : this.model.getShapes(0)) {
      switch (shape.getType()) {
        case "rectangle":
          this.acc.append("<rect ");
          shapeSVGInfo(shape);
          this.acc.append("</rect>");
          break;
        case "ellipse":
          this.acc.append("<ellipse ");
          shapeSVGInfo(shape);
          this.acc.append("</ellipse>");
          break;
        case "plus":
          this.acc.append("<polygon ");
          shapeSVGInfo(shape);
          this.acc.append("</polygon>");
          break;
        default:
          throw new IllegalArgumentException("Invalid shape type; cannot display.");
      }
    }
    this.acc.append("\n</svg>");
    return this.acc;
  }

  /**
   * Appends the initial shape information and motions to the acc for this view.
   *
   * @param shape the shape being formatted
   */
  private void shapeSVGInfo(IShape shape) {
    //display shape attributes
    if (shape.getType().equals("rectangle")) {
      this.acc.append("id=\"").append(shape.getName()).append("\" x=\"")
          .append(shape.getPosition().getX() - this.model.getMinPos().getX()).append("\" y=\"")
          .append(shape.getPosition().getY() - this.model.getMinPos().getY()).append("\" width=\"")
          .append(shape.getWidth()).append("\" height=\"").append(shape.getHeight())
          .append("\" fill=\"rgb(").append(shape.getColor().getRed()).append(",")
          .append(shape.getColor().getGreen()).append(",").append(shape.getColor().getBlue())
          .append(")\" visibility=\"hidden\" >\n");
    }
    if (shape.getType().equals("ellipse")) {
      this.acc.append("id=\"").append(shape.getName()).append("\" cx=\"")
          .append(shape.getPosition().getX() + (shape.getWidth() / 2) - this.model.getMinPos()
              .getX()).append("\" cy=\"").append(
          shape.getPosition().getY() + (shape.getHeight() / 2) - this.model.getMinPos().getX())
          .append("\" rx=\"").append(shape.getWidth() / 2).append("\" ry=\"")
          .append(shape.getHeight() / 2).append("\" fill=\"rgb(").append(shape.getColor().getRed())
          .append(",").append(shape.getColor().getGreen()).append(",")
          .append(shape.getColor().getBlue()).append(")\" visibility=\"hidden\" >\n");
    }
    if (shape.getType().equals("plus")) {
      int[] xCoordinates = new int[]{
          (int) (shape.getPosition().getX() + shape.getWidth() / 4 - this.model.getMinPos().getX()),
          (int) (shape.getPosition().getX() + 3 * shape.getWidth() / 4 - this.model.getMinPos()
              .getX()),
          (int) (shape.getPosition().getX() + 3 * shape.getWidth() / 4 - this.model.getMinPos()
              .getX()),
          (int) (shape.getPosition().getX() + shape.getWidth() - this.model.getMinPos().getX()),
          (int) (shape.getPosition().getX() + shape.getWidth() - this.model.getMinPos().getX()),
          (int) (shape.getPosition().getX() + 3 * shape.getWidth() / 4 - this.model.getMinPos()
              .getX()),
          (int) (shape.getPosition().getX() + 3 * shape.getWidth() / 4 - this.model.getMinPos()
              .getX()),
          (int) (shape.getPosition().getX() + shape.getWidth() / 4 - this.model.getMinPos().getX()),
          (int) (shape.getPosition().getX() + shape.getWidth() / 4 - this.model.getMinPos().getX()),
          (int) (shape.getPosition().getX() - this.model.getMinPos().getX()),
          (int) (shape.getPosition().getX() - this.model.getMinPos().getX()),
          (int) (shape.getPosition().getX() + shape.getWidth() / 4 - this.model.getMinPos().getX()),
          (int) (shape.getPosition().getX() + shape.getWidth() / 4 - this.model.getMinPos()
              .getX())};
      int[] yCoordinates = new int[]{
          (int) (shape.getPosition().getY() - this.model.getMinPos().getY()),
          (int) (shape.getPosition().getY() - this.model.getMinPos().getY()),
          (int) (shape.getPosition().getY() + shape.getHeight() / 4 - this.model.getMinPos()
              .getY()),
          (int) (shape.getPosition().getY() + shape.getHeight() / 4 - this.model.getMinPos()
              .getY()),
          (int) (shape.getPosition().getY() + 3 * shape.getHeight() / 4 - this.model.getMinPos()
              .getY()),
          (int) (shape.getPosition().getY() + 3 * shape.getHeight() / 4 - this.model.getMinPos()
              .getY()),
          (int) (shape.getPosition().getY() + shape.getHeight() - this.model.getMinPos().getY()),
          (int) (shape.getPosition().getY() + shape.getHeight() - this.model.getMinPos().getY()),
          (int) (shape.getPosition().getY() + 3 * shape.getHeight() / 4 - this.model.getMinPos()
              .getY()),
          (int) (shape.getPosition().getY() + 3 * shape.getHeight() / 4 - this.model.getMinPos()
              .getY()),
          (int) (shape.getPosition().getY() + shape.getHeight() / 4 - this.model.getMinPos()
              .getY()),
          (int) (shape.getPosition().getY() + shape.getHeight() / 4 - this.model.getMinPos()
              .getY()),
          (int) (shape.getPosition().getY() - this.model.getMinPos().getY())};
      this.acc.append("id=\"").append(shape.getName()).append("\" points=\"")
          .append(xCoordinates[0] + "," + yCoordinates[0] + " "
              + xCoordinates[1] + "," + yCoordinates[1] + " "
              + xCoordinates[2] + "," + yCoordinates[2] + " "
              + xCoordinates[3] + "," + yCoordinates[3] + " "
              + xCoordinates[4] + "," + yCoordinates[4] + " "
              + xCoordinates[5] + "," + yCoordinates[5] + " "
              + xCoordinates[6] + "," + yCoordinates[6] + " "
              + xCoordinates[7] + "," + yCoordinates[7] + " "
              + xCoordinates[8] + "," + yCoordinates[8] + " "
              + xCoordinates[9] + "," + yCoordinates[9] + " "
              + xCoordinates[10] + "," + yCoordinates[10] + " "
              + xCoordinates[11] + "," + yCoordinates[11] + " "
              + xCoordinates[12] + "," + yCoordinates[12] + "\"")
          .append(" fill=\"rgb(").append(shape.getColor().getRed())
          .append(",").append(shape.getColor().getGreen()).append(",")
          .append(shape.getColor().getBlue()).append(")\" visibility=\"hidden\" >\n");
    }
    // set visible when necessary
    this.acc.append(
        "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" " + "begin=\"")
        .append(String
            .format("%.1f", (shape.getVisibleStartTick() / this.ticksPerSec * 1000)))
        .append("ms\" dur=\"1s\" fill=\"freeze\" />\n");
    // display shape's motions
    for (Motion motion : shape.getMotions()) {
      MotionVisitor<IShape, Motion> v = new MotionVisitorImpl(model.getMinPos());
      String startString = ("<animate attributeType=\"xml\" begin=\""
          + String.format("%.1f", (motion.getStartTick() / this.ticksPerSec * 1000))
          + "ms\" dur=\"" + String.format("%.1f",
          ((motion.getEndTick() - motion.getStartTick()) / this.ticksPerSec * 1000))
          + "ms\" attributeName=\"");
      this.acc.append(motion.accept(v, shape, startString));
    }
  }

  @Override
  public void render() {
    try {
      this.ap.append(this.formatSVG());
    } catch (IOException e) {
      throw new IllegalStateException("Failed the append.");
    }
  }

  @Override
  public void setButtonListeners(ActionListener actionEvent) {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void resetTimer(int tick) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void cancelTimer() {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public int getCurrentTick() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void toggleLoop() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void toggleOutline() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void toggleDiscrete() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void incrementSpeed() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }

  @Override
  public void decrementSpeed() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Operation not available for this view");
  }
}
