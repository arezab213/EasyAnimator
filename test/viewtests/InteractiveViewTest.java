package viewtests;

import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.Controller;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.EasyAnimator;
import cs3500.animator.model.Position;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.Move;
import cs3500.animator.model.motions.Scale;
import cs3500.animator.model.shapes.AnimatorEllipse;
import cs3500.animator.model.shapes.AnimatorRectangle;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.InteractiveView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.VisualView;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the methods of the {@code InteractiveView} class.
 */
public class InteractiveViewTest {

  AnimatorModel<IShape, Motion> model1;
  AnimatorView view;
  ByteArrayOutputStream out;
  StringBuilder sb;
  AnimatorView view2;
  AnimatorView view3;
  AnimatorView view4;

  IShape ellipse;
  IShape square;

  Motion moveEllipse1;
  Motion scaleSquare1;

  @Before
  public void setInitialData() {
    model1 = new EasyAnimator(new ArrayList<>(), 0, 0, 500, 500);
    view = new InteractiveView(1, model1);
    out = new ByteArrayOutputStream();
    sb = new StringBuilder();
    view2 = new TextualView(model1);
    view3 = new SVGView(1, model1, sb);
    view4 = new VisualView(1, model1);

    // examples of shapes
    ellipse = new AnimatorEllipse("E");
    square = new AnimatorRectangle("S");

    // examples of motions
    moveEllipse1 =
        new Move(1, 3, new Position(50, 50));
    scaleSquare1 =
        new Scale(20, 25, 1.5, 1.5);
  }

