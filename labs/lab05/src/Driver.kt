import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec

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

  val key = Encryptor.generateRandomKey()
  println(key.length)
  println(key)
//
//  var plainText = "$email:$pw"
//
//  val cipherText: String? = Encryptor.encrypt(key, initVector, plainText)
//
//  println(Encryptor.decrypt(key, initVector, cipherText!!))
}

