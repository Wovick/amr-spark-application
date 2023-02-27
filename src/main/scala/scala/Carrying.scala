package scala

object Carrying extends App {

  println("Test")

  val kx: Double => Double => Double = k => x => k*x



  val y2 = kx(-1)
  val y3 = kx(0.5)


  val k = -6
  val y1 = kx(-6)



  println(s"For y1 = -6x x = $x1 y = ${y1(x1)} ")
  println(s"For y1 = -6x x = $x1 y = ${kx(-6)(x1)} ")





  println(s"")
  println(s"")




}
