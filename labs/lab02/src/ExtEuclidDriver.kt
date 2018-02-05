/**
 * file: ExtEuclidDriver.kt
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 02
 * due date: January 24th, 2018
 * version: 1.0
 * <p>
 * This file contains the main class driver
 * that runs the Euclidean Algorithm.
 */

/**
 * Driver method to test usage of the Extended
 * Euclidean Algorithm.
 *
 * @param args standard method input.
 */
fun main(args: Array<String>) {

  println("Extended Euclidean Algorithm formula:")
  println("ax + by = GCD(a, b)\n")
  print("Please enter two integers separated by a single space: ")

  val input: String? = readLine()

  // Input sanitation
  val ints: List<String> = input!!.split(" ".toRegex())
  val response = "Please enter two integers separated by a single space!"
  if (ints.size != 2) {
    println(response)
    return
  }

  ints.forEach {
    if (it.toIntOrNull() == null) {
      println(response)
      return
    }
  }

  // Convert strings to ints (if integer values)
  var a = ints[0].toInt()
  var d = ints[1].toInt()

  // Swap values if second input is larger.
  if (d > a) {
    val temp = d
    d = a
    a = temp
  }

  val returnArr = euclidAlgExt(a, d)
  val gcd: Int = returnArr[0]
  val x: Int = returnArr[1]
  val y: Int = returnArr[2]

  // Algorithm output
  println("GCD($a, $d) = $gcd")
  println("$a($x) + $d($y) = $gcd")
  println("" + (a * x + d * y) + " = $gcd")
}

private fun euclidAlgExt(a: Int, d: Int): IntArray {

    // Book's iterative approach
    var u: IntArray = intArrayOf(a, 1, 0)
    var v: IntArray = intArrayOf(d, 0, 1)
    var num = a
    var div = d

    // Base case
    while (div != 0) {
        val quotient = num / div
        val remainder = num % div
        num = div
        div = remainder

        u = compute(u, quotient)
        v = compute(v, quotient)
    }

    return intArrayOf(num, u[1], v[1])

  // Recursive approach
//  val ints: IntArray?
//  if (d == 0) {
//    ints = intArrayOf(a, 1, 0)
//    return ints
//  }
//
//  val arr: IntArray = euclidAlgExt(d, a % d)
//  return intArrayOf(arr[0], arr[2], arr[1] - (a / d) * arr[2])
}

/**
 * Method that computes the coefficients given an array
 *
 * @param arr an array of longs.
 * @param quotient the quotient value.
 * @return the new array.
 */
private fun compute(arr: IntArray, quotient: Int): IntArray {

  val temp: Int = arr[1] - quotient * arr[2]
  arr[1] = arr[2]
  arr[2] = temp
  return arr
}