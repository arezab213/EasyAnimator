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
import cs3500.animator.model.shapes.AnimatorPlus;
import cs3500.animator.model.shapes.AnimatorRectangle;
import cs3500.animator.model.shapes.IShape;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the methods of the {@code Shape} class.
 */
public class ShapeTest {

  IShape ellipse;
  IShape circle;
  IShape rectangle;
  IShape square;
  IShape plus;
  IShape ellipseCustom;
  IShape circleCustom;
  IShape rectangleCustom;
  IShape squareCustom;
  IShape plusCustom;

  Motion moveEllipse1;
  Motion moveCircle1;
  Motion moveRectangle1;
  Motion moveSquare1;

  Motion scaleEllipse1;
  Motion scaleCircle1;
  Motion scaleRectangle1;
  Motion scaleSquare1;

  Motion changeColorEllipse1;
  Motion changeColorCircle1;
  Motion changeColorRectangle1;
  Motion changeColorSquare1;
  Motion changeColorSquare2;

  @Before
  public void setInitialData() {
    // examples of shapes
    ellipse = new AnimatorEllipse("E");
    circle = new AnimatorEllipse("C");
    rectangle = new AnimatorRectangle("R");
    square = new AnimatorRectangle("S");
    plus = new AnimatorPlus("P");

    // custom constructors:
    ellipseCustom = new AnimatorEllipse("CustomE", 130, 95,
        new Color(30, 150, 150), new Position(150, 32), false);
    circleCustom = new AnimatorEllipse("CustomC", 13, 13,
        new Color(200, 30, 60), new Position(0, 12), true);
    rectangleCustom = new AnimatorRectangle("CustomR", 50, 140,
        new Color(200, 150, 10), new Position(20.4, 50.7), false);
    squareCustom = new AnimatorRectangle("CustomS", 10, 10,
        new Color(0, 0, 50), new Position(50, 50), true);
    plusCustom = new AnimatorPlus("CustomP", 10, 10,
        new Color(0, 0, 50), new Position(50, 50), true, 1);

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

    // scale motions:
    scaleEllipse1 =
        new Scale(5, 10, 2, 2);
    scaleCircle1 =
        new Scale(5, 10, 3, 3);
    scaleRectangle1 =
        new Scale(1, 10, .85, 2.32);
    scaleSquare1 =
        new Scale(20, 25, 1.5, 1.5);

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
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeConstructorHeight0() {
    IShape rect = new AnimatorRectangle("rect", 8, 0,
        new Color(12, 100, 12), new Position(4, 2), true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeConstructorWidth0() {
    IShape circle = new AnimatorEllipse("circle", 0.0, 0.0,
        new Color(10, 10, 100), new Position(0, 0), true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeConstructorHeightNegative() {
    IShape ellipse = new AnimatorEllipse("ellipse", 10, -5,
        new Color(100, 200, 0), new Position(-2, -2), true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeConstructorWidthNegative() {
    IShape ellipse = new AnimatorEllipse("ellipse2", -3, 25,
        new Color(10, 200, 300), new Position(2, -62), true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlusIncorrectDimensions() {
    IShape plus = new AnimatorPlus("plus2", 30, 31,
        new Color(10, 20, 30), new Position(30, 30), true, 1);
  }

  @Test
  public void testGetShapeAtTick() {
    ellipse.addMotionToShape(scaleEllipse1);
    ellipse.addMotionToShape(changeColorEllipse1);
    ellipse.addMotionToShape(moveEllipse1);
    IShape ellipseCopy = new AnimatorEllipse("E", 200, 200,
        new Color(244, 232, 102),
        new Position(23.684, 23.684), true);
    ellipseCopy.addMotionToShape(scaleEllipse1);
    ellipseCopy.addMotionToShape(changeColorEllipse1);
    ellipseCopy.addMotionToShape(moveEllipse1);

    assertEquals(ellipseCopy.getWidth(), ellipse.getShapeAtTick(10).getWidth(), .01);
    assertEquals(ellipseCopy.getHeight(), ellipse.getShapeAtTick(10).getHeight(), 0.0);
    assertEquals(ellipseCopy.getWidth(), ellipse.getShapeAtTick(10).getWidth(), 0.0);
    assertEquals(ellipseCopy.getPosition(), ellipse.getShapeAtTick(10).getPosition());

    assertEquals(ellipseCopy, ellipse.getShapeAtTick(10));
  }

  @Test
  public void testAddMotionToShape() {
    // checks that the motions have been added correctly
    assertEquals(0, square.getMotions().size());
    square.addMotionToShape(moveSquare1);
    square.addMotionToShape(scaleSquare1);
    square.addMotionToShape(changeColorSquare1);
    plus.addMotionToShape(moveSquare1);
    plus.addMotionToShape(scaleSquare1);
    plus.addMotionToShape(changeColorSquare1);
    assertEquals(3, plus.getMotions().size());
    assertTrue(plus.getMotions().contains(moveSquare1));
    assertTrue(plus.getMotions().contains(scaleSquare1));
    assertTrue(plus.getMotions().contains(changeColorSquare1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionToShapeEllipseOverlap() {
    ellipse.addMotionToShape(scaleEllipse1);
    ellipse.addMotionToShape(new Scale(2, 7, 3, 0.5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionToShapeRectangleOverlap() {
    rectangle.addMotionToShape(changeColorRectangle1);
    rectangle.addMotionToShape(new ChangeColor(9, 20, new Color(100, 50, 0)));
  }

  @Test
  public void testRemoveMotionFromShape() {
    assertEquals(0, square.getMotions().size());
    square.addMotionToShape(moveSquare1);
    square.addMotionToShape(scaleSquare1);
    square.addMotionToShape(changeColorSquare1);
    assertEquals(3, square.getMotions().size());
    assertTrue(square.getMotions().contains(moveSquare1));
    assertTrue(square.getMotions().contains(scaleSquare1));
    assertTrue(square.getMotions().contains(changeColorSquare1));

    square.removeMotionFromShape(moveSquare1);
    assertFalse(square.getMotions().contains(moveSquare1));

    square.removeMotionFromShape(scaleSquare1);
    assertFalse(square.getMotions().contains(scaleSquare1));

  }

  @Test
  public void testSetPosition() {
    assertEquals(new Position(0, 0), circle.getPosition());
    circle.setPosition(new Position(8, -2));
    assertEquals(8, circle.getPosition().getX(), 0.01);
    assertEquals(-2, circle.getPosition().getY(), 0.01);

    assertEquals(new Position(0, 0), ellipse.getPosition());
    ellipse.setPosition(new Position(0, 0.32));
    assertEquals(new Position(0, 0.32), ellipse.getPosition());

    assertEquals(new Position(0, 0), rectangle.getPosition());
    rectangle.setPosition(new Position(-50.3, -10));
    assertEquals(-50.3, rectangle.getPosition().getX(), 0.01);
    assertEquals(-10, rectangle.getPosition().getY(), 0.01);
  }

  @Test
  public void testSetColor() {
    assertEquals(Color.BLACK, this.rectangle.getColor());
    rectangle.setColor(new Color(100, 100, 200));
    assertEquals(100, rectangle.getColor().getRed());
    assertEquals(100, rectangle.getColor().getGreen());
    assertEquals(200, rectangle.getColor().getBlue());
  }

  @Test
  public void testGetWidth() {
    assertEquals(100, ellipse.getWidth(), .001);
    assertEquals(100, circle.getWidth(), .001);
    assertEquals(100, rectangle.getWidth(), .001);
    assertEquals(100, square.getWidth(), .001);
    assertEquals(100, plus.getWidth(), 0.001);
  }

  @Test
  public void testGetHeight() {
    assertEquals(100, ellipse.getWidth(), .001);
    assertEquals(circle.getHeight(), circle.getWidth(), .001);
    assertEquals(100, rectangle.getWidth(), .001);
    assertEquals(square.getHeight(), square.getWidth(), .001);
    assertEquals(100, plus.getHeight(), 0.001);
  }


  @Test
  public void testGetColor() {
    Color circleColor = this.circle.getColor();
    Color ellipseColor = this.ellipse.getColor();
    Color squareColor = this.square.getColor();
    Color rectangleColor = this.rectangle.getColor();
    Color plusColor = this.plus.getColor();

    // check reference variables
    assertEquals(Color.BLACK, circleColor);
    assertEquals(Color.BLACK, ellipseColor);
    assertEquals(Color.BLACK, squareColor);
    assertEquals(Color.BLACK, rectangleColor);
    assertEquals(Color.BLACK, plusColor);

    // ensure original remains unchanged
    assertEquals(Color.BLACK, this.circle.getColor());
    assertEquals(Color.BLACK, this.ellipse.getColor());
    assertEquals(Color.BLACK, this.square.getColor());
    assertEquals(Color.BLACK, this.rectangle.getColor());
    assertEquals(Color.black, this.plus.getColor());
  }

  @Test
  public void testGetPosition() {
    assertEquals(new Position(0, 0), ellipse.getPosition());
    assertEquals(new Position(0, 0), circle.getPosition());
    assertEquals(new Position(0, 0), rectangle.getPosition());
    assertEquals(new Position(0, 0), square.getPosition());
    assertEquals(new Position(0, 0), plus.getPosition());

    ellipse.setPosition(new Position(10, 10));
    circle.setPosition(new Position(1, -5));
    rectangle.setPosition(new Position(-2.3, 9.0));
    square.setPosition(new Position(17, 1002));
    plus.setPosition(new Position(-50, 50));

    assertEquals(new Position(10, 10), ellipse.getPosition());
    assertEquals(new Position(1, -5), circle.getPosition());
    assertEquals(new Position(-2.3, 9.0), rectangle.getPosition());
    assertEquals(new Position(17, 1002), square.getPosition());
    assertEquals(new Position(-50, 50), plus.getPosition());
  }

  @Test
  public void testGetMotions() {
    assertEquals(0, rectangle.getMotions().size());

    rectangle.addMotionToShape(changeColorRectangle1);
    rectangle.addMotionToShape(scaleRectangle1);
    rectangle.addMotionToShape(moveRectangle1);

    assertEquals(new ChangeColor(1, 10, new Color(50, 20, 3)),
        rectangle.getMotions().get(0));
    assertEquals(new Scale(1, 10, .85, 2.32),
        rectangle.getMotions().get(1));
    assertEquals(new Move(15, 22, new Position(-7, -2)),
        rectangle.getMotions().get(2));

  }

  @Test
  public void testSetWidth() {
    circle.setWidth(10);
    assertEquals(10, circle.getWidth(), .001);
    square.setWidth(50);
    assertEquals(50, square.getWidth(), .001);
    rectangle.setWidth(500);
    assertEquals(500, rectangle.getWidth(), .001);
    ellipse.setWidth(123);
    assertEquals(123, ellipse.getWidth(), .001);
    plus.setWidth(4);
    assertEquals(4, plus.getWidth(), .001);
  }

  @Test
  public void testSetHeight() {
    circle.setHeight(10);
    assertEquals(10, circle.getHeight(), .001);
    square.setHeight(50);
    assertEquals(50, square.getHeight(), .001);
    rectangle.setHeight(500);
    assertEquals(500, rectangle.getHeight(), .001);
    ellipse.setHeight(123);
    assertEquals(123, ellipse.getHeight(), .001);
    plus.setHeight(4);
    assertEquals(4, plus.getHeight(), .001);
  }

  @Test
  public void testShapeToStringRectangle() {
    assertEquals("shape R rectangle\n", rectangle.toString());
    rectangle.addMotionToShape(moveRectangle1);
    rectangle.addMotionToShape(scaleRectangle1);
    rectangle.addMotionToShape(changeColorRectangle1);

    assertEquals("shape R rectangle\n"
            + "motion R 1 0 0 100 100 0 0 0   10 0 0 85 232 0 0 0\n"
            + "motion R 1 0 0 85 232 0 0 0   10 0 0 85 232 50 20 3\n"
            + "motion R 10 0 0 85 232 50 20 3   15 0 0 85 232 50 20 3\n"
            + "motion R 15 0 0 85 232 50 20 3   22 -7 -2 85 232 50 20 3\n",
        rectangle.toString());
  }

  @Test
  public void testShapeToStringCircle() {
    assertEquals("shape C ellipse\n", circle.toString());
    circle.addMotionToShape(changeColorCircle1);
    circle.addMotionToShape(scaleCircle1);
    circle.addMotionToShape(moveCircle1);
    assertEquals("shape C ellipse\n"
        + "motion C 1 0 0 100 100 0 0 0   5 0 0 100 100 0 0 0\n"
        + "motion C 5 0 0 100 100 0 0 0   10 0 0 300 300 0 0 0\n"
        + "motion C 10 0 0 300 300 0 0 0   11 -10 10 300 300 0 0 0\n"
        + "motion C 11 -10 10 300 300 0 0 0   12 -10 10 300 300 0 0 0\n"
        + "motion C 12 -10 10 300 300 0 0 0   13 -10 10 300 300 0 0 12\n", circle.toString());
  }

  @Test
  public void testShapeToStringEllipse() {
    assertEquals("shape E ellipse\n", ellipse.toString());
    ellipse.addMotionToShape(changeColorEllipse1);
    assertEquals("shape E ellipse\n"
        + "motion E 1 0 0 100 100 0 0 0   5 0 0 100 100 244 232 102\n", ellipse.toString());

    ellipse.addMotionToShape(scaleEllipse1);
    assertEquals("shape E ellipse\n"
        + "motion E 1 0 0 100 100 244 232 102   5 0 0 100 100 244 232 102\n"
        + "motion E 5 0 0 100 100 244 232 102   10 0 0 200 200 244 232 102\n", ellipse.toString());
  }

  @Test
  public void testShapeToStringSquare() {
    assertEquals("shape S rectangle\n", square.toString());
    square.addMotionToShape(changeColorSquare1);
    square.addMotionToShape(scaleSquare1);
    square.addMotionToShape(moveSquare1);
    square.addMotionToShape(changeColorSquare2);

    assertEquals("shape S rectangle\n"
        + "motion S 1 0 0 100 100 0 0 0   15 0 0 100 100 0 0 0\n"
        + "motion S 15 0 0 100 100 0 0 0   18 0 0 100 100 190 100 200\n"
        + "motion S 18 0 0 100 100 190 100 200   20 0 0 100 100 190 100 200\n"
        + "motion S 20 0 0 100 100 190 100 200   25 0 0 150 150 190 100 200\n"
        + "motion S 22 0 0 150 150 190 100 200   23 0 0 150 150 220 80 230\n"
        + "motion S 25 0 0 150 150 220 80 230   50 0 0 150 150 220 80 230\n"
        + "motion S 50 0 0 150 150 220 80 230   100 3 -1 150 150 220 80 230\n", square.toString());
  }

  @Test
  public void testShapeToStringPlus() {
    assertEquals("shape P plus\n", plus.toString());
    plus.addMotionToShape(changeColorSquare1);
    plus.addMotionToShape(scaleSquare1);
    plus.addMotionToShape(moveSquare1);
    plus.addMotionToShape(changeColorSquare2);

    assertEquals("shape P plus\n"
        + "motion P 1 0 0 100 100 0 0 0   15 0 0 100 100 0 0 0\n"
        + "motion P 15 0 0 100 100 0 0 0   18 0 0 100 100 190 100 200\n"
        + "motion P 18 0 0 100 100 190 100 200   20 0 0 100 100 190 100 200\n"
        + "motion P 20 0 0 100 100 190 100 200   25 0 0 150 150 190 100 200\n"
        + "motion P 22 0 0 150 150 190 100 200   23 0 0 150 150 220 80 230\n"
        + "motion P 25 0 0 150 150 220 80 230   50 0 0 150 150 220 80 230\n"
        + "motion P 50 0 0 150 150 220 80 230   100 3 -1 150 150 220 80 230\n", plus.toString());
  }

  @Test
  public void testShapeTextOutputRectangle() {
    assertEquals("shape R rectangle\n", rectangle.toString());
    rectangle.addMotionToShape(moveRectangle1);
    rectangle.addMotionToShape(scaleRectangle1);
    rectangle.addMotionToShape(changeColorRectangle1);

    assertEquals("shape R rectangle\n"
            + "motion R 1.33 0 0 100 100 0 0 0   13.33 0 0 85 232 0 0 0\n"
            + "motion R 1.33 0 0 85 232 0 0 0   13.33 0 0 85 232 50 20 3\n"
            + "motion R 13.33 0 0 85 232 50 20 3   20.00 0 0 85 232 50 20 3\n"
            + "motion R 20.00 0 0 85 232 50 20 3   29.33 -7 -2 85 232 50 20 3\n",
        rectangle.textOutput(.75));
  }

  @Test
  public void testShapeTextOutputCircle() {
    assertEquals("shape C ellipse\n", circle.toString());
    circle.addMotionToShape(changeColorCircle1);
    circle.addMotionToShape(scaleCircle1);
    circle.addMotionToShape(moveCircle1);
    assertEquals("shape C ellipse\n"
            + "motion C 0.20 0 0 100 100 0 0 0   1.00 0 0 100 100 0 0 0\n"
            + "motion C 1.00 0 0 100 100 0 0 0   2.00 0 0 300 300 0 0 0\n"
            + "motion C 2.00 0 0 300 300 0 0 0   2.20 -10 10 300 300 0 0 0\n"
            + "motion C 2.20 -10 10 300 300 0 0 0   2.40 -10 10 300 300 0 0 0\n"
            + "motion C 2.40 -10 10 300 300 0 0 0   2.60 -10 10 300 300 0 0 12\n",
        circle.textOutput(5));
  }

  @Test
  public void testShapeTextOutputEllipse() {
    assertEquals("shape E ellipse\n", ellipse.toString());
    ellipse.addMotionToShape(changeColorEllipse1);
    assertEquals("shape E ellipse\n"
            + "motion E 1.00 0 0 100 100 0 0 0   5.00 0 0 100 100 244 232 102\n",
        ellipse.textOutput(1.0));

    ellipse.addMotionToShape(scaleEllipse1);
    assertEquals("shape E ellipse\n"
            + "motion E 1.00 0 0 100 100 244 232 102   5.00 0 0 100 100 244 232 102\n"
            + "motion E 5.00 0 0 100 100 244 232 102   10.00 0 0 200 200 244 232 102\n",
        ellipse.textOutput(1.0));
  }

  @Test
  public void testShapeTextOutputSquare() {
    assertEquals("shape S rectangle\n", square.toString());
    square.addMotionToShape(changeColorSquare1);
    square.addMotionToShape(scaleSquare1);
    square.addMotionToShape(moveSquare1);
    square.addMotionToShape(changeColorSquare2);

    assertEquals("shape S rectangle\n"
            + "motion S 0.43 0 0 100 100 0 0 0   6.52 0 0 100 100 0 0 0\n"
            + "motion S 6.52 0 0 100 100 0 0 0   7.83 0 0 100 100 190 100 200\n"
            + "motion S 7.83 0 0 100 100 190 100 200   8.70 0 0 100 100 190 100 200\n"
            + "motion S 8.70 0 0 100 100 190 100 200   10.87 0 0 150 150 190 100 200\n"
            + "motion S 9.57 0 0 150 150 190 100 200   10.00 0 0 150 150 220 80 230\n"
            + "motion S 10.87 0 0 150 150 220 80 230   21.74 0 0 150 150 220 80 230\n"
            + "motion S 21.74 0 0 150 150 220 80 230   43.48 3 -1 150 150 220 80 230\n",
        square.textOutput(2.3));
  }

  @Test
  public void testShapeTextOutputPlus() {
    assertEquals("shape P plus\n", plus.toString());
    plus.addMotionToShape(changeColorSquare1);
    plus.addMotionToShape(scaleSquare1);
    plus.addMotionToShape(moveSquare1);
    plus.addMotionToShape(changeColorSquare2);

    assertEquals("shape P plus\n"
            + "motion P 0.10 0 0 100 100 0 0 0   1.50 0 0 100 100 0 0 0\n"
            + "motion P 1.50 0 0 100 100 0 0 0   1.80 0 0 100 100 190 100 200\n"
            + "motion P 1.80 0 0 100 100 190 100 200   2.00 0 0 100 100 190 100 200\n"
            + "motion P 2.00 0 0 100 100 190 100 200   2.50 0 0 150 150 190 100 200\n"
            + "motion P 2.20 0 0 150 150 190 100 200   2.30 0 0 150 150 220 80 230\n"
            + "motion P 2.50 0 0 150 150 220 80 230   5.00 0 0 150 150 220 80 230\n"
            + "motion P 5.00 0 0 150 150 220 80 230   10.00 3 -1 150 150 220 80 230\n",
        plus.textOutput(10));
  }


  @Test
  public void testShapeEquals() {
    assertEquals(ellipse, new AnimatorEllipse("E"));
    assertEquals(circle, new AnimatorEllipse("C"));
    assertEquals(rectangle, new AnimatorRectangle("R"));
    assertEquals(square, new AnimatorRectangle("S"));
    assertEquals(plus, new AnimatorPlus("P"));

    assertEquals(squareCustom, new AnimatorRectangle("CustomS", 10, 10,
        new Color(0, 0, 50), new Position(50, 50), true));
    assertEquals(ellipseCustom, new AnimatorEllipse("CustomE", 130, 95,
        new Color(30, 150, 150), new Position(150, 32), false));
    assertEquals(circleCustom, new AnimatorEllipse("CustomC", 13, 13,
        new Color(200, 30, 60), new Position(0, 12), true));
    assertEquals(rectangleCustom, new AnimatorRectangle("CustomR", 50, 140,
        new Color(200, 150, 10), new Position(20.4, 50.7), false));
    assertEquals(plusCustom, new AnimatorPlus("CustomP", 10, 10,
        new Color(0, 0, 50), new Position(50, 50), true, 1));
  }

  @Test
  public void testShapeEqualsWithMotions() {
    squareCustom.addMotionToShape(moveSquare1);
    squareCustom.addMotionToShape(changeColorSquare1);
    IShape squareCustomCopy = (new AnimatorRectangle("CustomS", 10, 10,
        new Color(0, 0, 50), new Position(50, 50), true));
    squareCustomCopy.addMotionToShape(moveSquare1);
    squareCustomCopy.addMotionToShape(changeColorSquare1);

    assertEquals(squareCustom, squareCustomCopy);
    squareCustomCopy.addMotionToShape(scaleSquare1);
    assertNotEquals(squareCustom, squareCustomCopy);
  }

  @Test
  public void testShapeHashCode() {
    assertEquals(new AnimatorEllipse("E").hashCode(), ellipse.hashCode());
    assertEquals(new AnimatorEllipse("C").hashCode(), circle.hashCode());
    assertEquals(new AnimatorRectangle("R").hashCode(), rectangle.hashCode());
    assertEquals(new AnimatorRectangle("S").hashCode(), square.hashCode());
    assertEquals(new AnimatorPlus("P").hashCode(), plus.hashCode());

    assertEquals(new AnimatorEllipse("CustomE", 130, 95,
            new Color(30, 150, 150), new Position(150, 32), false).hashCode(),
        ellipseCustom.hashCode());
    assertEquals(new AnimatorEllipse("CustomC", 13, 13,
            new Color(200, 30, 60), new Position(0, 12), true).hashCode(),
        circleCustom.hashCode());
    assertEquals(new AnimatorRectangle("CustomR", 50, 140,
            new Color(200, 150, 10), new Position(20.4, 50.7),
            false).hashCode(),
        rectangleCustom.hashCode());
    assertEquals(new AnimatorRectangle("CustomS", 10, 10,
            new Color(0, 0, 50), new Position(50, 50), true).hashCode(),
        squareCustom.hashCode());
    assertEquals(new AnimatorPlus("CustomP", 10, 10,
        new Color(0, 0, 50), new Position(50, 50), true,
        1).hashCode(), plusCustom.hashCode());
  }
}
