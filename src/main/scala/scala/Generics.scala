package scala

object Generics extends App {

  trait MyListTrait[A]
  class MyList[A]

  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]


  class MyMap[Key, Value]


  object MyList {
    def empty[A]: MyList[A] = ???
  }
  val emptyListOfIntegers: MyList[Int] = MyList.empty[Int]



  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. yes, List[Cat] extends List[Animal] = COVARIANCE
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog) ??? HARD QUESTION => we return a list of Animals

  // 2. NO = INVARIANCE
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]

  // 3. Hell, no! CONTRAVARIANCE
  class ContrVariantAnimalList[-A]
  val contrVariantAnimalList: ContrVariantAnimalList[Cat] = new ContrVariantAnimalList[Animal]


}