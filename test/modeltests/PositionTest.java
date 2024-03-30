package modeltests;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.Position;
import org.junit.Test;

/**
 * Tests the functionality of the methods of the {@code Position} class.
 */
public class PositionTest {

  // examples of positions
  Position origin = new Position(0, 0);
  Position positiveX = new Position(5, 0);
  Position positiveY = new Position(0, 5);
  Position posQ1 = new Position(9, 5);
  Position posQ2 = new Position(-40, 23);
  Position posQ3 = new Position(-6, -7);
  Position posQ4 = new Position(12, -2);

  @Test
  public void testGetX() {
    assertEquals(0, origin.getX(), 0.01);
    assertEquals(5, positiveX.getX(), 0.01);
    assertEquals(0, positiveY.getX(), 0.01);
    assertEquals(9, posQ1.getX(), 0.01);
    assertEquals(-40, posQ2.getX(), 0.01);
    assertEquals(-6, posQ3.getX(), 0.01);
    assertEquals(12, posQ4.getX(), 0.01);
  }

  @Test
  public void testGetY() {
    assertEquals(0, origin.getY(), 0.01);
    assertEquals(0, positiveX.getY(), 0.01);
    assertEquals(5, positiveY.getY(), 0.01);
    assertEquals(5, posQ1.getY(), 0.01);
    assertEquals(23, posQ2.getY(), 0.01);
    assertEquals(-7, posQ3.getY(), 0.01);
    assertEquals(-2, posQ4.getY(), 0.01);
  }

  @Test
  public void testSetX() {
    assertEquals(0, origin.getX(), 0.01);
    origin.setX(7);
    assertEquals(7, origin.getX(), 0.01);
    origin.setX(-20);
    assertEquals(-20, origin.getX(), 0.01);
  }

  @Test
  public void testSetY() {
    assertEquals(0, origin.getY(), 0.01);
    origin.setY(34);
    assertEquals(34, origin.getY(), 0.01);
    origin.setY(-7);
    assertEquals(-7, origin.getY(), 0.01);
  }
}
