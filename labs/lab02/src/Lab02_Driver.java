import java.util.Scanner;

/**
 * file: Lab02_Driver.java
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 02
 * due date: January 24th, 2018
 * version: 1.0
 *
 * This file contains the main class driver
 * that runs the Euclidean Algorithm.
 */

public class Lab02_Driver {

  public static void main(String[] args) {

    // Scanner object to read input.
    Scanner input = new Scanner(System.in);

    // Grabs two longs to be plugged into euclidAlg
    System.out.print("Please enter a positive int(long): ");
    long a = input.nextLong();

    System.out.print("Please enter a positive int(long): ");
    long b = input.nextLong();

    // Swap values if second input is larger.
    if (b > a) {
      long swap = b;
      b = a;
      a = swap;
    }

    System.out.println(euclidAlg(a, b));
  }

  /**
   * Recursive implementation of the Euclidean Algorithm
   *
   * @param a a long
   * @param b a long
   * @return a long of GCD(a, b)
   */
  private static long euclidAlg(long a, long b) {

    // The base case
    if (b == 0) {
      return a;
    } else {
      return euclidAlg(b, a % b);
    }
  }
}
