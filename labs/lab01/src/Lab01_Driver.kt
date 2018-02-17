/**
 * file: Lab01_Driver.kt
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 01
 * due date: January 24th, 2018
 * version: 1.0
 *
 * This file contains the main class driver
 * that runs the str2int encryption scheme.
 */

/**
 * The Main method.
 */
fun main(args: Array<String>) {

  val plainText: String = readLine()!!.toString()
  val cipherText: IntArray = str2int(plainText)
  for (i in 0 until cipherText.size)
    print("${cipherText[i]} ")
  println()
}

private fun str2int(plainText: String): IntArray {

  // Normalize plaintext
  plainText.toUpperCase()

  // Initialize size of integer array
  val cipherText = IntArray(plainText.length)

  for (i in 0 until plainText.length) {
    val c: Char = plainText[i]
    if (c.toInt() == 32)
      cipherText[i] = 26
    else
      cipherText[i] = c.toInt() - 97
  }

  return cipherText
}