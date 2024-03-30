package cs3500.animator.view;

import cs3500.animator.model.Position;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.view.visitors.ShapeVisitor;
import cs3500.animator.view.visitors.ShapeVisitorImpl;
import cs3500.animator.view.visitors.ShapeVisitorOutline;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Represents a panel that displays the animation.
 */
public class AnimatorPanel extends JPanel {

  private final Position minPos;
  private List<IShape> shapes;
  private boolean isOutline;

  /**
   * Constructor that initializes a panel with an empty list of shapes.
   *
   * @param minPos the minimum position of the panel acting as an origin in relation to the shape's
   *               position.
   */
  public AnimatorPanel(Position minPos) {
    super();
    this.shapes = new ArrayList<>();
    this.minPos = minPos;
    this.isOutline = false;
  }

  /**
   * Assigns the given list of shapes to this {@code Panel}'s respective field, and initiates the
   * panel to draw them onto the {@code Graphic}.
   *
   * @param shapes The list of shapes to be assigned to the shapes field as well as drawn.
   */
  public void draw(List<IShape> shapes) {
    this.shapes = shapes;
    this.repaint();
  }

  /**
   * Toggles the current outline status to either false or true.
   */
  public void toggleOutline() {
    this.isOutline = !this.isOutline;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.drawCurrentShapes(g);
  }

  /**
   * Draws the shapes currently visible on this panel as {@code Graphics2d} objects.
   *
   * @param g The {@code Graphics} object that will display all the shapes
   */
  private void drawCurrentShapes(Graphics g) {
    Graphics2D g2D = (Graphics2D) g;
    ShapeVisitor<IShape> v;
    if (this.isOutline) {
      v = new ShapeVisitorOutline();
    } else {
      v = new ShapeVisitorImpl();
    }
    for (IShape shape : this.shapes) {
      shape.accept(v, g2D, this.minPos);
    }
  }
}
