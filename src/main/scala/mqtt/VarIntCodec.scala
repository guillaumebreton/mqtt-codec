package octalmind.mqtt

import scodec.bits._
import scodec._

// see http://docs.oasis-open.org/mqtt/mqtt/v3.1.1/os/mqtt-v3.1.1-os.html#_Toc398718023
object varint extends Codec[Int] {

  /**
   * Encode an Int value to a variable int value into a bit vector
   * @param v the value to encode
   * @return The encoded value as an Attempt
   */
  def encode(v: Int): Attempt[BitVector] = {
    var result = BitVector.empty
    var value = v
    var encodedByte = 0
    do {
      encodedByte = value % 128
      value = value / 128
      // if there are more data to encode, set the top bit of this byte
      if (value > 0) {
        encodedByte = encodedByte | 128
      }
      result = result ++ BitVector(encodedByte.toByte)
    } while (value > 0)
    Attempt.Successful(result)
  }

  /**
   * Decode a var int to an Int value
   * @param b the bitvector containg the value.
   * @return a decode result with the value and the remaining bits.
   */
  def decode(b: BitVector): Attempt[DecodeResult[Int]] = {
    var buffer = b
    var multiplier = 1
    var value = 0
    var encodedByte = 0
    do {
      //if there is more thant 3 digits with value FF,
      //the vector is invalid
      if (multiplier > 128 * 128 * 128) {
        return Attempt.Failure(Err("Malformed Remaining Length"))
      }
      encodedByte = buffer.take(8).toInt(false)
      buffer = buffer.drop(8)
      value = value + (encodedByte & 127) * multiplier
      multiplier *= 128
    } while ((encodedByte & 128) != 0)
    return Attempt.Successful(DecodeResult(value, buffer))
  }
  //as we do not know the size of the varint we
  //cannot set it.
  def sizeBound = SizeBound.unknown
}
