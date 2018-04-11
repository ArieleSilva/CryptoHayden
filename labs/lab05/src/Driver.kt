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
  print("Key: ")
  val key = readLine()!!.toUpperCase()

  val aes = AES()
  print("Email: ")
  val email = readLine()!!.replace("[@.]".toRegex(), "").toUpperCase()

  print("Password: ")
  val pw = readLine()!!.toUpperCase()

  var plainText = "$email:$pw"

  println(plainText)
  println(aes.encrypt(plainText, key))

  val cipherText = readLine()!!
  val parts = plainText.split(":".toRegex())
  for (part in parts)
    println(part)
  plainText = aes.decrypt(cipherText, key)
  println(plainText)
}