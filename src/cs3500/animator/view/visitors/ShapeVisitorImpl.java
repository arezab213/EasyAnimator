package cs3500.animator.view.visitors;

import cs3500.animator.model.Position;
import cs3500.animator.model.shapes.IShape;
import java.awt.Graphics2D;


/**
 * Represents the implementation for a visitor that draws a {@code IShape} onto a graphic.
 */
public class ShapeVisitorImpl implements ShapeVisitor<IShape> {

  @Override
  public void visitRectangle(IShape r, Graphics2D g, Position minPos) {
    if (r.getVisibility()) {
      g.setColor(r.getColor());
      g.fillRect(((int) (r.getPosition().getX() - minPos.getX())),
          ((int) (r.getPosition().getY() - minPos
              .getY())),
          (int) r.getWidth(), (int) r.getHeight());
    }
  }

  @Override
  public void visitEllipse(IShape e, Graphics2D g, Position minPos) {
    if (e.getVisibility()) {
      g.setColor(e.getColor());
      g.fillOval(((int) (e.getPosition().getX() - minPos.getX())),
          ((int) (e.getPosition().getY() - minPos
              .getY())),
          (int) e.getWidth(), (int) e.getHeight());
    }
  }

  @Override
  public void visitPlus(IShape p, Graphics2D g, Position minPos) {
    if (p.getVisibility()) {
      g.setColor(p.getColor());
      int[] xCoordinates = new int[]{
          (int) (p.getPosition().getX() + p.getWidth() / 4 - minPos.getX()),
          (int) (p.getPosition().getX() + 3 * p.getWidth() / 4 - minPos.getX()),
          (int) (p.getPosition().getX() + 3 * p.getWidth() / 4 - minPos.getX()),
          (int) (p.getPosition().getX() + p.getWidth() - minPos.getX()),
          (int) (p.getPosition().getX() + p.getWidth() - minPos.getX()),
          (int) (p.getPosition().getX() + 3 * p.getWidth() / 4 - minPos.getX()),
          (int) (p.getPosition().getX() + 3 * p.getWidth() / 4 - minPos.getX()),
          (int) (p.getPosition().getX() + p.getWidth() / 4 - minPos.getX()),
          (int) (p.getPosition().getX() + p.getWidth() / 4 - minPos.getX()),
          (int) (p.getPosition().getX() - minPos.getX()),
          (int) (p.getPosition().getX() - minPos.getX()),
          (int) (p.getPosition().getX() + p.getWidth() / 4 - minPos.getX()),
          (int) (p.getPosition().getX() + p.getWidth() / 4 - minPos.getX())};
      int[] yCoordinates = new int[]{
          (int) (p.getPosition().getY() - minPos.getY()),
          (int) (p.getPosition().getY() - minPos.getY()),
          (int) (p.getPosition().getY() + p.getHeight() / 4 - minPos.getY()),
          (int) (p.getPosition().getY() + p.getHeight() / 4 - minPos.getY()),
          (int) (p.getPosition().getY() + 3 * p.getHeight() / 4 - minPos.getY()),
          (int) (p.getPosition().getY() + 3 * p.getHeight() / 4 - minPos.getY()),
          (int) (p.getPosition().getY() + p.getHeight() - minPos.getY()),
          (int) (p.getPosition().getY() + p.getHeight() - minPos.getY()),
          (int) (p.getPosition().getY() + 3 * p.getHeight() / 4 - minPos.getY()),
          (int) (p.getPosition().getY() + 3 * p.getHeight() / 4 - minPos.getY()),
          (int) (p.getPosition().getY() + p.getHeight() / 4 - minPos.getY()),
          (int) (p.getPosition().getY() + p.getHeight() / 4 - minPos.getY()),
          (int) (p.getPosition().getY() - minPos.getY())};
      g.fillPolygon(xCoordinates, yCoordinates, 13);
    }
  }
}