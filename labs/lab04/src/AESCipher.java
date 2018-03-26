/**
 * file: AESCipher.java
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 04
 * due date: April 4th, 2018
 * version: 1.0
 * Converts a 32 bit hexidecimal String
 * to 11 secure 32 bit hexidecimal Strings.
 */

import java.util.ArrayList;
import java.util.Arrays;

public class AESCipher {

  /**
   * The constructor method.
   */
  AESCipher() {

  }

  /**
   * This method takes 32 bit hex key and generates
   * eleven secure 32 bit hex keys.
   *
   * @param keyHex the 32 bit hex key.
   * @return a String array with the secure keys.
   */
  public static String[] aesRoundKeys(String keyHex) {

    // Input sanitation:
    if (keyHex.length() != 32)
      return new String[]{"Incorrect string length!", "Please enter hex string of length 32"};

    // String array to hold keys.
    String[] roundKeys = new String[11];

    // Add each individual hex value to ArrayList
    ArrayList<Character> values = new ArrayList<>();
    for (char a : keyHex.toCharArray())
      values.add(a);

    // Populate matrix k with key value.
    String[][] k = new String[4][4];
    for (int i = 0; i < k.length; i++)
      for (int j = 0; j < k[0].length; j++)
        k[j][i] = values.remove(0).toString() + values.remove(0).toString();

    // Initialize 4x44 matrix with columns 0-3 with matrix k.
    String[][] w = new String[4][44];
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++)
        w[j][i] = k[j][i];

    // Iterate through and generate each new column.
    for (int j = 4; j < w[0].length; j++) {

      // If the column is a multiple of 4, assign column j to XOR of columns j - 1 and j - 4:
      if (j % 4 != 0)
        for (int index = 0; index < w.length; index++)
          w[index][j] = exclusiveOr(w[index][j - 4], w[index][j - 1]);

      // Otherwise, start a new round:
      else {

        // SBox for hex string substitution:
        SBox sBox = new SBox();

        // AES Rcon Hex String:
        String rCon = aesRcon(j / 4);

        // Temp String[] to house new values before plugging them into matrix:
        String[] temp = new String[4];

        // Construct new array with elements of previous column:
        for (int index = 0; index < w.length; index++)
          temp[index] = w[index][j - 1];

        // Perform a shift on the new array:
        temp = shift(temp);

        // Transform each byte using the SBox:
        for (int index = 0; index < 4; index++)
          temp[index] = sBox.aesSBox(temp[index]);

        // Perform XOR against the RCon and the first element on the top of the column:
        temp[0] = exclusiveOr(rCon, temp[0]);

        // Set column j to the contents of the new temp array XOR with column j - 4:
        for (int index = 0; index < w.length; index++)
          w[index][j] = exclusiveOr(w[index][j - 4], temp[index]);
      }
    }

    // Convert the 11 4x4 matrices in the 4x44 matrix into the 11 secure keys:
    int count = 0;
    while (count != 44) {
      String[][] matrix = new String[4][4];
      for (int i = 0; i < w.length; i++)
        for (int j = 0; j < w.length; j++)
          matrix[j][i] = w[j][i + count];

      roundKeys[count / 4] = generateString(matrix).toUpperCase();
      count += 4;
    }
    return roundKeys;
  }

  /**
   * Simplified method that returns the correct RCon
   * for the specified round in AES.
   *
   * @param round the current round in AES
   * @return the RCon hex byte.
   */
  private static String aesRcon(int round) {

    ArrayList<String> roundConstants = new ArrayList<>(Arrays.asList(
            "8d", "01", "02", "04", "08", "10",
            "20", "40", "80", "1b", "36", "36"
    ));

    return roundConstants.get(round);
  }

  /**
   * Method that simplifes the XOR process and
   * returns the formatted hex byte string.
   *
   * @param first  the first hex byte string.
   * @param second the second hex byte string.
   * @return the formatted post XOR hex byte string.
   */
  private static String exclusiveOr(String first, String second) {

    String val = Integer.toHexString(Integer.parseInt(first, 16) ^ Integer.parseInt(second, 16));
    if (val.length() < 2)
      return "0" + val;
    else
      return val;
  }

  /**
   * Method that simplifies the shift done in AES rounds.
   *
   * @param stringArray the String array.
   * @return the String array shifted over by 1.
   */
  private static String[] shift(String[] stringArray) {

    String temp = stringArray[0];
    stringArray[0] = stringArray[1];
    stringArray[1] = stringArray[2];
    stringArray[2] = stringArray[3];
    stringArray[3] = temp;

    return stringArray;
  }

  /**
   * Takes a 4x4 matrix and converts back to a 32 bit
   * hexidecimal string.
   *
   * @param matrix the 4x4 matrix.
   * @return the 32 bit hex string.
   */
  private static String generateString(String[][] matrix) {

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++)
        sb.append(matrix[j][i]);

    return sb.toString();
  }
}
