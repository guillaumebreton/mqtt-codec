package mqtt

import org.scalatest._
import scodec.bits._

class MQTTCodecSpec extends WordSpec with MustMatchers {
  import ReturnCode._
  import QOS._

  "encode/decode" should {
    "encode/decode connect" in {

      val header = Header(1, false, NONE, false)

      val connectHeader = ConnectHeader(
        4,
        true,
        true,
        true,
        AT_MOST_ONCE,
        true,
        true,
        5)
      val connect = Connect(
        connectHeader,
        "D",
        Some("t"),
        Some(hex"0202".toSeq.toList),
        Some("E"),
        Some("F"))
      val value = "101a00044D51545404EE000500014400017400020202000145000146"
      roundtrip(value, Frame(header, connect))

    }

    "encode/decode connack" in {
      val header = Header(2, false, NONE, false)
      val connack = Connack(true, CONNECTION_ACCEPTED)
      val value = "20020101"
      roundtrip(value, Frame(header, connack))

    }
    "encode/decode publish" in {
      val header = Header(3, false, AT_MOST_ONCE, false)
      val publish = Publish("t", Some(1), hex"020202")
      val value = "32080001740001020202"
      roundtrip(value, Frame(header, publish))

    }
    "encode/decode puback" in {
      val header = Header(4, false, AT_MOST_ONCE, false)
      val puback = PubAck(1)
      val value = "42020001"
      roundtrip(value, Frame(header, puback))

    }
    "encode/decode pubrec" in {

      val header = Header(5, false, AT_MOST_ONCE, false)
      val pubrec = PubRec(1)
      val value = "52020001"
      roundtrip(value, Frame(header, pubrec))
    }
    "encode/decode pubrel" in {

      val header = Header(6, false, AT_MOST_ONCE, false)
      val pubrel = PubRel(1)
      val value = "62020001"
      roundtrip(value, Frame(header, pubrel))
    }
    "encode/decode pubcomp" in {

      val header = Header(7, false, AT_MOST_ONCE, false)
      val pubcomp = PubComp(1)
      val value = "72020001"
      roundtrip(value, Frame(header, pubcomp))
    }
  }

  private[this] def roundtrip(hexValue: String, value: Frame): Unit = {

    val vector = BitVector.fromHex(hexValue).get
    val decodedResult = MQTTCodec.decode(vector)

    decodedResult mustBe 'successful
    val decodedValue = decodedResult.require
    decodedValue.remainder must be(BitVector.empty)
    decodedValue.value must be(value)

    val encodeResult = MQTTCodec.encode(value)
    encodeResult mustBe 'successful
    encodeResult.require must be(vector)
  }

}
