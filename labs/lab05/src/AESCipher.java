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

/**
 * The class that houses the necessary methods
 * for AES Encryption.
 */
class AESCipher {

  // Initial SBox hex values:
  private static int[] initialSBox = new int[] {
          0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
          0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
          0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
          0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
          0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
          0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
          0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
          0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
          0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
          0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
          0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
          0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
          0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
          0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E,
          0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
          0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
  };

  // Inverse of SBox hex values:
  private static int[] inverseSBox = new int[] {
          0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB,
          0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB,
          0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E,
          0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25,
          0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92,
          0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84,
          0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06,
          0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B,
          0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73,
          0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E,
          0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B,
          0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4,
          0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F,
          0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF,
          0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61,
          0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D
  };

  // Round constant hex values:
  private static int[] rCon = new int[] {
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
  };

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

        // AES Rcon Hex String:
        String rCon = aesRCon(j / 4);

        // Temp String[] to house new values before plugging them into matrix:
        String[] temp = new String[4];

        // Construct new array with elements of previous column:
        for (int index = 0; index < 4; index++)
          temp[index] = w[index][j - 1];

        // Perform a shift on the new array:
        temp = shift(temp);

        // Transform each byte using the SBox:
        for (int index = 0; index < 4; index++)
          temp[index] = sBoxTransform(Integer.parseInt(temp[index], 16));

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
   * value for the specified round in AES.
   *
   * @param round the current round in AES
   * @return the RCon hex byte.
   */
  private static String aesRCon(int round) {

    return Integer.toHexString(rCon[round]);
  }

  /**
   * Simplified method that returns the correct SBox
   * value for the specified nibble in AES.
   *
   * @param inHex the hex byte.
   * @return the modified value.
   */
  private static String sBoxTransform(int inHex) {

    return Integer.toHexString(initialSBox[inHex]);
  }

  /**
   * Simplified method the returns the correct SBox
   * value for the specified nibble in AES.
   *
   * @param inHex the hex byte.
   * @return the modified value.
   */
  private static String sBoxRevert(int inHex) {

    return Integer.toHexString(inverseSBox[inHex]);
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

    // Return the matrix:
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

    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++)
        inStateHex[i][j] = sBoxTransform(Integer.parseInt(inStateHex[i][j], 16));
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
  static String AES(String pTextHex, String keyHex) {

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
