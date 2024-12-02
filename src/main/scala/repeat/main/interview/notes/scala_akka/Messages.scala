package repeat.main.interview.notes.scala_akka

import java.util.UUID
import scala.util.Random

object Messages {

  case class ProductName(value: String) extends AnyVal {
    override def toString: String = value
  }
  case class ProductDescription(value: String) extends AnyVal  {
    override def toString: String = value
  }
  case class Product(name: ProductName, description: ProductDescription) {
    override def toString: String = s"name: $name, description: $description  "
  }


  object Product {
    def generateProducts(n: Int): Set[Product] = (1 to n).map(_ => generateProduct()).toSet
    def generateProduct(): Product =
      Product(
        name = ProductName(Random.nextString(5)),
        description = ProductDescription(Random.nextString(20)))
  }
  

  case class OrderId(value: UUID) extends AnyVal
  case class Order(id: OrderId, product: Set[Product])
  object Order {
    def generateUUID(): OrderId              = OrderId(UUID.randomUUID())
    def create(product: Set[Product]): Order = Order(generateUUID(), product)
  }


  trait GeneralRootMessages

  trait ShoppingCartMessage extends GeneralRootMessages
  case class AddProduct(product: Product) extends ShoppingCartMessage
  case class RemoveProduct(product: Product) extends ShoppingCartMessage
  case object DisplayProducts extends ShoppingCartMessage

  trait OrderingMessage extends GeneralRootMessages
  case class CreateOrder(product: Set[Product]) extends OrderingMessage
  case object RemoveOrder extends OrderingMessage
  case object DisplayOrder extends OrderingMessage
}
