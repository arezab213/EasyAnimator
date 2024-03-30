package modeltests;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.EasyAnimator;
import cs3500.animator.model.Position;
import cs3500.animator.model.SpeedInterval;
import cs3500.animator.model.motions.ChangeColor;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.Move;
import cs3500.animator.model.motions.Scale;
import cs3500.animator.model.shapes.AnimatorEllipse;
import cs3500.animator.model.shapes.AnimatorRectangle;
import cs3500.animator.model.shapes.IShape;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the model implementation of AnimatorModel, ensuring that the public
 * interface methods work as intended.
 */
public class EasyAnimatorTest {
  // Initial Conditions for testing

  AnimatorModel<IShape, Motion> easyModel1;
  EasyAnimator.Builder builder;

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
    easyModel1 = new EasyAnimator();
    builder = new EasyAnimator.Builder();

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
  public void testNegativeWidth() {
    AnimatorModel<IShape, Motion> faultyModel = new EasyAnimator(new ArrayList<>(),
        0, 0, -1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeHeight() {
    AnimatorModel<IShape, Motion> faultyModel = new EasyAnimator(new ArrayList<>(),
        0, 0, 1, -10);
  }

  @Test
  public void testGetShapes0() {
    easyModel1.addShape(ellipse);
    easyModel1.addShape(rectangle);
    easyModel1.addShape(square);

    List<IShape> copy = new ArrayList<>();
    copy.add(ellipse.getShapeAtTick(0));
    copy.add(rectangle.getShapeAtTick(0));
    copy.add(square.getShapeAtTick(0));

    assertEquals(copy, easyModel1.getShapes(0));
  }

  @Test
  public void testGetShapes10() {
    ellipse.addMotionToShape(moveEllipse1);
    circle.addMotionToShape(scaleCircle1);

    easyModel1.addShape(ellipse);
    easyModel1.addShape(circle);

    List<IShape> copy = new ArrayList<>();
    copy.add(ellipse.getShapeAtTick(10));
    copy.add(circle.getShapeAtTick(10));

    assertEquals(copy, easyModel1.getShapes(10));
    assertEquals(copy.get(0).getWidth(),
        easyModel1.getShapes(10).get(0).getWidth(), 0.01);
  }

  @Test
  public void testDescribe() {
    ellipse.addMotionToShape(moveEllipse1);
    circle.addMotionToShape(scaleCircle1);

    easyModel1.addShape(ellipse);
    easyModel1.addShape(circle);

    assertEquals("shape E ellipse\n"
        + "motion E 1.00 0 0 100 100 0 0 0   20.00 50 50 100 100 0 0 0\n"
        + "\n"
        + "shape C ellipse\n"
        + "motion C 1.00 0 0 100 100 0 0 0   5.00 0 0 100 100 0 0 0\n"
        + "motion C 5.00 0 0 100 100 0 0 0   10.00 0 0 300 300 0 0 0\n"
        + "\n", easyModel1.describe(1));

    circle.addMotionToShape(moveCircle1);
    square.addMotionToShape(scaleSquare1);
    rectangle.addMotionToShape(changeColorRectangle1);
    rectangle.addMotionToShape(moveRectangle1);

    easyModel1.addShape(rectangle);
    easyModel1.addShape(square);

    assertEquals("shape E ellipse\n"
            + "motion E 1.00 50 50 100 100 0 0 0   20.00 50 50 100 100 0 0 0\n"
            + "\n"
            + "shape C ellipse\n"
            + "motion C 1.00 0 0 300 300 0 0 0   5.00 0 0 300 300 0 0 0\n"
            + "motion C 5.00 0 0 300 300 0 0 0   10.00 0 0 900 900 0 0 0\n"
            + "motion C 10.00 0 0 900 900 0 0 0   11.00 -10 10 900 900 0 0 0\n"
            + "\n"
            + "shape R rectangle\n"
            + "motion R 1.00 0 0 100 100 0 0 0   10.00 0 0 100 100 50 20 3\n"
            + "motion R 10.00 0 0 100 100 50 20 3   15.00 0 0 100 100 50 20 3\n"
            + "motion R 15.00 0 0 100 100 50 20 3   22.00 -7 -2 100 100 50 20 3\n"
            + "\n"
            + "shape S rectangle\n"
            + "motion S 1.00 0 0 100 100 0 0 0   20.00 0 0 100 100 0 0 0\n"
            + "motion S 20.00 0 0 100 100 0 0 0   25.00 0 0 150 150 0 0 0\n\n",
        easyModel1.describe(1));
  }

  @Test
  public void testAddShape() {
    assertEquals(0, easyModel1.getShapes(10).size());
    easyModel1.addShape(ellipse);
    assertEquals(1, easyModel1.getShapes(8).size());
    easyModel1.addShape(circle);
    assertEquals(2, easyModel1.getShapes(12).size());
    easyModel1.addShape(rectangle);
    assertEquals(3, easyModel1.getShapes(1).size());
    easyModel1.addShape(square);
    assertEquals(4, easyModel1.getShapes(50).size());
  }

  @Test
  public void testRemoveShape() {
    assertEquals(0, easyModel1.getShapes(10).size());
    easyModel1.addShape(ellipse);
    easyModel1.addShape(rectangle);
    easyModel1.addShape(square);
    easyModel1.addShape(circle);
    assertEquals(4, easyModel1.getShapes(8).size());
    easyModel1.removeShape(circle);
    assertEquals(3, easyModel1.getShapes(8).size());
    easyModel1.removeShape(square);
    assertEquals(2, easyModel1.getShapes(8).size());
    easyModel1.removeShape(rectangle);
    assertEquals(1, easyModel1.getShapes(8).size());
    easyModel1.removeShape(ellipse);
    assertEquals(0, easyModel1.getShapes(8).size());
  }

  @Test
  public void testAddMotionToModel() {
    easyModel1.addShape(ellipse);
    easyModel1.addShape(rectangle);
    easyModel1.addShape(square);
    easyModel1.addShape(circle);

    easyModel1.addMotionToModel(0, scaleEllipse1);
    assertEquals(1, easyModel1.getShapes(10).get(0).getMotions().size());
    easyModel1.addMotionToModel(0, moveEllipse1);
    easyModel1.addMotionToModel(0, changeColorEllipse1);
    assertEquals(3, easyModel1.getShapes(10).get(0).getMotions().size());

    easyModel1.addMotionToModel(3, moveCircle1);
    assertEquals(1, easyModel1.getShapes(10).get(3).getMotions().size());
    easyModel1.addMotionToModel(3, changeColorCircle1);
    easyModel1.addMotionToModel(3, scaleCircle1);
    assertEquals(3, easyModel1.getShapes(10).get(3).getMotions().size());
  }

  @Test
  public void testRemoveMotionFromModel() {
    easyModel1.addShape(ellipse);
    easyModel1.addShape(rectangle);
    easyModel1.addShape(square);
    easyModel1.addShape(circle);

    easyModel1.addMotionToModel(0, scaleEllipse1);
    easyModel1.addMotionToModel(0, moveEllipse1);
    easyModel1.addMotionToModel(0, changeColorEllipse1);
    assertEquals(3, easyModel1.getShapes(10).get(0).getMotions().size());
    easyModel1.removeMotionFromModel(0, scaleEllipse1);
    assertEquals(2, easyModel1.getShapes(10).get(0).getMotions().size());
    easyModel1.removeMotionFromModel(0, moveEllipse1);
    assertEquals(1, easyModel1.getShapes(10).get(0).getMotions().size());
    easyModel1.removeMotionFromModel(0, changeColorEllipse1);
    assertEquals(0, easyModel1.getShapes(10).get(0).getMotions().size());
  }

  @Test
  public void testGetMotions() {
    ellipse.addMotionToShape(scaleEllipse1);
    ellipse.addMotionToShape(moveEllipse1);
    easyModel1.addShape(ellipse);
    easyModel1.addShape(rectangle);
    easyModel1.addShape(square);
    easyModel1.addShape(circle);

    assertEquals(ellipse.getMotions().size(), easyModel1.getMotions(0).size());
  }

  @Test
  public void testGetShape() {
    ellipse.addMotionToShape(moveEllipse1);
    circle.addMotionToShape(scaleCircle1);

    easyModel1.addShape(ellipse);
    easyModel1.addShape(circle);

    List<IShape> copy = new ArrayList<>();
    copy.add(ellipse.getShapeAtTick(1));
    copy.add(circle.getShapeAtTick(1));

    assertEquals(copy.get(0), easyModel1.getShape(0, 1));
    assertEquals(copy.get(0).getWidth(),
        easyModel1.getShape(0, 10).getWidth(), 0.01);
    assertEquals(copy.get(0).getHeight(),
        easyModel1.getShape(0, 10).getHeight(), 0.01);
  }

  @Test
  public void testGetFinalTick() {
    ellipse.addMotionToShape(moveEllipse1);
    circle.addMotionToShape(scaleCircle1);
    easyModel1.addShape(ellipse);
    easyModel1.addShape(circle);

    assertEquals(20, easyModel1.getFinalTick());
  }

  @Test
  public void testGetNextDiscreteTick() {
    ellipse.addMotionToShape(moveEllipse1);
    circle.addMotionToShape(scaleCircle1);
    easyModel1.addShape(ellipse);
    easyModel1.addShape(circle);

    assertEquals(5, easyModel1.getNextDiscreteTick(2));
    assertEquals(10, easyModel1.getNextDiscreteTick(6));
    assertEquals(20, easyModel1.getNextDiscreteTick(11));
  }

  // tests that the addSpeedInterval throws errors properly when overlapping intervals are
  // attempted to be added
  @Test(expected = IllegalArgumentException.class)
  public void testAddSpeedIntervalThrowsInvalidStart() {
    easyModel1.addSpeedInterval(1, 10, 1);
    easyModel1.addSpeedInterval(12, 14, 1);
    // overlapping start
    easyModel1.addSpeedInterval(6, 25, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddSpeedIntervalThrowsInvalidEnd() {
    easyModel1.addSpeedInterval(5, 10, 1);
    easyModel1.addSpeedInterval(12, 14, 1);
    // overlapping end
    easyModel1.addSpeedInterval(1, 6, 1);
  }

  // tests the functionality of adding and getting speed intervals to/from a model
  @Test
  public void testAddSpeedIntervalsAndGetSpeedIntervals() {
    easyModel1.addSpeedInterval(1, 10, 1);
    easyModel1.addSpeedInterval(12, 14, 1);
    easyModel1.addSpeedInterval(20, 25, 1);

    assertEquals(3, easyModel1.getSpeedIntervals().size());
    assertEquals(10, easyModel1.getSpeedIntervals().get(0).getEndTick());
    assertEquals(12, easyModel1.getSpeedIntervals().get(1).getStartTick());
    assertEquals(1, easyModel1.getSpeedIntervals().get(2).getSpeed());
  }

  @Test
  public void testBuilder() {
    // for testing setBounds()
    this.builder.setBounds(-3, -3, 300, 340);
    // for testing declareShapes()
    this.builder.declareShape("R1", "rectangle");
    this.builder.declareShape("E2", "ellipse");
    // for testing addMotion()
    this.builder.addMotion("R1", 1, 0, 0, 30, 30, 0,
        0, 0, 10, 150, 150, 60, 60, 255, 255, 255);
    this.builder
        .addMotion("E2", 1, 10, 12, 10, 30, 255, 255,
            255, 10, 10, 12, 30, 60, 255, 255, 255);

    AnimatorModel<IShape, Motion> testModel = this.builder.build();
    // tests if resulting model has correct minX, minY, width, and height values
    assertEquals(-3, testModel.getMinPos().getX(), .001);
    assertEquals(-3, testModel.getMinPos().getY(), .001);
    assertEquals(300,
        (testModel.getMaxPos().getX() - testModel.getMinPos().getX()), .001);
    assertEquals(300,
        (testModel.getMaxPos().getX() - testModel.getMinPos().getX()), .001);
    // tests if resulting model has the correct list size for its shapes list
    // as well as the correct sizes for each shape's motion lists
    assertEquals(2, testModel.getShapes(1).size());
    assertEquals(3, testModel.getShapes(1).get(0).getMotions().size());
    assertEquals(1, testModel.getShapes(1).get(1).getMotions().size());
    // tests if the models shapes are updated properly at the given times
    // rectangle position:
    assertEquals(0,
        testModel.getShapes(1).get(0).getPosition().getX(), .001);
    assertEquals(0,
        testModel.getShapes(1).get(0).getPosition().getY(), .001);
    assertEquals(150,
        testModel.getShapes(10).get(0).getPosition().getX(), .001);
    assertEquals(150,
        testModel.getShapes(10).get(0).getPosition().getX(), .001);
    // rectangle dimensions:
    assertEquals(30,
        testModel.getShapes(1).get(0).getWidth(), .001);
    assertEquals(30,
        testModel.getShapes(1).get(0).getHeight(), .001);
    assertEquals(60,
        testModel.getShapes(10).get(0).getWidth(), .001);
    assertEquals(60,
        testModel.getShapes(10).get(0).getHeight(), .001);
    // rectangle colors:
    assertEquals(0, testModel.getShapes(1).get(0).getColor().getRed());
    assertEquals(0, testModel.getShapes(1).get(0).getColor().getBlue());
    assertEquals(0, testModel.getShapes(1).get(0).getColor().getGreen());
    assertEquals(255, testModel.getShapes(10).get(0).getColor().getRed());
    assertEquals(255, testModel.getShapes(10).get(0).getColor().getBlue());
    assertEquals(255, testModel.getShapes(10).get(0).getColor().getGreen());
    // ellipse dimensions:
    assertEquals(10, testModel.getShapes(1).get(1).getWidth(), .001);
    assertEquals(30, testModel.getShapes(1).get(1).getHeight(), .001);
    assertEquals(30, testModel.getShapes(10).get(1).getWidth(), .001);
    assertEquals(60, testModel.getShapes(10).get(1).getHeight(), .001);
  }

  @Test
  public void testBuilderSlowMo() {
    // For slow-mo interval
    this.builder.declareSlowMo(5, 10, 2);

    AnimatorModel<IShape, Motion> testModel = this.builder.build();

    assertEquals(new SpeedInterval(5, 10, 2),
        testModel.getSpeedIntervals().get(0));
  }
}
