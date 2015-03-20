package mqtt

import scodec.bits._
import scodec._

object MQTTCodec extends Codec[Frame] {
  import ReturnCode._
  import QOS._
  import SubscriptionReturnCode._
  import scodec.codecs._
  val str = variableSizeBytes(uint16, utf8).as[String]

  val qos = mappedEnum(uint(2),
    QOS0 -> 0x00,
    QOS1 -> 0x01,
    QOS2 -> 0x02)

  val connectHeader = (
    constant(hex"00044D515454") ::
    uint8 ::
    bool ::
    bool ::
    bool ::
    qos ::
    bool ::
    bool ::
    ignore(1) ::
    uint16).dropUnits.as[ConnectHeader]

  val connect = connectHeader.flatPrepend(header ⇒
    str ::
      conditional(header.willFlag, str) ::
      conditional(header.willFlag, listOfN(uint16, byte)) ::
      conditional(header.usernameFlag, str) ::
      conditional(header.passwordFlag, str)).as[Connect]

  val returnCode = mappedEnum(uint8,
    CONNECTION_ACCEPTED -> 1,
    UNACCEPTABLE_PROTOCOL_VERSION -> 2,
    IDENTIFIER_REJECTED -> 3,
    SERVER_UNAVAILABLE -> 4,
    BAD_AUTHENTICATION -> 5,
    NOT_AUTHORIZED -> 6)

  val connack = (
    ignore(7) ::
    bool(1) ::
    returnCode).dropUnits.as[Connack]

  val header = (uint(4) :: bool :: qos :: bool).as[Header]

  val frame = header.flatPrepend((header: Header) ⇒
    variableSizeBytes(varint, payloadCodec(header)).hlist).as[Frame]

  def publish(header: Header): Codec[Publish] = (
    str ::
    conditional(header.qos.id > 0, uint16) ::
    bytes).as[Publish]
  val puback = (uint16).as[PubAck]
  val pubrec = (uint16).as[PubRec]
  val pubrel = (uint16).as[PubRel]
  val pubcomp = (uint16).as[PubComp]

  val topicSubscription = (
    str ::
    ignore(6) ::
    qos).dropUnits.as[TopicSubscription]

  val subscribe = (
    uint16 ::
    list(topicSubscription)).as[Subscribe]

  val subscriptionReturnCode = mappedEnum(uint8,
    OK_QOS0 -> 0x01,
    OK_QOS1 -> 0x02,
    OK_QOS2 -> 0x03,
    FAILURE -> 0x80)

  val suback = (
    uint16 ::
    list(subscriptionReturnCode)).as[SubAck]

  def payloadCodec(header: Header) = discriminated[MQTTMessage].by(provide(header.messageType))
    .typecase(1, connect)
    .typecase(2, connack)
    .typecase(3, publish(header))
    .typecase(4, puback)
    .typecase(5, pubrec)
    .typecase(6, pubrel)
    .typecase(7, pubcomp)
    .typecase(8, subscribe)
    .typecase(9, suback)

  def encode(m: Frame) = frame.encode(m)
  def decode(m: BitVector) = frame.decode(m)

  override def sizeBound = SizeBound.unknown
}
