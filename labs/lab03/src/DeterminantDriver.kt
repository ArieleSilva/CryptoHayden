/**
 * file: DeterminantDriver.kt
 * author: Kevin Hayden
 * course: MSCS 630
 * assignment: Lab 03
 * due date: February 28th, 2018
 * version: 1.0
 *
 * Finds the determinant of a matrix.
 */

fun main(args: Array<String>) {

  // Read input for modulus and matrix size:
  print("Please enter mod and matrix separated by a single space: ")
  var input: String? = readLine()
  var ints: List<String> = input!!.split(" ".toRegex())

  // Input sanitation:
  if (ints.size != 2) return
  ints.forEach {if (it.toIntOrNull() == null) return}

  // Assign input values to modulus and size:
  val modulus: Int = ints[0].toInt()
  val size: Int = ints[1].toInt()

  // Populate Matrix:
  val matrixValues: MutableList<Int> = mutableListOf()
  for (index in 0 until size) {
    print("Please enter $size integers separated by a space: ")
    input = readLine()
    ints = input!!.split(" ".toRegex())
    for (string in ints) matrixValues.add(ints[index].toInt() % modulus)
  }
  val matrix = Array(size, { IntArray(size) })
  for (i in 0 until matrix.size) {
    val colArray = IntArray(size)
    for (j in 0 until colArray.size) colArray[j] = matrixValues.removeAt(0)
    matrix[i] = colArray
  }
  println("Determinant: ${cofModDet(modulus, matrix)}")
}

/**
 *
 */
private fun cofModDet(mod: Int, matrix: Array<IntArray>): Int {

  // The determinant:
  var det = 0

  // Apply modulus:
  for (i in 0 until matrix.size)
    for (j in 0 until matrix[0].size)
      matrix[i][j] = matrix[i][j] % mod

  when (matrix.size) {

    // Base case (size 1):
    1 -> det = matrix[0][0]

    // Base case (size 2):
    2 -> det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]

    // Recursive case:
    else ->
      for (index in 0 until matrix.size)
        if (index % 2 == 0)
          det += matrix[0][index] * cofModDet(mod, generateSubMatrix(index, matrix))
        else
          det -= matrix[0][index] * cofModDet(mod, generateSubMatrix(index, matrix))
  }
  return det % mod
}

/**
 *
 */
private fun generateSubMatrix(col: Int, matrix: Array<IntArray>): Array<IntArray> {

  // Create sub-matrix
  val subMatrix = Array(matrix.size - 1, { IntArray(matrix.size - 1) })

  // Array to hold values for new sub-matrix.
  val integers: MutableList<Int> = mutableListOf()

  // Add correct integers  to ArrayList.
  for (i in 0 until matrix.size)
    for (j in 0 until matrix[0].size)
      if (i != 0 && j != col) integers.add(matrix[i][j])

  // Populate the newly created submatrix
  for (i in 0 until subMatrix.size)
    for (j in 0 until subMatrix[i].size)
      subMatrix[i][j] = integers.removeAt(0)

  return subMatrix
}