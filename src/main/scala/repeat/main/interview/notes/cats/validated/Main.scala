package repeat.main.interview.notes.cats.validated

import cats.data.Validated

object Main {


  def main(args: Array[String]): Unit = {

    /*

      Let say we do have a bunch of conditions to check
        - return error list with all possible errors
        - in case no errors return an int

    * */

    // regular solutions
    def testNumber(n: Int): Either[List[String], Int] =
      List(
        (x: Int) => if (x < 120) Right(x)   else Left(s"$n - must be less than 120"),
        (x: Int) => if (x > 0)   Right(x)   else Left(s"$n - must be non negative"),
        (x: Int) => if (x < 100) Right(x)   else Left(s"$n - must be <= 100"))
        .map(_.apply(n))
        .foldLeft(Right.apply[List[String], Int](n): Either[List[String], Int]) { (acc, el) =>
          el match {
            case Right(_)  => acc
            case Left(str) => acc match {
              case Right(_)     => Left.apply[List[String], Int](str :: Nil)
              case Left(errors) => Left.apply[List[String], Int](errors :+ str)
            }
          }
        }

    // cats solutions
    def validateNumber(x: Int): Validated[List[String], Int] =
      Validated.cond(x < 120, x, List(s"$x - must be less than 120"))
        .combine(Validated.cond(x > 0, x, List(s"$x - must be non negative")))
        .combine(Validated.cond(x < 100, x, List(s"$x - must be <= 100")))


    println(testNumber(121))
    println(validateNumber(121))

    /*

        Left(   List(121 - must be less than 120, 121 - must be <= 100))
        Invalid(List(121 - must be less than 120, 121 - must be <= 100))

     */
  }
}
