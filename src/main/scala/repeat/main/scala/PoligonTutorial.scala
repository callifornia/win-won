package repeat.main.scala

object PoligonTutorial {

  case class Pet(age: Int, name: String)
  object Pet {
    def unapply(pet: Pet): Option[(Int, String)] =
      pet.age > 10 match {
        case true  => Some(10, "...")
        case false => Some(12, "...")
      }

    def unapply(value: Int): Option[String] =
      value > 12 match {
        case true  => Some("Zero")
        case false => Some("One")
      }
  }


  def main(args: Array[String]): Unit = {
    Pet(13, "Billy") match {
      case Pet(age, status) => println(age, status) /* (10,...) */
    }

    Pet(13, "Billy").age match {
      case Pet(status) => println(status)           /* Zero */
    }
  }
}
