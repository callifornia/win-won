package functor

object Main {

  trait Functor[C[_]] {
    def map[A, B](container: C[A])(function: A => B): C[B]
  }










  @main def run(): Unit = {
    
    
    
    
    

    

  }


}










