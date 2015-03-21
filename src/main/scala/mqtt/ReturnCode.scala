package octalmind.mqtt

object ReturnCode extends Enumeration {

  type ReturnCode = Value

  val CONNECTION_ACCEPTED = Value(0x01)
  val UNACCEPTABLE_PROTOCOL_VERSION = Value(0x02)
  val IDENTIFIER_REJECTED = Value(0x03)
  val SERVER_UNAVAILABLE = Value(0x04)
  val BAD_AUTHENTICATION = Value(0x05)
  val NOT_AUTHORIZED = Value(0x06)

}
