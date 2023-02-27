
import io.circe
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._

import scala.language.implicitConversions


case class Country(
                    name: Name,
                    capital: List[String],
                    area: Double,
                    region: String)

case class Name(common: String)

case class CountryDto(
                       name: String,
                       capital: String,
                       area: Double
                     )

object CountryDto {
  def createDto(country: Country): CountryDto =
    CountryDto(
      name = country.name.common,
      capital = country.capital.head,
      area = country.area
    )
}


/**
 * 0. Creating a project SBT
 * 1. Case classes
 * 2. Reading from file, from URL with IO lib - https://www.scala-lang.org/api/2.12.2/scala/io/index.html
 * 3. Generic in Scala
 * 4. JSON parse lib
 * 5. Option vs Either vs Try - monads
 * 6. Pattern Matching
 * 7. Collection lib
 * 8. Collection operation - https://superruzafa.github.io/visual-scala-reference/
 * 9. Try Catch - working with resources
 * 10. Config libraries
 *
* */


object CircleApp extends App {
  def using[A, B <: {def close(): Unit}] (closeable: B) (f: B => A): A =
    try { f(closeable) } finally { closeable.close() }

  val result = using(scala.io.Source.fromFile("src/main/resourses/countries.json")) { src =>
    val decoded: Either[circe.Error, List[Country]] = decode[List[Country]](src.mkString)

    decoded match {
      case Right(value) =>
        value
          .filter(_.region == "Africa")
          .sortBy(_.area)(Ordering[Double].reverse)
          .slice(0, 10)
          .map(CountryDto.createDto)
          .asJson
          .noSpaces
      case Left(value) => throw new RuntimeException(s"Parsing problems: $value")
    }
  }

  println(result)
}