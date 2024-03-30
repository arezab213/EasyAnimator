package viewtests;

import static org.junit.Assert.assertEquals;

import cs3500.animator.Excellence;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.EasyAnimator;
import cs3500.animator.model.Position;
import cs3500.animator.model.motions.ChangeColor;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.Move;
import cs3500.animator.model.motions.Scale;
import cs3500.animator.model.shapes.AnimatorEllipse;
import cs3500.animator.model.shapes.AnimatorRectangle;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextualView;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the methods for the {@code TextualView} class.
 */
public class TextualViewTest {

  AnimatorModel<IShape, Motion> model1;
  AnimatorModel<IShape, Motion> emptyModel;
  ByteArrayOutputStream out;
  StringBuilder sb;
  AnimatorView view1;
  AnimatorView view2;
  AnimatorView view3;

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
    model1 = new EasyAnimator(new ArrayList<>(), 0, 0, 500, 500);
    emptyModel = new EasyAnimator(new ArrayList<>());
    out = new ByteArrayOutputStream();
    sb = new StringBuilder();
    view1 = new TextualView(model1);
    view2 = new TextualView(2.5, model1, sb);
    view3 = new TextualView(2, emptyModel, sb);

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
  public void testConstructorException1() {
    AnimatorView errorConstruct = new TextualView(0, model1, sb);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException2() {
    AnimatorView errorConstruct = new TextualView(-1.7, model1, sb);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException3() {
    AnimatorView errorConstruct = new SVGView(2, null, sb);
  }

  // tests if the view outputs the correct String with this set of shapes and motions.
  // One view has the default ticksPerSec, the other has 2.5.
  @Test
  public void testToString1() {
    ellipse.addMotionToShape(moveEllipse1);
    circle.addMotionToShape(scaleCircle1);
    rectangle.addMotionToShape(changeColorRectangle1);
    square.addMotionToShape(moveSquare1);
    model1.addShape(ellipse);
    model1.addShape(circle);
    model1.addShape(rectangle);
    model1.addShape(square);

    assertEquals("canvas 0.0 0.0 500.0 500.0\n"
        + "shape E ellipse\n"
        + "motion E 1.00 0 0 100 100 0 0 0   20.00 50 50 100 100 0 0 0\n"
        + "\n"
        + "shape C ellipse\n"
        + "motion C 1.00 0 0 100 100 0 0 0   5.00 0 0 100 100 0 0 0\n"
        + "motion C 5.00 0 0 100 100 0 0 0   10.00 0 0 300 300 0 0 0\n"
        + "\n"
        + "shape R rectangle\n"
        + "motion R 1.00 0 0 100 100 0 0 0   10.00 0 0 100 100 50 20 3\n"
        + "\n"
        + "shape S rectangle\n"
        + "motion S 1.00 0 0 100 100 0 0 0   50.00 0 0 100 100 0 0 0\n"
        + "motion S 50.00 0 0 100 100 0 0 0   100.00 3 -1 100 100 0 0 0\n"
        + "\n", view1.toString());

    assertEquals("canvas 0.0 0.0 500.0 500.0\n"
        + "shape E ellipse\n"
        + "motion E 0.40 50 50 100 100 0 0 0   8.00 50 50 100 100 0 0 0\n"
        + "\n"
        + "shape C ellipse\n"
        + "motion C 0.40 0 0 300 300 0 0 0   2.00 0 0 300 300 0 0 0\n"
        + "motion C 2.00 0 0 300 300 0 0 0   4.00 0 0 900 900 0 0 0\n"
        + "\n"
        + "shape R rectangle\n"
        + "motion R 0.40 0 0 100 100 50 20 3   4.00 0 0 100 100 50 20 3\n"
        + "\n"
        + "shape S rectangle\n"
        + "motion S 0.40 3 -1 100 100 0 0 0   20.00 3 -1 100 100 0 0 0\n"
        + "motion S 20.00 3 -1 100 100 0 0 0   40.00 3 -1 100 100 0 0 0\n"
        + "\n", view2.toString());
  }

  // tests if the view outputs the correct String with a different set of shapes and motions.
  // One view has the default ticksPerSec, the other has 2.5.
  @Test
  public void testToString2() {
    square.addMotionToShape(scaleSquare1);
    square.addMotionToShape(changeColorSquare2);
    circle.addMotionToShape(moveCircle1);

    model1.addShape(square);
    model1.addShape(circle);

    assertEquals("canvas 0.0 0.0 500.0 500.0\n"
        + "shape S rectangle\n"
        + "motion S 1.00 0 0 100 100 0 0 0   20.00 0 0 100 100 0 0 0\n"
        + "motion S 20.00 0 0 100 100 0 0 0   25.00 0 0 150 150 0 0 0\n"
        + "motion S 22.00 0 0 150 150 0 0 0   23.00 0 0 150 150 220 80 230\n"
        + "\n"
        + "shape C ellipse\n"
        + "motion C 1.00 0 0 100 100 0 0 0   10.00 0 0 100 100 0 0 0\n"
        + "motion C 10.00 0 0 100 100 0 0 0   11.00 -10 10 100 100 0 0 0\n"
        + "\n", view1.toString());

    assertEquals("canvas 0.0 0.0 500.0 500.0\n"
        + "shape S rectangle\n"
        + "motion S 0.40 0 0 150 150 220 80 230   8.00 0 0 150 150 220 80 230\n"
        + "motion S 8.00 0 0 150 150 220 80 230   10.00 0 0 225 225 220 80 230\n"
        + "motion S 8.80 0 0 225 225 220 80 230   9.20 0 0 225 225 220 80 230\n"
        + "\n"
        + "shape C ellipse\n"
        + "motion C 0.40 -10 10 100 100 0 0 0   4.00 -10 10 100 100 0 0 0\n"
        + "motion C 4.00 -10 10 100 100 0 0 0   4.40 -10 10 100 100 0 0 0\n"
        + "\n", view2.toString());
  }

  @Test
  public void testToStringEmptyModel() {
    assertEquals("canvas 0.0 0.0 500.0 500.0\n", view3.toString());
  }

  // tests if the view outputs the correct String with this set of shapes and motions after
  // the method render is invoked.
  @Test
  public void testRender1() {
    ellipse.addMotionToShape(moveEllipse1);
    circle.addMotionToShape(scaleCircle1);
    rectangle.addMotionToShape(changeColorRectangle1);
    square.addMotionToShape(moveSquare1);
    model1.addShape(ellipse);
    model1.addShape(circle);
    model1.addShape(rectangle);
    model1.addShape(square);

    view2.render();
    assertEquals("canvas 0.0 0.0 500.0 500.0\n"
        + "shape E ellipse\n"
        + "motion E 0.40 50 50 100 100 0 0 0   8.00 50 50 100 100 0 0 0\n"
        + "\n"
        + "shape C ellipse\n"
        + "motion C 0.40 0 0 300 300 0 0 0   2.00 0 0 300 300 0 0 0\n"
        + "motion C 2.00 0 0 300 300 0 0 0   4.00 0 0 900 900 0 0 0\n"
        + "\n"
        + "shape R rectangle\n"
        + "motion R 0.40 0 0 100 100 50 20 3   4.00 0 0 100 100 50 20 3\n"
        + "\n"
        + "shape S rectangle\n"
        + "motion S 0.40 3 -1 100 100 0 0 0   20.00 3 -1 100 100 0 0 0\n"
        + "motion S 20.00 3 -1 100 100 0 0 0   40.00 3 -1 100 100 0 0 0\n"
        + "\n", view2.toString());
  }

  // tests if the view outputs the correct String with a different set of shapes and motions after
  // the method render is invoked.
  @Test
  public void testRender2() {
    square.addMotionToShape(scaleSquare1);
    square.addMotionToShape(changeColorSquare2);
    circle.addMotionToShape(moveCircle1);

    model1.addShape(square);
    model1.addShape(circle);

    view2.render();
    assertEquals("canvas 0.0 0.0 500.0 500.0\n"
        + "shape S rectangle\n"
        + "motion S 0.40 0 0 150 150 220 80 230   8.00 0 0 150 150 220 80 230\n"
        + "motion S 8.00 0 0 150 150 220 80 230   10.00 0 0 225 225 220 80 230\n"
        + "motion S 8.80 0 0 225 225 220 80 230   9.20 0 0 225 225 220 80 230\n"
        + "\n"
        + "shape C ellipse\n"
        + "motion C 0.40 -10 10 100 100 0 0 0   4.00 -10 10 100 100 0 0 0\n"
        + "motion C 4.00 -10 10 100 100 0 0 0   4.40 -10 10 100 100 0 0 0\n"
        + "\n", view2.toString());
  }

  @Test
  public void testRenderEmptyModel() {
    view3.render();
    assertEquals("canvas 0.0 0.0 500.0 500.0\n", sb.toString());
  }

  @Test
  public void testSmallDemoSystemOut() {
    System.setOut(new PrintStream(out));
    try {
      Excellence.main(new String[]{"-view", "text", "-in", "./resources/smalldemo.txt"});
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    assertEquals("canvas 200.0 70.0 360.0 360.0\n"
        + "shape R rectangle\n"
        + "motion R 1.00 200 200 50 100 255 0 0   10.00 200 200 50 100 255 0 0\n"
        + "motion R 10.00 200 200 50 100 255 0 0   50.00 300 300 50 100 255 0 0\n"
        + "motion R 50.00 300 300 50 100 255 0 0   51.00 300 300 50 100 255 0 0\n"
        + "motion R 51.00 300 300 50 100 255 0 0   70.00 300 300 25 100 255 0 0\n"
        + "motion R 70.00 300 300 25 100 255 0 0   100.00 200 200 25 100 255 0 0\n"
        + "\n"
        + "shape C ellipse\n"
        + "motion C 1.00 440 70 120 60 0 0 255   20.00 440 70 120 60 0 0 255\n"
        + "motion C 20.00 440 70 120 60 0 0 255   50.00 440 250 120 60 0 0 255\n"
        + "motion C 50.00 440 250 120 60 0 0 255   70.00 440 370 120 60 0 0 255\n"
        + "motion C 50.00 440 370 120 60 0 0 255   70.00 440 370 120 60 0 170 85\n"
        + "motion C 70.00 440 370 120 60 0 170 85   80.00 440 370 120 60 0 255 0\n"
        + "\n", out.toString());
  }

  @Test
  public void testSmallDemoFileOut() throws IOException {
    try {
      Excellence.main(new String[]{"-view", "text", "-in", "./resources/smalldemo.txt", "-out",
          "./resources/out.txt"});
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    Path path = Paths.get("./resources/out.txt");
    String contents = Files.readString(path);
    assertEquals("canvas 200.0 70.0 360.0 360.0\n"
        + "shape R rectangle\n"
        + "motion R 1.00 200 200 50 100 255 0 0   10.00 200 200 50 100 255 0 0\n"
        + "motion R 10.00 200 200 50 100 255 0 0   50.00 300 300 50 100 255 0 0\n"
        + "motion R 50.00 300 300 50 100 255 0 0   51.00 300 300 50 100 255 0 0\n"
        + "motion R 51.00 300 300 50 100 255 0 0   70.00 300 300 25 100 255 0 0\n"
        + "motion R 70.00 300 300 25 100 255 0 0   100.00 200 200 25 100 255 0 0\n"
        + "\n"
        + "shape C ellipse\n"
        + "motion C 1.00 440 70 120 60 0 0 255   20.00 440 70 120 60 0 0 255\n"
        + "motion C 20.00 440 70 120 60 0 0 255   50.00 440 250 120 60 0 0 255\n"
        + "motion C 50.00 440 250 120 60 0 0 255   70.00 440 370 120 60 0 0 255\n"
        + "motion C 50.00 440 370 120 60 0 0 255   70.00 440 370 120 60 0 170 85\n"
        + "motion C 70.00 440 370 120 60 0 170 85   80.00 440 370 120 60 0 255 0\n"
        + "\n", contents);
  }
}
