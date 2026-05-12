import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import scala.util.Try

val date_1 = Seq(
  "1963-01-01",
  "12-Jun-98",
  "1-Jul-86")

val parseFormats = Seq(
  DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH),
  DateTimeFormatter.ofPattern("d-MMM-yy", Locale.ENGLISH)
)

def parseDate(str: String): Option[LocalDate] = {
  parseFormats.flatMap(formatter => Try(LocalDate.parse(str, formatter)).toOption).headOption
}

val result = date_1.map(parseDate)

//
result.mkString("\n")


