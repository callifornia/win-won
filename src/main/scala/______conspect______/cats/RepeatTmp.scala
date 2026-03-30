package ______conspect______.cats

object RepeatTmp {

  trait CustomMonad[A, F[_]] {
    def pure[C](value: C): F[C]
    def map[B](function: A => B): F[B]
    def flatMap[B](function: A => F[B]): F[B]
  }

  case class CustomContainer[A](aValue: A) extends CustomMonad[A, CustomContainer] {
    override def pure[C](value: C): CustomContainer[C]                             = new CustomContainer(value)
    override def map[B](function: A => B): CustomContainer[B]                      = pure(function(aValue))
    override def flatMap[B](function: A => CustomContainer[B]): CustomContainer[B] = function.apply(aValue)
  }

//  case object CustomContainerEmpty extends CustomMonad[Nothing] {
//    override def pure[C](value: C): CustomMonad[C] = ???
//    override def map[B](function: Nothing => B): CustomMonad[B] = ???
//    override def flatMap[B](function: Nothing => CustomMonad[B]): CustomMonad[B] = ???
//  }


  def main(args: Array[String]): Unit = {
    val customContainer = CustomContainer(2)
    val customContainer_2 = CustomContainer(2)

    val res_1 = customContainer.map(_ + 3)
    val res_2 = customContainer.flatMap(x => customContainer_2.map(y => x + y))


    val res_3 = for {
      x <- CustomContainer(10)
      y <- CustomContainer(20)
    } yield x + y


    //c withFilter ... ?

    println(
      s"""
         | res_1: ${res_1} // 5
         | res_2: ${res_2}
         | res_3: ${res_3}
         |""".stripMargin)
  }
}
