package octalmind.mqtt

object QOS extends Enumeration {

  type QOS = Value
  val QOS0 = Value(0x00)
  val QOS1 = Value(0x01)
  val QOS2 = Value(0x02)

}
