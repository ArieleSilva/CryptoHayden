/**
 * file: HexidecimalMatrixDriver.java
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 03
 * due date: February 28th, 2018
 * version: 1.0
 *
 * Converts plaintext to matrices
 * full of hexidecimal numbers.
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class that tests implementation of
 * methods getHexMatP and printHexMatrix.
 */
public class HexidecimalMatrixDriver {

  /**
   * The main method to test implementation.
   *
   * @param args standard main method parameters.
   */
  public static void main(String[] args) {

    // Prompt user for substitution character:
    System.out.print("Enter a single character: ");
    Scanner input = new Scanner(System.in);
    char sub = input.nextLine().charAt(0);

    // Prompt user for plaintext:
    System.out.print("Enter plaintext: ");
    String plainText = input.nextLine();
    int[][] hexMatrix;

    // Prints matrix until empty
    while (plainText.length() != 0) {

      // While plainText is 16 characters long print matrix of substring:
      if (plainText.length() > 15) {
        printHexMatrix(getHexMatP(sub, plainText.substring(0, 16)));
        plainText = plainText.substring(16);
      }

      // If plainText is less than 16 print whole string:
      else {
        printHexMatrix(getHexMatP(sub, plainText));
        plainText = "";
      }
    }
  }

  /**
   * If plainText length is less than 16, appends sub char to
   * the end until it is length 16. The method then converts
   * plainText to ASCII matrix.
   *
   * @param sub the substitution char to append to plainText.
   * @param plainText the plainText to be converted to ASCII.
   * @return an int matrix with ASCII values.
   */
  private static int[][] getHexMatP(char sub, String plainText) {

    // Add each character to character ArrayList:
    ArrayList<Character> chars = new ArrayList<>();

    // If plainText is less than 16 append with sub character:
    while (plainText.length() < 16)
      plainText += sub;

    for (int index = 0; index < plainText.length(); index++)
      chars.add(plainText.charAt(index));

    // Convert char array to ASCII matrix
    int[][] matrix = new int[4][4];
    for (int i = 0; i < matrix.length; i++)
      for (int j = 0; j < matrix[0].length; j++)
        matrix[j][i] = chars.remove(0);

    return matrix;
  }

  /**
   * Prints an ASCII matrix in hexidecimal format.
   *
   * @param matrix the matrix to print
   */
  private static void printHexMatrix(int[][] matrix) {

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++)
        System.out.print(Integer.toHexString(matrix[i][j]).toUpperCase() + " ");
      System.out.println();
    }
    System.out.println();
  }
}
