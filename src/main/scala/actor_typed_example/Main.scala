package actor_typed_example
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import Configuration._
import Processor._
import actor_typed_example.PaymentHandling.{Money, UserId}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.receptionist.Receptionist
import akka.util.Timeout

import scala.util.{Failure, Success}

object Configuration {
  case class MerchantId(id: String) extends AnyVal
  case class BankIdentifier(id: String) extends AnyVal
  case class MerchantConfiguration(bankIdentifier: BankIdentifier)

  sealed trait ConfigurationMessage
  final case class RetrieveConfiguration(merchantId: MerchantId,
                                         replyTo: ActorRef[ConfigurationResponse]) extends ConfigurationMessage

  sealed trait ConfigurationResponse
  final case class ConfigurationNotFound(merchanId: MerchantId) extends ConfigurationResponse
  final case class ConfigurationFound(merchantId: MerchantId,
                                      merchantConfiguration: MerchantConfiguration) extends ConfigurationResponse
}


object Processor {
  sealed trait ProcessorRequest
  case class Process(amount: Money, userId: UserId) extends ProcessorRequest
}


object CreditCardProcessor {

  val key: ServiceKey[ProcessorRequest] = ServiceKey("creditCardProcessor")
  def process: Behavior[ProcessorRequest] =
    Behaviors.setup {
      context =>
        // register with the Receptionist which makes this actor discoverable
        context.system.receptionist ! Receptionist.Register(key, context.self)
        // TODO implement the actual behaviour
        Behaviors.unhandled
  }
}


object PaymentProcessor {
  def apply(): Behavior[Nothing] =
    Behaviors.setup[Nothing] {
      context =>
        println("Typed Payment Processor started")
        context.spawn(Behaviors.empty, "config")
        Behaviors.empty
    }
}


object PaymentHandling {

  import scala.concurrent.duration._
  def apply2(configuration: ActorRef[ConfigurationMessage]): Behavior[PaymentHandlingMessage] = {
    Behaviors.setup[PaymentHandlingMessage] { context =>
      Behaviors.receiveMessage {
        case paymentRequest: HandlePayment =>
          // define the timeout after which the ask request has failed
          implicit val timeout: Timeout = 1.second

          def buildConfigurationRequest(replyTo: ActorRef[ConfigurationResponse]) =
            RetrieveConfiguration(paymentRequest.merchantId, replyTo)

          context.ask(configuration, buildConfigurationRequest) {
            case Success(response: ConfigurationResponse) => AdaptedConfigurationResponse(response, paymentRequest)
            case Failure(exception) => ConfigurationFailure(exception)
          }

          Behaviors.same
        case AdaptedConfigurationResponse(ConfigurationNotFound(merchantId), _) =>
          context.log.warn("Cannot handle request since no configuration was found for merchant", merchantId.id)
          Behaviors.same
        case AdaptedConfigurationResponse(ConfigurationFound(merchantId, merchantConfiguration), request) =>
          // TODO relay the request to the proper payment processor
          Behaviors.unhandled
        case ConfigurationFailure(exception) =>
          context.log.warn(s"Could not retrieve configuration: $exception")
          Behaviors.same
      }
    }
  }


  def apply(configuration: ActorRef[ConfigurationMessage]): Behavior[PaymentHandlingMessage] = {
    Behaviors.setup[PaymentHandlingMessage] {
      context =>
        val configResponseAdapter: ActorRef[ConfigurationResponse] = context.messageAdapter(response => WrappedConfigurationResponse(response))
        def handle(requests: Map[MerchantId, HandlePayment]): Behavior[PaymentHandlingMessage] =
          Behaviors.receiveMessage {
            case paymentRequest: HandlePayment =>
              configuration ! RetrieveConfiguration(paymentRequest.merchantId, configResponseAdapter)
              handle(requests.updated(paymentRequest.merchantId, paymentRequest))

            case wrapped: WrappedConfigurationResponse =>
              wrapped.response match {
                case ConfigurationNotFound(merchantId) =>
                  context.log.warn("Cannot handle request since no configuration was found for merchant", merchantId.id)
                  Behaviors.same
                case ConfigurationFound(merchantId, merchantConfiguration) =>
                  requests.get(merchantId) match {
                    case Some(request) =>
                      // TODO relay the request to the proper payment processor
                      Behaviors.same
                    case None =>
                      context.log.warn("Could not find payment request for merchant id {}", merchantId.id)
                      Behaviors.same
                  }
              }

          }

        handle(requests = Map.empty)
    }
  }


  sealed trait PaymentHandlingMessage
  case class AdaptedConfigurationResponse(response: ConfigurationResponse,
                                          request: HandlePayment) extends PaymentHandlingMessage
  case class ConfigurationFailure(exception: Throwable) extends PaymentHandlingMessage
  case class WrappedConfigurationResponse(response: Configuration.ConfigurationResponse) extends PaymentHandlingMessage
  case class HandlePayment(amount: Money,
                           merchantId: MerchantId,
                           userId: UserId) extends PaymentHandlingMessage
                           
  case class Money(value: Int)
  case class UserId(value: Int)
  
}


object Main {
  def run(): Unit = {
    ActorSystem[Nothing](PaymentProcessor(), "typed-payment-processor")
  }
}
