package types_to_repeat_f_bound_cacke

object Main {
  def main(args: Array[String]): Unit = {

    println("asdasd")
  }


//
  trait Animal[A <: Animal[A]] { self: A =>
    def get: List[Animal[A]]
  }



  trait Fish extends Animal[Fish]
  class Shark extends Fish {
    def get: List[Animal[Fish]] = List(new Cod)
  }

  class Cod extends Fish {
    def get: List[Animal[Fish]] = ???
  }

  class Dog extends Animal[Dog] {
    def get: List[Animal[Dog]] = ???
  }

  class Crocodile extends Animal[Crocodile] {
    def get: List[Animal[Crocodile]] = ???
  }



  // Cake pattern
  // self-type annotations to declare dependencies among types.
  trait B {
    def bAction(): Unit
  }
  trait A { self: B =>
    def aAction(): Unit = bAction()
  }





  // F-bound poilimorfism
//  trait Animal[A <: Animal[A]] {
//    def get: List[Animal[A]]
//  }
//
//  class Dog extends Animal[Dog] {
//    def get: List[Animal[Dog]] = ???
//  }
//
//  class Crocodile extends Animal[Dog] {
//    def get: List[Animal[Dog]] = ???
//  }








  class CParking[+T](vihacles: List[T]) {
    def park[B >: T](vehicle: B): CParking[T] = ???
    def imbound[B >: T](vehicles: List[B]): CParking[T] = ???
    def checkVehicle[T](str: String): List[T] = ???
    def flatMap[S](f: T => CParking[S]): CParking[S] = ???
  }

  class OParking[-T](vihacle: List[T]) {
    def park(vehicle: T): OParking[T] = ???
    def imbound(vehicle: List[T]): OParking[T] = ???
    def check[B <: T](con: String): List[B] = ???
    def flatMap[R <: T, S](f: R => OParking[S]): OParking[S] = ???
  }
}
