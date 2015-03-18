package mqtt

import org.scalatest._
import scodec.bits._

class MQTTCodecSpec extends WordSpec with MustMatchers {
  import ReturnCode._
  import QOS._

  "encode/decode" should {
    "encode/decode connect" in {

      val header = ConnectHeader(
        10,
        4,
        true,
        true,
        true,
        AT_MOST_ONCE,
        true,
        true,
        5
      )
      val connect = Connect(
        header,
        "D",
        Some("t"),
        Some(hex"0202".toSeq.toList),
        Some("E"),
        Some("F")
      )
      roundtrip("100A00044D51545404EE000500014400017400020202000145000146", connect)

    }

    "encode/decode connack" in {
      val connack = Connack(true, CONNECTION_ACCEPTED)
      val value = "20020101"
      roundtrip(value, connack)

    }
  }

  private[this] def roundtrip(hexValue: String, value: MQTTMessage) {

    val vector = BitVector.fromHex(hexValue).get
    val result = MQTTCodec.decode(vector)

    val decodedValue = result.require
    decodedValue.remainder must be(BitVector.empty)
    decodedValue.value must be(value)

    val encodeResult = MQTTCodec.encode(value)
    encodeResult mustBe 'successful
    encodeResult.require must be(vector)
  }

}
