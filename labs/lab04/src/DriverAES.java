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

    // Scanner object to read input.
    Scanner input = new Scanner(System.in);

    // Read 32 bit hexidecimal String. (test Key: 5468617473206D79204B756E67204675)
    String key = input.nextLine();

    // Array of keys.
    String[] roundKeysHex = AESCipher.aesRoundKeys(key);

    // Prints the 11 secure hex strings.
    for (String roundKey : roundKeysHex)
      System.out.println(roundKey);
  }
}
