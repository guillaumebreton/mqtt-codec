package mqtt

import org.scalatest._
import scodec.bits._

class VarIntCodecSpec extends WordSpec with MustMatchers {

  "encode/decode" should {
    "encode/decode 1 digits" in {
      roundtrip("00", 0)
      roundtrip("7F", 127)
    }
    "encode/decode 2 digits" in {
      roundtrip("8001", 128)
      roundtrip("FF7F", 16383)
    }
    "encode/decode 3 digits" in {
      roundtrip("808001", 16384)
      roundtrip("FFFF7F", 2097151)
    }
    "encode/decode 4 digits" in {
      roundtrip("80808001", 2097152)
      roundtrip("FFFFFF7F", 268435455)
    }
    "Fail with invalid" in {
      val vector = BitVector.fromHex("FFFFFFFF").get
      val result = varint.decode(vector)
      result mustBe 'failure
    }
  }

  private[this] def roundtrip(hexValue: String, value: Int) {

    val vector = BitVector.fromHex(hexValue).get
    val result = varint.decode(vector)

    val decodedValue = result.require
    decodedValue.remainder must be(BitVector.empty)
    decodedValue.value must be(value)

    val encodeResult = varint.encode(value)
    encodeResult mustBe 'successful
    encodeResult.require must be(vector)
  }
}
