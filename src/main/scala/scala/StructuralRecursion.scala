package scala

object StructuralRecursion extends App {

  // Polymorphism

  sealed trait A {
    def foo: String
  }

  final case class B() extends A {
    def foo: String =
      "It's B!"
  }

  final case class C() extends A {
    def foo: String =
      "It's C!"
  }

  val anAB: A = B() // anA: A = B()
  println(anAB.foo) // res0: String = It's B!
  val anAC: A = C() // anA: A = C()
  println(anAC.foo) // res1: String = It's C!


//  Pattern Matching

/*
  sealed trait Feline
  final case class Lion() extends Feline
  final case class Tiger() extends Feline
  final case class Panther() extends Feline
  final case class Cat(favouriteFood: String) extends Feline
*/


  sealed trait Food
  case object Antelope extends Food
  case object TigerFood extends Food
  case object Licorice extends Food
  final case class CatFood(food: String) extends Food

  sealed trait Feline {
    def dinner: Food
  }

  final case class Lion() extends Feline {
    def dinner: Food = Antelope
  }

  final case class Tiger() extends Feline {
    def dinner: Food = TigerFood
  }

  final case class Panther() extends Feline {
    def dinner: Food = Licorice
  }

  final case class Cat(favouriteFood: String) extends Feline {
    def dinner: Food = CatFood(favouriteFood)
  }



}
