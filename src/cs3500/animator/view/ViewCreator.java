package cs3500.animator.view;

import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.shapes.IShape;

/**
 * Class to create an instance of a {@code AnimatorView}.
 */
public class ViewCreator {

  /**
   * Creates an {@code AnimatorView} object.
   *
   * @param type        The type of view specified
   * @param ticksPerSec The speed for the view
   * @param model       The model being represented by the view
   * @param ap          The {@code Appendable} used for the view
   * @return A new view
   * @throws IllegalArgumentException if the specified view type is not valid
   */
  public static AnimatorView create(String type, double ticksPerSec,
      IViewModel<IShape, Motion> model, Appendable ap) throws IllegalArgumentException {
    switch (type) {
      case "text":
        return new TextualView(ticksPerSec, model, ap);
      case "visual":
        return new VisualView(ticksPerSec, model);
      case "svg":
        return new SVGView(ticksPerSec, model, ap);
      case "interactive":
        return new InteractiveView(ticksPerSec, model);
      default:
        throw new IllegalArgumentException("Invalid view type");
    }
  }
}
