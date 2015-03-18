package mqtt

object SubscriptionReturnCode extends Enumeration {

  type SubscriptionReturnCode = Value

  val OK_QOS0 = Value(0x01)
  val OK_QOS1 = Value(0x02)
  val OK_QOS2 = Value(0x03)
  val FAILURE = Value(0x80)

}
