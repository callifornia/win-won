case class A private(id: Int)
object A {
  def apply(a: String): Option[A] = Some(new A(a.toInt))
}

//val a1 = new A(123)
//val a2 = A(123)
//val a3 = new A("123")
val a4 = A("123")



sealed trait Apartment[T]
case class Balcony[T](value: T) extends Apartment[T]
case class Room[T](value: T) extends Apartment[T]
case object Floor extends Apartment[Nothing]



def updateApartment(app: Apartment[BalconyType]) =
  balcony

