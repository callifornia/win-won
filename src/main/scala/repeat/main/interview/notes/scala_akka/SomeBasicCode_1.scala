package repeat.main.interview.notes.scala_akka

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}
import Messages.{ShoppingCartMessage, _}


object SomeBasicCode_1 {

  def shoppingCart(products: Set[Product] = Set.empty): Behavior[ShoppingCartMessage] =
    Behaviors.receive[ShoppingCartMessage] { (ctx, msg) =>
      msg match {
        case AddProduct(product) =>
          ctx.log.info(s"\nProduct was added: \n\t$product")
          shoppingCart(products + product)
        case RemoveProduct(product) =>
          ctx.log.info(s"\nProduct was removed: \n\t$product")
          shoppingCart(products - product)
        case DisplayProducts =>
          ctx.log.info(s"\nProducts in cart ... \n\t${products.mkString("\n\t")}")
          Behaviors.same[ShoppingCartMessage]
      }
    }


  def orderBehavior(order: Option[Order] = None): Behavior[OrderingMessage] =
    Behaviors.receive[OrderingMessage]{ (ctx, msg) =>
      msg match {
        case CreateOrder(product: Set[Product]) =>
          ctx.log.info(s"\nOrder was created, products: \n${product.mkString("\n")}")
          orderBehavior(Some(Order.create(product)))
        case RemoveOrder                        =>
          ctx.log.info(s"\nOrder to remove \n$order")
          orderBehavior(None)
        case DisplayOrder =>
          ctx.log.info(s"\nCurrent order: \n$order")
          Behaviors.same[OrderingMessage]
      }
    }



  val store = ActorSystem(
    Behaviors.setup[GeneralRootMessages]{
      ctx => {
        val shoppingCartActor = ctx.spawn(shoppingCart(), "shopping-cart-actor")
        val orderingCartActor = ctx.spawn(orderBehavior(), "order-actor")
        Behaviors.receiveMessage {
          case msg: ShoppingCartMessage =>
            ctx.log.info("\nRoot actor. Received shopping cart messages ...\n\t\t\t" + msg)
            shoppingCartActor ! msg
            Behaviors.same[GeneralRootMessages]
          case msg: OrderingMessage =>
            ctx.log.info("\nRoot actor. Received ordering cart message ...\n\t\t\t" + msg)
            orderingCartActor ! msg
            Behaviors.same[GeneralRootMessages]
        }
      }
    }, "actorSystem")




  def main(args: Array[String]): Unit = {
    val productToRemove = Product.generateProduct()
    store ! AddProduct(Product.generateProduct)
    store ! AddProduct(productToRemove)
    store ! AddProduct(Product.generateProduct)
    store ! DisplayProducts
    store ! RemoveProduct(productToRemove)
    store ! DisplayProducts

    store ! CreateOrder(Product.generateProducts(3))
    store ! DisplayOrder
    store ! RemoveOrder
    store ! DisplayOrder
  }
}
