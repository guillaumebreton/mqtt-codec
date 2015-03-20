package mqtt

object QOS extends Enumeration {

  type QOS = Value
  val NONE = Value(0x00)
  val AT_MOST_ONCE = Value(0x01)
  val AT_LEAST_ONCE = Value(0x02)
  val EXACTLY_ONCE = Value(0x03)

}
