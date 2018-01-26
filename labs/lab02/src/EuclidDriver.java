/**
 * file: Lab02_Driver.java
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
 *
 */
public class EuclidDriver {

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

      // Debugging
//      System.out.println("Num 1: " + a);
//      System.out.println("Num 2: " + b);

      // Swap values if second input is larger.
      if (b > a) {
        long swap = b;
        b = a;
        a = swap;
      }

      System.out.println(euclidAlg(a, b));
    }
  }

  /**
   * Recursive implementation of the Euclidean Algorithm.
   *
   * @param a a long
   * @param b a long
   * @return a long of GCD(a, b)
   */
  private static long euclidAlg(long a, long d) {

//      // a = qd + r
//      // q = floor(a/d)
//      // r = a - qd
//      long q = Math.floorDiv(a, d);
//      long r = a - q * d;
//      return r;

    if (d == 0) {
      return a;
    } else {
      return euclidAlg(d, a % d);
    }
  }
}
