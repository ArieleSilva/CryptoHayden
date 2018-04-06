/**
 * file: Driver.kt
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 05
 * due date: April 4th, 2018
 * version: 1.0
 * Tests the implementation of the AESCipher
 * and SBox classes.
 */

/**
 * Main function that tests the
 * functionality of AES encryption
 */
fun main(args: Array<String>) {

  // Read key.
  val key = readLine()!!

  // Ready plainText
  var plainText = readLine()!!
  val aes = AES()

  // Encrypt plaintext
  val cipherText = aes.encrypt(plainText, key)
  println("Ciphertext: $cipherText")

  plainText = aes.decrypt(cipherText, key)
  println("Plaintext: $plainText")
}