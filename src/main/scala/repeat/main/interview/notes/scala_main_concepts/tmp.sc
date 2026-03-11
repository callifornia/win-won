import java.time.LocalDate




val today = LocalDate.now()
println("today is: " + today)
val hireDay = LocalDate.of(2025, 3, 1)
val endDay = hireDay.plusMonths(8)

println{
  s"""
     | start day: $hireDay
     | end day:   $endDay
     | endDay
     |
     |""".stripMargin
}

endDay.getMonth
