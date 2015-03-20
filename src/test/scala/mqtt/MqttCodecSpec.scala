package mqtt

import org.scalatest._
import scodec.bits._

class MQTTCodecSpec extends WordSpec with MustMatchers {
  import ReturnCode._
  import QOS._
  import SubscriptionReturnCode._

  "encode/decode" should {
    "encode/decode connect" in {

      val header = Header(1, false, QOS0, false)

      val connectHeader = ConnectHeader(
        4,
        true,
        true,
        true,
        QOS1,
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
      val header = Header(2, false, QOS0, false)
      val connack = Connack(true, CONNECTION_ACCEPTED)
      val value = "20020101"
      roundtrip(value, Frame(header, connack))

    }
    "encode/decode publish" in {
      val header = Header(3, false, QOS1, false)
      val publish = Publish("t", Some(1), hex"020202")
      val value = "32080001740001020202"
      roundtrip(value, Frame(header, publish))

    }
    "encode/decode puback" in {
      val header = Header(4, false, QOS1, false)
      val puback = PubAck(1)
      val value = "42020001"
      roundtrip(value, Frame(header, puback))

    }
    "encode/decode pubrec" in {

      val header = Header(5, false, QOS1, false)
      val pubrec = PubRec(1)
      val value = "52020001"
      roundtrip(value, Frame(header, pubrec))
    }
    "encode/decode pubrel" in {

      val header = Header(6, false, QOS1, false)
      val pubrel = PubRel(1)
      val value = "62020001"
      roundtrip(value, Frame(header, pubrel))
    }
    "encode/decode pubcomp" in {

      val header = Header(7, false, QOS1, false)
      val pubcomp = PubComp(1)
      val value = "72020001"
      roundtrip(value, Frame(header, pubcomp))
    }
    "encode/decode subscribe" in {
      val header = Header(8, false, QOS1, false)
      val subscribe = Subscribe(1, List(TopicSubscription("t", QOS1), TopicSubscription("u", QOS2)))
      val value = "820A00010001740100017502"
      roundtrip(value, Frame(header, subscribe))

    }

    "encode/decode suback" in {
      val header = Header(9, false, QOS0, false)
      val suback = SubAck(1, List(OK_QOS0, OK_QOS1, OK_QOS2, FAILURE))
      val value = "9006000101020380"
      roundtrip(value, Frame(header, suback))

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
