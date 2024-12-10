package repeat.main.interview.notes.poligon

object Poligon3 {
  def main(args: Array[String]): Unit = {
    new C
    val a = Math.min(2, 3)
    println(a)
  }


  /* */





  /* just test */
  trait A {
    println("iA")
    val foo: String = {
      println("A.foo")
      "iA"
    }
  }


  trait B extends A {
    println(s"iB: $foo")
    val bar: String = foo + " World"
  }


  class C extends B {
    println("iC")
//    val foo = "Hello "
    println(bar)
  }
  // C -> B -> A









  /* just test*/
//  def main(args: Array[String]): Unit = {
//    val a: Foo = new Barr {}
//    println(a.one)
//    println(a.two)
//  }
  trait Barr extends Foo {
    abstract override def one: String = {
      println("Init Barr.one")
      val fromSupper = super.one
      fromSupper + "overriden in Barr"
    }

    override def two: String = {
      println("Init Barr.two")
      val fromSupper = super.two
      fromSupper + "overriden in Barr"
    }
  }

  trait Foo {
    def one: String = {
      println("init Foo.one")
      "Foo.one"
    }
    def two: String = {
      println("Init Foo.two")
      "Foo.two"
    }
  }



  /* Deference between Self type and */
  trait Dao {
    def getUser(id: Int): String = ""
  }

  trait UserRepository { self: Dao =>
    def printUser(): Unit = self.getUser(2)
  }

  trait Dashboard { self:  UserRepository =>
    self.printUser()
  }


  trait IDao {
    def getUser(id: Int): String = ""
  }

  trait IUserRepository extends IDao {
    def printUser(): Unit = getUser(2)
  }

  trait IDashboard extends IUserRepository {
    printUser()
    getUser(2) // this is BAD it's should not be the case ...
  }


}





