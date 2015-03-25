# MQTT Codec

[![Circle CI](https://circleci.com/gh/guillaumebreton/mqtt-codec/tree/master.svg?style=svg)](https://circleci.com/gh/guillaumebreton/mqtt-codec/tree/master)

# What is it ?

MQTT codec is an implementation of the MQTT Codec using scodec. It provides a set of case class and a coder/decoder to extract these clases from binary messages. It implements the [MQTT 3.1 protocol specification](http://docs.oasis-open.org/mqtt/mqtt/v3.1.1/os/mqtt-v3.1.1-os.html).

# Documentation

All extracted messages are visible in the [Message.scala](https://github.com/guillaumebreton/mqtt-codec/blob/develop/src/main/scala/mqtt/Message.scala) file.

# Licensing

The project is under the MIT License. See the LICENSE.md file.

# Dependency

Add the following dependency and the bintray host

'''
resolvers += "octalmind maven" at "http://dl.bintray.com/guillaumebreton/maven"
libraryDependencies += "octamind" % "mqtt-codec_2.11" % "1.1.0"
'''

# Usage

'''
import scodec.bits.ByteVector
import octalmind.mqtt.MykioCodec

val message = hex"32080001740001020202"

val result = MQTTCodec.decode(message.toBitVector)

//result: scodec.Attempt[scodec.DecodeResult[octalmind.mqtt.Frame]] = Successful(DecodeResult(Frame(Header(3,false,QOS1,false),Publish(t,Some(1),ByteVector(3 bytes, 0x020202))),BitVector(empty)))
'''


# Release Nots

# 1.1.0 - Minor fixes

- Use semantic versioning
- Update scala version and cross compiled version to scala 2.11.6

# 1.0

- First release that implement the complete protocol.

# Contact

Feel free to use the github issue to submit bugs and features request.

