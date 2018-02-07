/**
 * file: ExtEuclidDriver.java
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 02
 * due date: January 24th, 2018
 * version: 1.0
 * <p>
 * This file contains the main class driver
 * that runs the Extended Euclidean Algorithm.
 */

import java.util.Scanner;

/**
 * Class that houses the driver method as well as the Extended
 * Euclidean Algorithm.
 */
public class ExtEuclidDriver {

  /**
   * Driver method to test usage of the Extended Euclidean Algorithm.
   * Usage: java ExtEuclidDriver long1 long2
   *
   * @param args standard method input.
   */
  public static void main(String[] args) {

    System.out.println("Extended Euclidean Algorithm formula: ");
    System.out.println("ax + by = GCD(a, b)\n");
    System.out.print("Please enter two integers separated by a single: ");

    // Scanner object to read input.
    Scanner input = new Scanner(System.in);

    // Grabs the longs to be plugged into euclidAlgExt
    while (input.hasNext()) {

      String values = input.nextLine();

      // Separates the numbers in the input string and adds them to a String[]
      String[] longs = values.split(" ");

      // Input sanitation
      for (String s : longs) {

        if (longs.length != 2) {
          System.out.println("Please enter two positive numbers!");
          return;
        }

        if (Long.parseLong(s) % 1 != 0) {
          System.out.println("Please only enter integer values!");
          return;
        }
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

      long[] longArray = euclidAlgExt(a, b);
      long d = longArray[0], x = longArray[1], y = longArray[2];

      // Algorithm output
      System.out.println("GCD(" + a + ", " + b + ") = " + d);
      System.out.println(a + "(" + x + ")" + " + " + b + "(" + y + ")" + " = " + d);
      System.out.println(((a * x) + (b * y)) + " = " + d + "\n");
    }
  }

  /**
   * Iterative implementation of the Extended Euclidean Algorithm.
   * Euclidean Algorithm ax + by = GCD(a, b)
   *
   * @param a The bigger long.
   * @param d The smaller long.
   * @return a long[] containing the GCD as well as the coefficients x, y.
   */
  private static long[] euclidAlgExt(long a, long d) {

    // Book's iterative approach.
    long[] U = new long[]{a, 1, 0};
    long[] V = new long[]{d, 0, 1};
    long[] W = new long[3];

    while (d != 0) {

      // Euclidean Algorithm
      long quotient = a / d;
      long remainder = a % d;
      a = d;
      d = remainder;

      // Generate coefficients
      U = compute(U, quotient);
      V = compute(V, quotient);
    }

    W[0] = a;     // The GCD(a, d).
    W[1] = U[1];  // The x coefficient.
    W[2] = V[1];  // The y coefficient.

    return W;
  }

  /**
   * Method that computes the coefficient given an array.
   *
   * @param arr an array of longs.
   * @param quotient the quotient value.
   * @return the new array.
   */
  private static long[] compute(long[] arr, long quotient) {

    long temp = arr[1] - quotient * arr[2];
    arr[1] = arr[2];
    arr[2] = temp;
    return arr;
  }
}
