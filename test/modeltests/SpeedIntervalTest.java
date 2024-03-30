package modeltests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.SpeedInterval;
import org.junit.Test;

/**
 * Tests the functionality of the methods of the {@code SpeedInterval} class.
 */
public class SpeedIntervalTest {

  SpeedInterval int1 = new SpeedInterval(2, 6, 10);
  SpeedInterval int2 = new SpeedInterval(10, 100, 5);
  SpeedInterval int3 = new SpeedInterval(0, 46, 3);
  SpeedInterval int4 = new SpeedInterval(2, 6, 10);


  @Test(expected = IllegalArgumentException.class)
  public void testEndTickBeforeStartConstruct() {
    SpeedInterval errorConstruct = new SpeedInterval(10, 6, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonPositiveSpeed() {
    SpeedInterval errorConstruct = new SpeedInterval(6, 8, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroSpeed() {
    SpeedInterval errorConstruct = new SpeedInterval(6, 8, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeStart() {
    SpeedInterval errorConstruct = new SpeedInterval(6, 8, -1);
  }

  @Test
  public void testGetStartTick() {
    assertEquals(2, int1.getStartTick());
    assertEquals(10, int2.getStartTick());
    assertEquals(0, int3.getStartTick());
  }

  @Test
  public void testGetEndTick() {
    assertEquals(6, int1.getEndTick());
    assertEquals(100, int2.getEndTick());
    assertEquals(46, int3.getEndTick());
  }

  @Test
  public void testGetSpeed() {
    assertEquals(10, int1.getSpeed());
    assertEquals(5, int2.getSpeed());
    assertEquals(3, int3.getSpeed());
  }

  @Test
  public void testSpeedIntervalEquals() {
    assertTrue(int1.equals(int4));
    assertTrue(int4.equals(int1));
    assertFalse(int2.equals(int3));
    assertFalse(int3.equals(int2));
  }

  @Test
  public void testSpeedIntervalHashcode() {
    assertEquals(new SpeedInterval(2, 6, 10).hashCode(), int1.hashCode());
    assertEquals(new SpeedInterval(10, 100, 5).hashCode(), int2.hashCode());
    assertEquals(new SpeedInterval(0, 46, 3).hashCode(), int3.hashCode());
    assertNotEquals(int2.hashCode(), int3.hashCode());
    assertNotEquals(int4.hashCode(), int2.hashCode());
  }
}
