package mqtt

import scodec.bits._
import scodec.codecs._
import scodec._

object MQTTCodec extends Codec[MQTTMessage] {
  import ReturnCode._
  import QOS._
  val str = variableSizeBytes(uint16, utf8).as[String]

  val qos = mappedEnum(uint(2),
    AT_MOST_ONCE -> 0x01,
    AT_LEAST_ONCE -> 0x02,
    EXACTLY_ONCE -> 0x03
  )

  val connectHeader = (
    ignore(4) ::
    uint8 ::
    constant(hex"00044D515454") ::
    uint8 ::
    bool ::
    bool ::
    bool ::
    qos ::
    bool ::
    bool ::
    ignore(1) ::
    uint16
  ).dropUnits.as[ConnectHeader]

  val connect = connectHeader.flatPrepend(header =>
    str ::
      conditional(header.willFlag, str) ::
      conditional(header.willFlag, listOfN(uint16, byte)) ::
      conditional(header.usernameFlag, str) ::
      conditional(header.passwordFlag, str)
  ).as[Connect]

  val returnCode = mappedEnum(uint8,
    CONNECTION_ACCEPTED -> 1,
    UNACCEPTABLE_PROTOCOL_VERSION -> 2,
    IDENTIFIER_REJECTED -> 3,
    SERVER_UNAVAILABLE -> 4,
    BAD_AUTHENTICATION -> 5,
    NOT_AUTHORIZED -> 6
  )
  val connack = (
    ignore(4) ::
    constant(hex"02") ::
    ignore(7) ::
    bool(1) ::
    returnCode
  ).dropUnits.as[Connack]

  val codec = discriminated[MQTTMessage].by(uint(4))
    .typecase(1, connect)
    .typecase(2, connack)

  def encode(m: MQTTMessage) = codec.encode(m)
  def decode(m: BitVector) = codec.decode(m)

  override def sizeBound = SizeBound.unknown
}
