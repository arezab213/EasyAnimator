package cs3500.animator;

import cs3500.animator.adapter.AnimatorModelAdapter;
import cs3500.animator.adapter.EllipseAdapter;
import cs3500.animator.adapter.ProviderViewCreator;
import cs3500.animator.adapter.RectangleAdapter;
import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.Controller;
import cs3500.animator.model.AnimationBuilder;
import cs3500.animator.model.AnimationReader;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.EasyAnimator;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.shapes.IShape;
import cs3500.animator.provider.model.EasyAnimatorImmutableModel;
import cs3500.animator.provider.model.shapes.VisitableShape;
import cs3500.animator.provider.view.EasyAnimatorView;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.ViewCreator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to run the animation.
 */
public final class Excellence {

  /**
   * The main function that initializes the model, its builder, and the view based on the inputted
   * console commands and runs the animation.
   *
   * @param args Console commands that provide the specifications for the animation.
   * @throws IOException If the console commands are invalid.
   */
  public static void main(String[] args) throws IOException {
    AnimationBuilder<AnimatorModel<IShape, Motion>> builder = new EasyAnimator.Builder();
    AnimationBuilder<EasyAnimatorImmutableModel<VisitableShape<RectangleAdapter,
        EllipseAdapter>>> providerBuilder = new AnimatorModelAdapter.Builder();

    AnimatorModel<IShape, Motion> model;
    EasyAnimatorImmutableModel<VisitableShape<RectangleAdapter, EllipseAdapter>> providerModel;
    AnimatorView view;
    EasyAnimatorView<RectangleAdapter, EllipseAdapter> providerView;
    Readable in;

    boolean hasIn = false;
    boolean hasView = false;
    Appendable out = System.out;
    FileWriter writer = null;
    double ticksPerSec = 1;
    String currStr;
    String file = "";
    String viewType = "";
    String speed;
    boolean isProvided = false;
    for (int i = 0; i < args.length; i++) {
      currStr = args[i];
      if (i + 1 == args.length) {
        throw new IllegalArgumentException("All commands must be followed by a specifier.");
      }
      switch (currStr) {
        case "-in":
          hasIn = true;
          file = args[i + 1];
          i++;
          break;
        case "-view":
          hasView = true;
          if (args[i + 1].equals("provider")) {
            isProvided = true;
            switch (args[i + 2]) {
              case "text":
                viewType = "providerText";
                i += 2;
                break;
              case "visual":
                viewType = "providerVisual";
                i += 2;
                break;
              case "interactive":
                viewType = "providerInteractive";
                i += 2;
                break;
              default:
                throw new IllegalArgumentException("Invalid Command");
            }
          } else {
            viewType = args[i + 1];
            i++;
          }
          break;
        case "-out":
          writer = new FileWriter(args[i + 1]);
          i++;
          break;
        case "-speed":
          speed = args[i + 1];
          try {
            ticksPerSec = Integer.parseInt(speed);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Speed must be an integer");
          }
          i++;
          break;
        default:
          throw new IllegalArgumentException("Invalid Command");
      }
    }
    if (!hasIn || !hasView) {
      throw new IllegalArgumentException("Input arguments must contain -in and -view calls");
    }

    in = new FileReader(file);

    if (writer == null) {
      if (isProvided) {
        providerModel = AnimationReader.parseFile(in, providerBuilder);
        providerView = ProviderViewCreator.create(viewType);
        providerView.render(providerModel, out, (int) ticksPerSec);
      } else {
        model = AnimationReader.parseFile(in, builder);
        view = ViewCreator.create(viewType, ticksPerSec, model, out);
        AnimatorController controller = new Controller(view);
        controller.setUpView();
      }
    } else {
      if (isProvided) {
        providerModel = AnimationReader.parseFile(in, providerBuilder);
        providerView = ProviderViewCreator.create(viewType);
        providerView.render(providerModel, writer, (int) ticksPerSec);
      } else {
        model = AnimationReader.parseFile(in, builder);
        view = ViewCreator.create(viewType, ticksPerSec, model, writer);
        AnimatorController controller = new Controller(view);
        controller.setUpView();
      }

      writer.flush();
    }
  }
}
