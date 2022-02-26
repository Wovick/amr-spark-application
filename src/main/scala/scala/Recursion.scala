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


  /*
    1.  Concatenate a string n times
    2.  IsPrime function tail recursive
    3.  Fibonacci function, tail recursive.
   */

//  @tailrec
  def concatenateTailrec(aString: String, n: Int, accumulator: String): String = ???

  println(concatenateTailrec("hello", 3, "")) // hellohellohello

  def isPrime(n: Int): Boolean = ???

  println(isPrime(2003)) // true
  println(isPrime(629)) // false

  def fibonacci(n: Int): Int = ???

  println(fibonacci(8)) // 1 1 2 3 5 8 13, 21

}