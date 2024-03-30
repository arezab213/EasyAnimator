package cs3500.animator.model.motions;

/**
 * Represents an abstract motion.
 */
public abstract class AbstractMotion implements Motion {

  protected int tStart;
  protected int tEnd;

  /**
   * Constructs a {@code AbstractMotion} object.
   *
   * @param tStart The start tick of the motion.
   * @param tEnd   The end tick of the motion.
   * @throws IllegalArgumentException if the start tick is greater than or equal to  the end tick,
   *                                  or if either the start or end tick is non-positive
   */
  AbstractMotion(int tStart, int tEnd) throws IllegalArgumentException {
    if (tStart > tEnd) {
      throw new IllegalArgumentException("Start tick must come before the end tick.");
    }
    if (tStart < 0) {
      throw new IllegalArgumentException("Start and end ticks must be non negative.");
    }
    this.tStart = tStart;
    this.tEnd = tEnd;
  }

  @Override
  public boolean timeContains(int time) {
    return tStart < time && tEnd > time;
  }

  @Override
  public int getStartTick() {
    return this.tStart;
  }

  @Override
  public int getEndTick() {
    return this.tEnd;
  }
}
