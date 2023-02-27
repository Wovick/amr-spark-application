val kx: Int => Int => Int = k => x => k * x

val k = -6

val y1 = kx(k)
val y2 = kx(-1)

val x1 = 2

y1(x1)

kx(k)(x1) // curried function

def straightLine(k: Int)(x: Int): Int = k * x // curried method

//val lineY6X = straightLine(-6) _
val lineY6X: Int => Int = straightLine(-6) // function


lineY6X(x1)

// transforming method to function called lifting
// function != method






