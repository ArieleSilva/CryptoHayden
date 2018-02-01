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

public class ExtEuclidDriver {

  /**
   * Main class to test usage of the Extended Euclidean Algorithm.
   */
  public static void main(String[] args) {

    System.out.println("Extended Euclidean Algorithm formula: ");
    System.out.println("ax + by = GCD(a, b)\n");

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

      // Debugging
//      System.out.println("Num 1: " + a);
//      System.out.println("Num 2: " + b);

      long[] longArray = euclidAlgExt(a, b);
      long d = longArray[0], x = longArray[1], y = longArray[2];

      // Algorithm output
      System.out.println("GCD(" + a + ", " + b + ") = " + d);
      System.out.println(a + "(" + x + ")" + " + " + b + "(" + y + ")" + " = " + d);
      System.out.println(((a * x) + (b * y)) + " = " + d + "\n");
    }
  }

  /**
   * Recursive implementation of the Extended
   * Euclidean Algorithm ax + by = GCD(a, b)
   *
   * @param a The bigger long.
   * @param d The smaller long.
   * @return a long[] containing the GCD as well as the coefficients x, y.
   */
  private static long[] euclidAlgExt(long a, long d) {

    // Book's iterative approach.
    long temp, quotient, remainder;
    long[] U = new long[] {a, 1, 0};
    long[] V = new long[] {d, 0, 1};

    while (d != 0) {

      quotient = a / d;
      remainder = a % d;
      a = d;
      d = remainder;

      temp = U[1] - quotient * U[2];
      U[1] = U[2];
      U[2] = temp;

      temp = V[1] - quotient * V[2];
      V[1] = V[2];
      V[2] = temp;
    }

    long gcd = a;
    long x = U[1];
    long y = V[1];

    // My recursive approach.
//    if (d == 0) // Base case
//      return new long[] {a, 1, 0};
//
//    // Recursively calls euclidAlgExt all the way to the base case.
//    long[] longs = euclidAlgExt(d, a % d);
//
//    // The GCD of (a, b), x coefficient, y coefficient
//    long gcd = longs[0];
//    long x = longs[2];
//    long y = longs[1] - (a / d) * longs[2];

    return new long[] {gcd, x, y};
  }
}