  // Tests that the interactive methods throw errors when called on a textual view
  @Test(expected = UnsupportedOperationException.class)
  public void testTextualViewExceptionSetButtonListeners() {
    view2.setButtonListeners(new Controller(view2));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testTextualViewExceptionResetTimer() {
    view2.resetTimer(1);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testTextualViewExceptionCancelTimer() {
    view2.cancelTimer();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testTextualViewExceptionGetCurrentTick() {
    view2.getCurrentTick();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testTextualViewExceptionToggleLoop() {
    view2.toggleLoop();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testTextualViewExceptionIncrementSpeed() {
    view2.incrementSpeed();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testTextualViewExceptionDecrementSpeed() {
    view2.decrementSpeed();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testTextualViewExceptionToggleOutline() {
    view2.toggleOutline();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testTextualViewExceptionToggleDiscrete() {
    view2.toggleDiscrete();
  }

  // Tests that the interactive methods throw errors when called on a SVG view
  @Test(expected = UnsupportedOperationException.class)
  public void testSVGViewExceptionSetButtonListeners() {
    view3.setButtonListeners(new Controller(view3));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSVGViewExceptionResetTimer() {
    view3.resetTimer(1);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSVGViewExceptionCancelTimer() {
    view3.cancelTimer();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSVGViewExceptionGetCurrentTick() {
    view3.getCurrentTick();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSVGViewExceptionToggleLoop() {
    view3.toggleLoop();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSVGViewExceptionIncrementSpeed() {
    view3.incrementSpeed();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSVGViewExceptionDecrementSpeed() {
    view3.decrementSpeed();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSVGViewExceptionToggleOutline() {
    view3.toggleOutline();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSVGViewExceptionToggleDiscrete() {
    view3.toggleDiscrete();
  }

  // Tests that the interactive methods throw errors when called on a visual view
  @Test(expected = UnsupportedOperationException.class)
  public void testVisualViewExceptionSetButtonListeners() {
    view4.setButtonListeners(new Controller(view3));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testVisualViewExceptionResetTimer() {
    view4.resetTimer(1);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testVisualViewExceptionCancelTimer() {
    view4.cancelTimer();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testVisualViewExceptionGetCurrentTick() {
    view4.getCurrentTick();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testVisualViewExceptionToggleLoop() {
    view4.toggleLoop();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testVisualViewExceptionIncrementSpeed() {
    view4.incrementSpeed();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testVisualViewExceptionDecrementSpeed() {
    view4.decrementSpeed();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testVisualViewExceptionToggleOutline() {
    view4.toggleOutline();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testVisualViewExceptionToggleDiscrete() {
    view4.toggleDiscrete();
  }

  // Interactive view tests
  // Note that the tests that use the Thread.sleep method do use a delta to account for
  // small inconsistencies with the timers. These tests still ensure proper functionality
  // but sometimes failed without the included delta values.

  // Tests that the restTimer method throws an exception when given a negative tick value
  @Test(expected = IllegalArgumentException.class)
  public void testResetTimerNegativeTickValue() {
    view.resetTimer(-2);
  }

  @Test
  // tests if the timer is reset to the correct tick, also tests the
  // functionality of the getCurrentTick method
  public void testResetTimerValidTickValue() {
    view.resetTimer(10);
    assertEquals(10, view.getCurrentTick());
    view.resetTimer(5);
    assertEquals(5, view.getCurrentTick());
  }

  // Tests that the timer stays at 1 after the cancelTimer method is called
  @Test
  public void testCancel() throws InterruptedException {
    assertEquals(1, view.getCurrentTick());
    view.cancelTimer();
    Thread.sleep(1000);
    assertEquals(1, view.getCurrentTick());
  }

  // Tests the functionality of toggleLoop by ensuring that
  // the timer is reset at the correct moments (when loop is toggled
  // and when the timer exceeds the final tick).
  @Test
  public void testLoop() throws InterruptedException {
    ellipse.addMotionToShape(moveEllipse1);
    model1.addShape(ellipse);
    view.resetTimer(1);
    view.toggleLoop();
    assertEquals(1, view.getCurrentTick());
    Thread.sleep(2000);
    assertEquals(3, view.getCurrentTick());
    Thread.sleep(2000);
    assertEquals(2, view.getCurrentTick());

    // timer stays at 4 (last tick) when loop is not toggled
    view.toggleLoop();
    Thread.sleep(5000);
    assertEquals(4, view.getCurrentTick());
  }

  @Test
  public void testIncrementSpeed() throws InterruptedException {
    ellipse.addMotionToShape(moveEllipse1);
    ellipse.addMotionToShape(scaleSquare1);
    model1.addShape(ellipse);
    view.resetTimer(1);
    assertEquals(1, view.getCurrentTick());
    Thread.sleep(1000);
    assertEquals(2, view.getCurrentTick());
    // increase speed to 3
    view.incrementSpeed();
    // increase speed to 5
    view.incrementSpeed();
    // increase speed to 7
    view.incrementSpeed();
    // increase speed to 9
    view.incrementSpeed();
    // increase speed to 11
    view.incrementSpeed();
    Thread.sleep(1000);
    assertEquals(14, view.getCurrentTick(), 2);
    // speed at 11, so increments by 10
    // increase speed to 21
    view.incrementSpeed();
    view.resetTimer(1);
    Thread.sleep(1000);
    assertEquals(22, view.getCurrentTick(), 3);
  }

  @Test
  public void testDecrementSpeed() throws InterruptedException {
    ellipse.addMotionToShape(moveEllipse1);
    ellipse.addMotionToShape(scaleSquare1);
    model1.addShape(ellipse);
    view.resetTimer(1);
    assertEquals(1, view.getCurrentTick());
    Thread.sleep(1000);
    assertEquals(2, view.getCurrentTick(), 2);
    view.incrementSpeed();
    view.incrementSpeed();
    view.incrementSpeed();
    view.incrementSpeed();
    view.incrementSpeed();
    view.incrementSpeed();
    // speed increased to 21
    view.resetTimer(1);
    Thread.sleep(1000);
    assertEquals(22, view.getCurrentTick(), 3);
    // speed decreased to 11
    view.decrementSpeed();
    view.resetTimer(1);
    Thread.sleep(1000);
    assertEquals(13, view.getCurrentTick(), 2);
    // speed decreased to 9
    view.decrementSpeed();
    // speed decreased to 7
    view.decrementSpeed();
    // speed decreased to 5
    view.decrementSpeed();
    // speed decreased to 3
    view.decrementSpeed();
    // speed decreased to 1
    view.decrementSpeed();
    Thread.sleep(1000);
    assertEquals(13, view.getCurrentTick(), 2);
  }

  @Test
  public void testIncrementSpeedWhenPaused() throws InterruptedException {
    ellipse.addMotionToShape(moveEllipse1);
    ellipse.addMotionToShape(scaleSquare1);
    model1.addShape(ellipse);
    view.resetTimer(1);
    assertEquals(1, view.getCurrentTick(), 2);
    Thread.sleep(1000);
    assertEquals(2, view.getCurrentTick(), 2);
    // paused
    view.cancelTimer();
    view.incrementSpeed();
    view.incrementSpeed();
    view.incrementSpeed();
    view.incrementSpeed();
    view.incrementSpeed();
    // increased speed to 11 while paused
    view.resetTimer(1);
    Thread.sleep(1000);
    assertEquals(13, view.getCurrentTick(), 2);
    // speed at 11, so increments by 10
    // increase speed to 21
    view.incrementSpeed();
    Thread.sleep(1000);
    assertEquals(26, view.getCurrentTick(), 2);
  }

  @Test
  public void testDecrementSpeedWhenPaused() throws InterruptedException {
    ellipse.addMotionToShape(moveEllipse1);
    ellipse.addMotionToShape(scaleSquare1);
    model1.addShape(ellipse);
    view.resetTimer(1);
    assertEquals(1, view.getCurrentTick());
    Thread.sleep(1000);
    assertEquals(2, view.getCurrentTick(), 2);
    // paused
    view.cancelTimer();
    view.incrementSpeed();
    view.incrementSpeed();
    view.incrementSpeed();
    view.incrementSpeed();
    view.incrementSpeed();
    // increased speed to 11 while paused
    view.resetTimer(1);
    Thread.sleep(1000);
    assertEquals(14, view.getCurrentTick(), 2);
    view.cancelTimer();
    // Decrements speed down by 10 since speed is 11 while paused
    view.decrementSpeed();
    view.resetTimer(14);
    Thread.sleep(1000);
    assertEquals(15, view.getCurrentTick(), 2);
  }

  @Test
  public void toggleLoopWhenPaused() throws InterruptedException {
    ellipse.addMotionToShape(moveEllipse1);
    model1.addShape(ellipse);
    // pause the animation and toggle loop
    view.cancelTimer();
    view.toggleLoop();
    assertEquals(1, view.getCurrentTick(), 2);
    Thread.sleep(2000);
    assertEquals(1, view.getCurrentTick(), 2);
    // resume animation
    view.resetTimer(1);
    // timer stays at 4 (last tick) when loop is not toggled
    view.toggleLoop();
    Thread.sleep(5000);
    assertEquals(4, view.getCurrentTick(), 2);
  }

  @Test
  public void testToggleDiscrete() throws InterruptedException {
    ellipse.addMotionToShape(moveEllipse1);
    ellipse.addMotionToShape(scaleSquare1);
    model1.addShape(ellipse);

    view.resetTimer(1);
    assertEquals(1, view.getCurrentTick());
    view.toggleDiscrete();
    assertEquals(100,
        ellipse.getShapeAtTick(view.getCurrentTick()).getHeight(), 0.01);
    assertEquals(100,
        ellipse.getShapeAtTick(view.getCurrentTick()).getWidth(), 0.01);
    Thread.sleep(1050);
    assertEquals(new Position(50, 50),
        ellipse.getShapeAtTick(view.getCurrentTick()).getPosition());
    Thread.sleep(1000);
    assertEquals(110,
        ellipse.getShapeAtTick(view.getCurrentTick()).getHeight(), 0.01);
    assertEquals(110,
        ellipse.getShapeAtTick(view.getCurrentTick()).getWidth(), 0.01);
  }
}
