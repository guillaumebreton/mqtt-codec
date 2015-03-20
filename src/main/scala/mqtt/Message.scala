package mqtt

import ReturnCode._
import SubscriptionReturnCode._
import QOS._
import scodec.bits._

sealed trait MQTTMessage

case class Header(messageType: Int, dup: Boolean, qos: QOS, retain: Boolean)
case class Frame(header: Header, payload: MQTTMessage)

case class ConnectHeader(
  protocolLevel: Int,
  usernameFlag: Boolean,
  passwordFlag: Boolean,
  willRetain: Boolean,
  willQos: QOS,
  willFlag: Boolean,
  cleanSession: Boolean,
  keepAliveTime: Int)

case class Connect(
  header: ConnectHeader,
  clientId: String,
  willTopic: Option[String],
  willMessage: Option[List[Byte]],
  username: Option[String],
  password: Option[String]) extends MQTTMessage
case class Connack(sessionPresent: Boolean, returnCode: ReturnCode) extends MQTTMessage
case class Publish(topic: String, id: Option[Int], payload: ByteVector) extends MQTTMessage

case class PubAck(id: Int) extends MQTTMessage
case class PubRel(id: Int) extends MQTTMessage
case class PubRec(id: Int) extends MQTTMessage
case class PubComp(id: Int) extends MQTTMessage
case class TopicSubscription(topic: String, qos: QOS)
case class Subscribe(id: Int, subscription: List[TopicSubscription])
case class SubAck(id: Int, returnCodes: List[SubscriptionReturnCode])
case class Unsubscribe(id: Int, topics: List[String])
case class UnsubAck(id: Int)
case object PingReq
case object PingResp
case object Disconnect

