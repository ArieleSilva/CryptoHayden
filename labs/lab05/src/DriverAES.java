/**
 * file: DriverAES.java
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 04
 * due date: April 4th, 2018
 * version: 1.0
 * Tests the implementation of the AESCipher
 * and SBox classes.
 */

import java.util.Scanner;

/**
 * The class that houses the main method.
 */
public class DriverAES {

  /**
   * Main method to test implementation.
   *
   * @param args standard input.
   */
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    // Key: 5468617473206D79204B756E67204675
    String key = scanner.nextLine();

    // Plain Text: 54776F204F6E65204E696E652054776F
    String plainText = scanner.nextLine();

    // Generate the ciphertext:
    String cipherText = AESCipher.AES(plainText, key);
    System.out.println(cipherText);
  }
}
