package scala

object MapFlatmapFilterFor extends App {

  val list = List(1, 2, 3)
  println(list.head)
  println(list.headOption)
  println(list.tail)

  // map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair                 = (x: Int) => List(x, x + 1)
  val ints2: List[List[Int]] = list.map(toPair)
  val ints: List[Int]        = list.flatMap(toPair)
  println(ints)

  // print all combinations between two lists
  val numbers = List(1, 2, 3, 4)
  val chars   = List('a', 'b', 'c', 'd')
  val colors  = List("black", "white")

  // List("a1", "a2"... "d4")

  // "iterating"
  val combinations = numbers
    .filter(_ % 2 == 0)
    .flatMap(n =>
      chars
        .flatMap(c => colors.map(color => "" + c + n + "-" + color))
    )
  println(combinations)

  // foreach
  list.foreach(println)

  // for-comprehensions
  val forCombinations: List[String] = for {
    n     <- numbers if n % 2 == 0
    c     <- chars
    color <- colors
  } yield "" + c + n + "-" + color

  class Message(val person: Person)
  class Person(val name: String, val childs: List[Child])
  class Child(val name: String, val pet: Pet)
  class Pet(val name: String)

  val m = new Message(new Person("test", List(new Child("test", new Pet("sdfsdf")))))

  val maybeString = for {
    p      <- Option(m.person)
    childs <- Option(p.childs)
    h      <- childs.headOption
    p      <- Option(h.pet)
  } yield p.name

  println(forCombinations)

  for { n <- numbers } println(n)

}