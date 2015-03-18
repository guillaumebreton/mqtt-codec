package mqtt

import ReturnCode._
import SubscriptionReturnCode._
import QOS._
import scodec.bits._

sealed trait MQTTMessage

case class ConnectHeader(
  length: Int,
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
case class Publish(id: Option[Int], topic: String, payload: ByteVector, dup: Boolean, qos: QOS, retain: Boolean)
case class PubAck(id: Int)
case class PubRec(id: Int)
case class PubComp(id: Int)
case class TopicSubscription(topic: String, qos: QOS)
case class Subscribe(id: Int, subscription: List[TopicSubscription])
case class SubAck(id: Int, returnCodes: List[SubscriptionReturnCode])
case class Unsubscribe(id: Int, topics: List[String])
case class UnsubAck(id: Int)
case object PingReq
case object PingResp
case object Disconnect

