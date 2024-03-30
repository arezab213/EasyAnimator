package modeltests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.Position;
import cs3500.animator.model.motions.ChangeColor;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.Move;
import cs3500.animator.model.motions.Scale;
import cs3500.animator.model.shapes.AnimatorEllipse;
import cs3500.animator.model.shapes.AnimatorRectangle;
import cs3500.animator.model.shapes.IShape;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the methods of the {@code Motion} class.
 */
public class MotionTest {

  IShape ellipse;
  IShape circle;
  IShape rectangle;
  IShape square;

  Motion moveEllipse1;
  Motion moveCircle1;
  Motion moveRectangle1;
  Motion moveSquare1;
  Motion moveSquare2;
  Motion moveCircle2;

  Motion scaleEllipse1;
  Motion scaleCircle1;
  Motion scaleRectangle1;
  Motion scaleSquare1;
  Motion scaleRectangle2;

  Motion changeColorEllipse1;
  Motion changeColorCircle1;
  Motion changeColorRectangle1;
  Motion changeColorSquare1;
  Motion changeColorSquare2;
  Motion changeColorCircle2;

  @Before
  public void setInitialData() {
    // examples of shapes
    ellipse = new AnimatorEllipse("E");
    circle = new AnimatorEllipse("C");
    rectangle = new AnimatorRectangle("R");
    square = new AnimatorRectangle("S");

    // examples of motions
    // move motions:
    moveEllipse1 =
        new Move(1, 20, new Position(50, 50));
    moveCircle1 =
        new Move(10, 11, new Position(-10, 10));
    moveRectangle1 =
        new Move(15, 22, new Position(-7, -2));
    moveSquare1 =
        new Move(50, 100, new Position(3, -1));
    moveSquare2 =
        new Move(100, 101, new Position(3, -2));
    moveCircle2 =
        new Move(10, 20, new Position(10, 10));

    // scale motions:
    scaleEllipse1 =
        new Scale(5, 10, 2, 2);
    scaleCircle1 =
        new Scale(5, 10, 3, 3);
    scaleRectangle1 =
        new Scale(1, 10, .85, 2.32);
    scaleSquare1 =
        new Scale(20, 25, 1.5, 1.5);
    scaleRectangle2 =
        new Scale(1, 3, 2, 4);

    // changeColor motions:
    changeColorEllipse1 =
        new ChangeColor(1, 5, new Color(244, 232, 102));
    changeColorCircle1 =
        new ChangeColor(12, 13, new Color(0, 0, 12));
    changeColorRectangle1 =
        new ChangeColor(1, 10, new Color(50, 20, 3));
    changeColorSquare1 =
        new ChangeColor(15, 18, new Color(190, 100, 200));
    changeColorSquare2 =
        new ChangeColor(22, 23, new Color(220, 80, 230));
    changeColorCircle2 =
        new ChangeColor(1, 7, new Color(9, 27, 30));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionStartGreaterThanEnd() {
    Motion invalidMove1 = new Move(25, 14, new Position(-2, -7));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionStartEqualsEnd() {
    Motion invalidMove2 = new Move(26, 25, new Position(-2, -7));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionStartEqual0() {
    Motion invalidScale1 = new Scale(-1, 10, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionStartLessThan0() {
    Motion invalidScale2 = new Scale(-2, 10, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionEndEquals0() {
    Motion invalidChangeColor1 = new ChangeColor(1, 0, new Color(20, 50, 60));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionEndLessThan0() {
    Motion invalidChangeColor2 = new ChangeColor(1, -4, new Color(20, 50, 60));
  }

  @Test
  public void testTimeContains() {
    assertTrue(changeColorRectangle1.timeContains(2));
    assertTrue(changeColorRectangle1.timeContains(9));
    assertFalse(changeColorRectangle1.timeContains(1));
    assertFalse(changeColorRectangle1.timeContains(10));

    assertFalse(moveCircle1.timeContains(10));
    assertFalse(moveCircle1.timeContains(11));
    assertFalse(moveCircle1.timeContains(18));

    assertTrue(scaleEllipse1.timeContains(7));
    assertFalse(scaleEllipse1.timeContains(5));
    assertFalse(scaleEllipse1.timeContains(10));
    assertFalse(scaleEllipse1.timeContains(11));
  }

  @Test
  public void testGetStartTick() {
    assertEquals(5, scaleCircle1.getStartTick());
    assertEquals(22, changeColorSquare2.getStartTick());
    assertEquals(1, moveEllipse1.getStartTick());
  }

  @Test
  public void testGetEndTick() {
    assertEquals(10, scaleCircle1.getEndTick());
    assertEquals(23, changeColorSquare2.getEndTick());
    assertEquals(20, moveEllipse1.getEndTick());
  }

  @Test
  public void testIsNotCompatible() {
    // tests if incompatibility is correctly determined
    assertTrue(changeColorEllipse1.isNotCompatible(changeColorRectangle1));
    assertTrue(scaleCircle1.isNotCompatible(scaleRectangle1));
    assertTrue(moveEllipse1.isNotCompatible(moveCircle1));

    // tests if compatibility is correctly determined
    // (motions of different types, or non-conflicting motions of the same type)
    assertFalse(changeColorEllipse1.isNotCompatible(changeColorSquare2));
    assertFalse(changeColorSquare2.isNotCompatible(moveSquare1));
    assertFalse(scaleEllipse1.isNotCompatible(scaleSquare1));
    assertFalse(moveRectangle1.isNotCompatible(moveCircle1));
    // compatible adjacent motions of the same type
    assertFalse(moveSquare1.isNotCompatible(moveSquare2));
  }

  @Test
  public void testChangeShape() {
    assertEquals(100, rectangle.getWidth(), 0.01);
    assertEquals(100, rectangle.getHeight(), 0.01);
    assertEquals(new Position(0, 0), square.getPosition());
    assertEquals(new Color(0, 0, 0), ellipse.getColor());
    assertEquals(new Color(0, 0, 0), circle.getColor());

    scaleRectangle2.changeShape(rectangle, 2);
    assertEquals(150, rectangle.getWidth(), 0.01);
    assertEquals(250, rectangle.getHeight(), 0.01);

    moveCircle2.changeShape(circle, 15);
    assertEquals(5, circle.getPosition().getY(), 0.01);
    assertEquals(5, circle.getPosition().getX(), 0.01);

    moveCircle2.changeShape(circle, 20);
    assertEquals(new Position(10, 10), circle.getPosition());

    changeColorEllipse1.changeShape(ellipse, 5);
    assertEquals(new Color(244, 232, 102), ellipse.getColor());

    changeColorCircle2.changeShape(circle, 1);
    assertEquals(new Color(0, 0, 0), circle.getColor());
    changeColorCircle2.changeShape(circle, 3);
    assertEquals(new Color(3, 9, 10), circle.getColor());

    circle = new AnimatorEllipse("C");
    changeColorCircle2.changeShape(circle, 5);
    assertEquals(new Color(6, 18, 20), circle.getColor());

    circle = new AnimatorEllipse("C");
    changeColorCircle2.changeShape(circle, 7);
    assertEquals(new Color(9, 27, 30), circle.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeShapeExceptionTimeBefore() {
    scaleRectangle1.changeShape(rectangle, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeShapeExceptionTimeAfter() {
    changeColorEllipse1.changeShape(ellipse, 7);
  }

  @Test
  public void testEquals() {
    // check if equals() returns false properly
    assertNotEquals(moveCircle1, moveCircle2);
    assertNotEquals(scaleEllipse1, scaleCircle1);
    assertNotEquals(changeColorRectangle1, scaleSquare1);

    // check if equals() returns true properly
    assertEquals(moveCircle2, new Move(10, 20, new Position(10, 10)));
    assertEquals(scaleEllipse1, new Scale(5, 10, 2, 2));
    assertEquals(changeColorRectangle1, new ChangeColor(1, 10,
        new Color(50, 20, 3)));
  }

  @Test
  public void testHashCode() {
    assertEquals(new Move(10, 20, new Position(10, 10)).hashCode(),
        moveCircle2.hashCode());
    assertEquals(new Scale(5, 10, 2, 2).hashCode(),
        scaleEllipse1.hashCode());
    assertEquals(new ChangeColor(1, 10, new Color(50, 20, 3)).hashCode(),
        changeColorRectangle1.hashCode());
  }
}
