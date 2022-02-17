package scala

import scala.annotation.tailrec

object Recursion extends App {

  def factorial(n: Int): Int =
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + " - I first need factorial of " + (n-1))
      val result = n * factorial(n-1)
      println("Computed factorial of " + n)

      result
    }

  // println(factorial(10))
  // println(factorial(5000))

  def anotherFactorial(n: Int): BigInt = {
    @tailrec
    def factHelper(x: Int, accumulator: BigInt): BigInt =
      if (x <= 1) accumulator
      else {
        factHelper(x - 1, x * accumulator)
      }

    factHelper(n, 1)
  }

  println(anotherFactorial(5000))

  def factorialWithFoldLeft(): BigInt = ???

}