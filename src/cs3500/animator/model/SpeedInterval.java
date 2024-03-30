package cs3500.animator.model;


import java.util.Objects;

/**
 * Represents a time interval during an animation with a specified playback speed.
 */
public class SpeedInterval {

  private final int startTick;
  private final int endTick;
  private final int speed;

  /**
   * Constructs a {@code SpeedInterval} object.
   *
   * @param startTick the beginning tick of this slow motion period.
   * @param endTick   the end tick of this slow motion period.
   * @param speed     the animation speed of this slow motion period.
   */
  public SpeedInterval(int startTick, int endTick, int speed) throws IllegalArgumentException {
    if (endTick <= startTick) {
      throw new IllegalArgumentException("End tick must come after start tick");
    }
    if (speed <= 0) {
      throw new IllegalArgumentException("Interval speed must be positive");
    }
    if (startTick < 0) {
      throw new IllegalArgumentException("Start tick for interval must not be negative");
    }
    this.startTick = startTick;
    this.endTick = endTick;
    this.speed = speed;
  }

  /**
   * Gets the start tick of this interval.
   *
   * @return the start tick of the slow motion period.
   */
  public int getStartTick() {
    return this.startTick;
  }

  /**
   * Gets the end tick of this interval.
   *
   * @return the end tick of the slow motion period.
   */
  public int getEndTick() {
    return this.endTick;
  }

  /**
   * Gets the animation speed of this interval.
   *
   * @return the animation speed of the slow motion period.
   */
  public int getSpeed() {
    return this.speed;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof SpeedInterval)) {
      return false;
    }
    SpeedInterval a = (SpeedInterval) that;

    return (this.startTick == a.startTick && this.endTick == a.endTick && this.speed == a.speed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.startTick, this.endTick, this.speed);
  }
}
