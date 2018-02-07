/**
 * file: EuclidDriver.kt
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
 * Driver method to test usage of the Euclidean Algorithm.
 */
fun main(args: Array<String>) {

  print("Please enter two longs separated by a single space: ")
  val input: String? = readLine()

  // Input sanitation
  val ints: List<String> = input!!.split(" ".toRegex())
  if (ints.size != 2) {
    println("Please enter two integers separated " +
            "by a single space!")
    return
  }

  ints.forEach {
    if (it.toIntOrNull() == null) {
      println("Please enter two integers separated " +
              "by a single space!")
      return
    }
  }

  // Assign values to a, d
  var a = ints[0].toInt()
  var d = ints[1].toInt()

  // Swap values if second input is larger
  if (d > a) {
    val temp = d
    d = a
    a = temp
  }

  println("GCD($a, $d) = " + euclidAlg(a, d))

}

/**
 * Recursive implementation if the Euclidean Algorithm
 *
 * @param a the int
 * @param d the divisor
 * @return the GCD(a, d)
 */
fun euclidAlg(a: Int, d: Int): Int {
  return when (d) {
    0 -> a
//    else -> euclidAlg(d, a % d)
    else -> euclidAlg(d, a - (d * (a / d)))
  }
}