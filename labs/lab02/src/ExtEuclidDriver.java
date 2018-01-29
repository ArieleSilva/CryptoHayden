import java.util.Scanner;

public class ExtEuclidDriver {

  /**
   * @param args
   */
  public static void main(String[] args) {

    // Scanner object to read input.
    Scanner input = new Scanner(System.in);

    System.out.print("Please enter two numbers separated by a space: ");

    // Grabs the longs to be plugged into euclidAlg
    while (input.hasNext()) {

      String values = input.nextLine();

      String[] longs = values.split(" ");

      // Input sanitation
      for (String s : longs) {
        if (Long.parseLong(s) % 1 == 0) {
          System.out.println("Please only enter integer values!");
          return;
        }
      }

      if (longs.length > 2) {
        System.out.println("Please enter two positive numbers!");
        return;
      }

      // Converts strings to longs (if integer values)
      long a = Long.parseLong(longs[0]);
      long b = Long.parseLong(longs[1]);


      // Swap values if second input is larger.
      if (b > a) {
        long swap = b;
        b = a;
        a = swap;
      }

      // Debugging
      System.out.println("Num 1: " + a);
      System.out.println("Num 2: " + b);

      System.out.println(euclidAlgExt(a, b));
    }
  }

  /**
   * Recursive implementation of the Extended
   * Euclidean Algorithm
   *
   * @param a long
   * @param b long
   * @return a long[] of the original inputs
   */
  private static long[] euclidAlgExt ( long a, long b){

    return new long[] {0, 1};
  }
}
