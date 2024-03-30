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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the {@code SVGView} class. Also tests the functionality of the {@code
 * MotionVisitor} implementation as its string outputs are utilized by the SVGView to form the
 * resulting textual representation of the SVG file for the animation represented by the given
 * model.
 */
public class SVGViewTest {

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
    AnimatorView errorConstruct = new SVGView(0, model1, sb);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException2() {
    AnimatorView errorConstruct = new SVGView(-1.7, model1, sb);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException3() {
    AnimatorView errorConstruct = new SVGView(2, null, sb);
  }

  @Test
  public void testSVGSmallDemoSystemOut() throws IOException {
    System.setOut(new PrintStream(out));
    try {
      Excellence.main(new String[]{"-view", "svg", "-in", "./resources/smalldemo.txt"});
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    Path expPath = Paths.get("./resources/smalldemo.svg");
    String contents = Files.readString(expPath);
    // determining equivalence between the system's output and the actual SVG file
    assertEquals(contents, out.toString());
  }

  @Test
  public void testSVGSmallDemoFileOut() throws IOException {
    try {
      Excellence.main(new String[]{"-view", "svg", "-in", "./resources/smalldemo.txt", "-out",
          "./resources/out.svg"});
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    Path actPath = Paths.get("./resources/out.svg");
    String actContents = Files.readString(actPath);

    Path expPath = Paths.get("./resources/smalldemo.svg");
    String expContents = Files.readString(expPath);
    // determining equivalence between the created file's contents and the actual SVG file
    assertEquals(expContents, actContents);
  }

  @Test
  public void testCustomSVGAppendableOut() {
    ellipse.setVisibleStartTick(1);
    ellipse.addMotionToShape(scaleEllipse1);

    model1.addShape(ellipse);

    AnimatorView svgView1 = new SVGView(30, model1, sb);
    svgView1.render();
    // determining equivalence between the appended
    // resulting to StringBuilder to the expected string
    assertEquals("<svg width=\"500.0\" height=\"500.0\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<ellipse id=\"E\" cx=\"50.0\" cy=\"50.0\" rx=\"50.0\" ry=\"50.0\" "
        + "fill=\"rgb(0,0,0)\" visibility=\"hidden\" >\n"
        + "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" "
        + "begin=\"33.3ms\" dur=\"1s\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"166.7ms\" dur=\"166.7ms\" "
        + "attributeName=\"rx\" from=\"50.0\" to=\"100.0\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"166.7ms\" dur=\"166.7ms\" "
        + "attributeName=\"ry\" from=\"50.0\" to=\"100.0\" fill=\"freeze\" />\n"
        + "</ellipse>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void testSVGAppendableOutEmpty() {

    AnimatorView svgView1 = new SVGView(30, model1, sb);
    svgView1.render();
    // determining equivalence between the appended
    // resulting to StringBuilder to the expected string
    assertEquals("<svg width=\"500.0\" height=\"500.0\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void testCustomSVGFileOut() throws IOException {
    circle.setVisibleStartTick(1);
    circle.addMotionToShape(changeColorCircle1);

    model1.addShape(circle);

    Writer out = new FileWriter("./resources/out.svg");
    AnimatorView svgView1 = new SVGView(30, model1, out);
    svgView1.render();
    out.flush();
    Path path = Paths.get("./resources/out.svg");
    String contents = Files.readString(path);
    // determining equivalence between the outputted file and expected String content
    assertEquals("<svg width=\"500.0\" height=\"500.0\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<ellipse id=\"C\" cx=\"50.0\" cy=\"50.0\" rx=\"50.0\" ry=\"50.0\" "
        + "fill=\"rgb(0,0,0)\" visibility=\"hidden\" >\n"
        + "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" "
        + "begin=\"33.3ms\" dur=\"1s\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"400.0ms\" dur=\"33.3ms\" "
        + "attributeName=\"fill\" from=\"rgb(0,0,0)\" to=\"rgb(0,0,12)\" fill=\"freeze\" />\n"
        + "</ellipse>\n"
        + "</svg>", contents);
  }

  @Test
  public void testSVGFileOutEmpty() throws IOException {
    Writer out = new FileWriter("./resources/out.svg");
    AnimatorView svgView1 = new SVGView(30, model1, out);
    svgView1.render();
    out.flush();
    Path path = Paths.get("./resources/out.svg");
    String contents = Files.readString(path);
    // determining equivalence between the outputted file and expected String content
    assertEquals("<svg width=\"500.0\" height=\"500.0\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "</svg>", contents);
  }

  @Test
  public void testSVGFileOutPlus() throws IOException {
    try {
      Excellence.main(new String[]{"-view", "svg", "-in", "./resources/plusAcrossScreen", "-out",
          "./resources/simplePlus.svg"});
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    Path actPath = Paths.get("./resources/simplePlus.svg");
    String actContents = Files.readString(actPath);

    Path expPath = Paths.get("./resources/plusOut.svg");
    String expContents = Files.readString(expPath);
    // determining equivalence between the created file's contents and the actual SVG file
    assertEquals(expContents, actContents);
  }
}
