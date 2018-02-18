/**
 * file: DeterminantDriver.java
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 03
 * due date: February 14th, 2018
 * version: 1.0
 *
 * Finds the determinant of a matrix.
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class the houses the necessary methods
 * for generating submatrices, finding the
 * determinant, as well as testing.
 */
public class DeterminantDriver {

  /**
   * The main method.
   *
   * @param args standard main method param
   */
  public static void main(String[] args) {

    // Read input for modulus and matrix size.
    System.out.print("Please enter mod and matrix size separated by a single space: ");
    ArrayList<Integer> matrixValues = new ArrayList<>();
    Scanner input = new Scanner(System.in);
    String values = input.nextLine();
    String[] nums = values.split(" ");

    // Assign input values to modulus and size
    int modulus = Integer.parseInt(nums[0]);
    int size = Integer.parseInt(nums[1]);

    for (int i = 0; i < size; i++) {

      System.out.print("Please enter " + size + " integers separated by a space: ");
      values = input.nextLine();
      nums = values.split(" ");

      for (String s : nums)
        matrixValues.add(Integer.parseInt(s) % modulus);
    }

    // Populate matrix.
    int[][] matrix = new int[size][size];
    for (int i = 0; i < size; i++)
      for (int j = 0; j < size; j++)
        matrix[i][j] = (int) matrixValues.remove(0);

    System.out.println("Determinant: " + cofModDet(modulus, matrix));
  }

  /**
   * Recursively returns the determinant of a given matrix.
   *
   * @param mod    the level of modulus to apply
   * @param matrix the matrix
   * @return the determinant of the matrix
   */
  private static int cofModDet(int mod, int[][] matrix) {

    // The determinant:
    int det = 0;

    // Apply modulus:
    for (int i = 0; i < matrix.length; i++)
      for (int j = 0; j < matrix[0].length; j++)
        matrix[i][j] = matrix[i][j] % mod;

    // Base case (size 1):
    if (matrix[0].length == 1)
      det = matrix[0][0];

    // Base case (size 2):
    else if (matrix[0].length == 2)
      det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

    // Recursive case:
    else
      for (int index = 0; index < matrix.length; index++)
        if (index % 2 == 0)
          det += matrix[0][index] * cofModDet(mod, generateSubMatrix(index, matrix));
        else
          det -= matrix[0][index] * cofModDet(mod, generateSubMatrix(index, matrix));

    return det % mod;
  }

  /**
   * Generates a sub-matrix for determining a determinant
   * for a given matrix.
   *
   * @param col    the column to be excluded
   * @param matrix the matrix to build from
   * @return the desired sub-matrix
   */
  private static int[][] generateSubMatrix(int col, int[][] matrix) {

    // Create sub-matrix
    int[][] subMatrix = new int[matrix.length - 1][matrix[0].length - 1];

    // ArrayList to hold values for new sub-matrix.
    ArrayList integers = new ArrayList<Integer>(0);

    // Add correct integers to ArrayList
    for (int i = 0; i < matrix.length; i++)
      for (int j = 0; j < matrix[0].length; j++)
        if (i != 0 && j != col) integers.add(matrix[i][j]);

    // Populate the newly created submatrix
    for (int i = 0; i < subMatrix.length; i++)
      for (int j = 0; j < subMatrix[i].length; j++)
        subMatrix[i][j] = (int) integers.remove(0);

    return subMatrix;
  }
}
