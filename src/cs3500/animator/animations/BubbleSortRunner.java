package cs3500.animator.animations;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Runs a {@code BubbleSort} animation.
 */
public class BubbleSortRunner {

  /**
   * The main function that initializes a model storing a list of circles in decreasing order of
   * their diameter, with {@code Move} motions representing their bubble sort rearrangement to
   * increasing order. The model's description is then written to file using its class toString
   * method, creating the animation.
   *
   * @param args Console commands that provide the specifications for the animation.
   * @throws IOException If the console commands are invalid.
   */
  public static void main(String[] args) throws IOException {

    Appendable out = System.out;
    FileWriter writer = null;
    String numCirclesStr;
    int numCircles = 5;
    String currStr;
    for (int i = 0; i < args.length; i++) {
      currStr = args[i];
      if (i + 1 == args.length) {
        throw new IllegalArgumentException("All commands must be followed by a specifier.");
      }
      switch (currStr) {
        case "-out":
          writer = new FileWriter(args[i + 1]);
          i++;
          break;
        case "-circles":
          numCirclesStr = args[i + 1];
          try {
            numCircles = Integer.parseInt(numCirclesStr);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Number of circles must be an integer");
          }
          i++;
          break;
        default:
          throw new IllegalArgumentException("Invalid Command");
      }
    }
    BubbleSort sort = new BubbleSort(numCircles);

    if (writer != null) {
      writer.append(sort.describeBubble());
      writer.flush();
    } else {
      out.append(sort.describeBubble());
    }
  }
}
