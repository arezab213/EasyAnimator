package cs3500.animator.adapter;


import cs3500.animator.provider.view.EasyAnimatorInteractiveView;
import cs3500.animator.provider.view.EasyAnimatorTextualView;
import cs3500.animator.provider.view.EasyAnimatorView;
import cs3500.animator.provider.view.EasyAnimatorVisualView;


/**
 * Class to create an instance of a {@code EasyAnimatorView}.
 */
public class ProviderViewCreator {

  /**
   * Creates an {@code AnimatorView} object.
   *
   * @param type The type of view specified
   * @return A new view
   * @throws IllegalArgumentException if the specified view type is not valid
   */
  public static EasyAnimatorView<RectangleAdapter, EllipseAdapter> create(String type)
      throws IllegalArgumentException {
    switch (type) {
      case "providerText":
        return new EasyAnimatorTextualView<>();
      case "providerVisual":
        return new EasyAnimatorVisualView<>(new VisualShapeRendererImpl());
      case "providerInteractive":
        return new EasyAnimatorInteractiveView<>(new VisualShapeRendererImpl());
      default:
        throw new IllegalArgumentException("Invalid view type");
    }
  }
}
