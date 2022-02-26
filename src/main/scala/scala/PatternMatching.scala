package scala

import scala.util.Random

object PatternMatching extends App {

  val random = new Random
  val x: Int = random.nextInt(10)

  val description: String = x match {
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else"
  }

  println(x)
  println(description)

  // 1. Decompose values

  case class Person(name: String, age: Int)
  case class AnotherPerson(name: String, age: Int)
  val bob: Person = Person("Bob", 20) // apply

  val Person(n, a) = bob // unapply

  println(s"$n and $a")

  val greeting = bob match {
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink in the US"
    case Person(n, a)           => s"Hi, my name is $n and I am $a years old"
    case _                      => "I don't know who I am"
  }
  println(greeting)

  /*
    MatchError
   */

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String)       extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")
  }

  val word = "77aTest"

  val Pattern = "([a-cA-C])".r
  word.head match {
    case Pattern(c) => println(c)
    case _          => println("Not valid")
  }

  println(animal)

  val isEven = x match {
    case n if n % 2 == 0 => true
    case _ => false
  }

  val isEvenCond: Boolean = if (x % 2 == 0) true else false // ?!

  val isEvenCond2 = x == 2
  lazy val isEvenCond3: Boolean = x == 2

  var acc = 0
  acc = 7
    // bad practise

  def isEvenCond3(x: Int) = x == 2
  val isEvenCond4 = (x: Int) => x == 2

  val isEvenNormal = x % 2 == 0


  /*
  Exercise
  Write a method with pattern matching takes an Expr trait as parameter => human readable form
 */
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(e: Expr): String = ???

  println(show(Sum(Number(2), Number(3))))   //   2 + 3
  println(show(Sum(Sum(Number(2), Number(3)), Number(4)))) //   2 + 3 + 4
  println(show(Prod(Sum(Number(2), Number(1)), Number(3)))) // (2 + 1) * 3
  println(show(Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(4))))) // (2 + 1) * (3 + 4)
  println(show(Sum(Prod(Number(2), Number(1)), Number(3)))) // 2 * 1 + 3

}