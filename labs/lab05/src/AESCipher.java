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
  private static String[] aesRoundKeys(String keyHex) {

    // Input sanitation:
    if (keyHex.length() != 32)
      return new String[]{"Incorrect string length!", "Please enter hex string of length 32"};

    // String array to hold keys:
    String[] roundKeys = new String[11];

    // Generate 44x4 hex matrix:
    String[][] w = generateMatrix(keyHex, 4, 44);

    // Iterate through and generate each new column:
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
        for (int index = 0; index < 4; index++)
          temp[index] = w[index][j - 1];

        // Perform a shift on the new array:
        temp = shift(temp);

        // Transform each byte using the SBox:
        for (int index = 0; index < 4; index++)
          temp[index] = sBox.convert(Integer.parseInt(temp[index], 16));

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

    ArrayList<Integer> roundConstants = new ArrayList<>(Arrays.asList(
            0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
            0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
            0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
            0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
            0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
            0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
            0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
            0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
            0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
            0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
            0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
            0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
            0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
            0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
            0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
            0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d
    ));

    return Integer.toHexString(roundConstants.get(round));
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

    int a = Integer.parseInt(first, 16);
    int b = Integer.parseInt(second, 16);
    String val = Integer.toHexString(a ^ b);
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

    return new String[]{stringArray[1], stringArray[2], stringArray[3], stringArray[0]};
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

  /**
   * Converts a hex string to a matrix.
   *
   * @param hexString the hex string to be converted.
   * @return the matrix.
   */
  private static String[][] generateMatrix(String hexString, int row, int col) {

    // Add each individual hex value to ArrayList
    ArrayList<Character> values = new ArrayList<>();
    for (char a : hexString.toCharArray())
      values.add(a);

    // Populate matrix k with key value.
    String[][] k = new String[row][col];
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++)
        k[j][i] = values.remove(0).toString() + values.remove(0).toString();

    return k;
  }

  /**
   * Uses the XOR function to manipulate a chosen hex matrix
   * against a secure key.
   *
   * @param inHex  the chosen hex string.
   * @param keyHex the hexidecimal key.
   * @return the modified hexidecimal matrix.
   */
  private static String[][] AESStateXOR(String[][] inHex, String[][] keyHex) {
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++)
        inHex[i][j] = exclusiveOr(inHex[i][j], keyHex[i][j]);

    return inHex;
  }

  /**
   * Substitutes each hex byte in the matrix with its
   * corresponding SBox value.
   *
   * @param inStateHex the matrix.
   * @return the modified matrix.
   */
  private static String[][] AESNibbleSub(String[][] inStateHex) {
    SBox sBox = new SBox();
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++)
        inStateHex[i][j] = sBox.convert(Integer.parseInt(inStateHex[i][j], 16));
    return inStateHex;
  }

  /**
   * Performs the necessary shifts on a matrix
   * for AES encryption.
   *
   * @param inStateHex the matrix.
   * @return the modified matrix.
   */
  private static String[][] AESShiftRow(String[][] inStateHex) {

    String[][] temp = new String[4][4];
    for (int index = 0; index < 4; index++) {

      temp[0][index] = inStateHex[0][index];
      temp[1][index] = inStateHex[1][(index + 1) % 4];
      temp[2][index] = inStateHex[2][(index + 2) % 4];
      temp[3][index] = inStateHex[3][(index + 3) % 4];
    }
    return temp;
  }

  /**
   * Mixes up the given matrix using Rijndael's
   * method of mixing columns by using Galois
   * multiplication.
   *
   * @param inStateHex the given matrix.
   * @return a mixed matrix.
   */
  private static String[][] AESMixColumn(String[][] inStateHex) {

    // Create new matrix comprised of given matrix in integer format:
    int n[][] = new int[4][4];
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++)
        n[i][j] = Integer.parseInt(inStateHex[i][j], 16);

    // Perform the XOR operations on each of the columns:
    for (int c = 0; c < 4; c++) {
      inStateHex[0][c] = Integer.toHexString(
              gMult(0x02, n[0][c]) ^ gMult(0x03, n[1][c]) ^ n[2][c] ^ n[3][c]).toUpperCase();
      inStateHex[1][c] = Integer.toHexString(
              n[0][c] ^ gMult(0x02, n[1][c]) ^ gMult(0x03, n[2][c]) ^ n[3][c]).toUpperCase();
      inStateHex[2][c] = Integer.toHexString(
              n[0][c] ^ n[1][c] ^ gMult(0x02, n[2][c]) ^ gMult(0x03, n[3][c])).toUpperCase();
      inStateHex[3][c] = Integer.toHexString(
              gMult(0x03, n[0][c]) ^ n[1][c] ^ n[2][c] ^ gMult(0x02, n[3][c])).toUpperCase();
    }

    // Format each the hex bytes by either trimming or padding them:
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++) {
        int length = inStateHex[i][j].length();
        if (length < 2)
          inStateHex[i][j] = "0" + inStateHex[i][j];
        if (length > 2)
          inStateHex[i][j] = "" + inStateHex[i][j].charAt(length - 2) + inStateHex[i][j].charAt(length - 1);
      }
    return inStateHex;
  }

  /**
   * Performs Galois Multiplication on two integers
   * and returns the product.
   *
   * @param a the first int.
   * @param b the second int.
   * @return the product.
   */
  private static int gMult(int a, int b) {

    int p = 0;
    for (int c = 0; c < 8; c++) {
      if ((b & 1) != 0)
        p ^= a;

      boolean hiBitSet = (a & 0x80) != 0;
      a <<= 1;
      if (hiBitSet)
        a ^= 0x1b;
      b >>= 1;
    }

    return p;
  }

  /**
   * Encrypts given plaintext in 11 rounds of
   * encryption functions such as SBox sub, column
   * shift, column mixing, and XORing.
   *
   * @param pTextHex the String to be encrypted.
   * @param keyHex   The secure key.
   * @return the ciphertext string.
   */
  public static String AES(String pTextHex, String keyHex) {

    /*
     * Round 1:
     * Convert params to matrices, XOR with key.
     */
    String[] secureKeys = aesRoundKeys(keyHex);
    String[][] pTextBloc = generateMatrix(pTextHex, 4, 4);
    String[][] secureKeyBloc = generateMatrix(secureKeys[0], 4, 4);
    String[][] outStateHex = AESStateXOR(pTextBloc, secureKeyBloc);

    /*
     * Rounds 2-10 of encryption:
     * Nibble sub, shift row, mix columns, XOR with key.
     */
    for (int key = 1; key < secureKeys.length - 1; key++) {
      outStateHex = AESNibbleSub(outStateHex);
      outStateHex = AESShiftRow(outStateHex);
      outStateHex = AESMixColumn(outStateHex);
      secureKeyBloc = generateMatrix(secureKeys[key], 4, 4);
      outStateHex = AESStateXOR(outStateHex, secureKeyBloc);
    }

    /*
     * Last round of encryption:
     * Nibble sub, shift row, XOR with key.
     */
    secureKeyBloc = generateMatrix(secureKeys[10], 4, 4);
    outStateHex = AESNibbleSub(outStateHex);
    outStateHex = AESShiftRow(outStateHex);
    outStateHex = AESStateXOR(outStateHex, secureKeyBloc);

    // Return the ciphertext:
    return generateString(outStateHex).toUpperCase();
  }
}
