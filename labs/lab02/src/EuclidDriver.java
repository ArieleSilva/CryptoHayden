/**
 * file: EuclidDriver.java
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 02
 * due date: January 24th, 2018
 * version: 1.0
 * <p>
 * This file contains the main class driver
 * that runs the Euclidean Algorithm.
 */

import java.util.Scanner;

/**
 * The class that houses the main driver and
 * Euclidean algorithm.
 */
public class EuclidDriver {

  /**
   * Usage: Two integers(long) separated by a single space.
   *
   * @param args
   */
  public static void main(String[] args) {

    // Scanner object to read input.
    Scanner input = new Scanner(System.in);

    // Grabs the longs to be plugged into euclidAlg
    while (input.hasNext()) {

      // Reads two String values.
      String values = input.nextLine();

      // Splits up numbers and adds them to an array.
      String[] longs = values.split(" ");

      // Input sanitation
      for (String s : longs) {
        if (Long.parseLong(s) % 1 != 0) {
          System.out.println("Please only enter integer values!");
          return;
        }
      }

      if (longs.length != 2) {
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
      System.out.println(a);
      System.out.println(b);

      System.out.println("GCD: " + euclidAlg(a, b) + "\n");
    }
  }

  /**
   * Recursive implementation of the Euclidean Algorithm.
   *
   * @param a the larger long
   * @param d the smaller long
   * @return a long of GCD(a, b)
   */
  private static long euclidAlg(long a, long d) {

    if (d == 0)
      return a;

    else
      return euclidAlg(d, a % d);

//      long q = a - (d * (a / d));
//      return euclidAlg(d, q);
  }
}
