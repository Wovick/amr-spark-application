
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

object CircleApp extends App {
  def using[A, B <: {def close(): Unit}] (closeable: B) (f: B => A): A =
    try { f(closeable) } finally { closeable.close() }

  val result = using(scala.io.Source.fromFile("src/main/resourses/countries.json")) { src =>
    val decoded = decode[List[Country]](src.mkString)

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