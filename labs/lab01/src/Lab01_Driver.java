/**
 * file: Lab01_Driver.java
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 01
 * due date: January 24th, 2018
 * version: 1.0
 *
 * This file contains the main class driver
 * that runs the str2int encryption scheme.
 */

import java.util.Scanner;

/**
 * Class declaration
 */
public class Lab01_Driver {

  /**
   * The main method.
   *
   * @param args input
   */
  public static void main(String[] args) {

    // Scanner object to read input
    Scanner input = new Scanner(System.in);

    // Reads each line and prints each char as an int.
    while (input.hasNext()) {

      // Reads input.
      String plainText = input.nextLine();

      // Sets plainText to all uppercase.
      plainText = plainText.toUpperCase();

      // Converts string to an array of ints.
      int[] output = str2int(plainText);

      // Prints each int in the array.
      for (int num : output)
        System.out.print(num + " ");
      System.out.println();
    }
  }

  /**
   * This method takes a String object and converts
   * each letter to an integer and then adds it to
   * an integer array. The function then returns the
   * array.
   *
   * @param plainText The string object to be encrypted.
   * @return an array of ints to be printed
   */
  private static int[ ] str2int(String plainText) {

    // Constant derived from where 'A' starts on the ASCII chart.
    final int INDEX = 65;

    // int array initialized to the size of the method parameter input.
    int[] output = new int[plainText.length()];

    // Normalize plainText to all uppercase.
    plainText = plainText.toUpperCase();

    // Sets char to its corresponding int and adds it to the array
    for (int i = 0; i < plainText.length(); i++) {
      char c = plainText.charAt(i);
      if (c == 32)
        output[i] = 26;
      else
        output[i] = ((int) c) - INDEX;
    }

    return output;
  }
}
