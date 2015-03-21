package octalmind.mqtt

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
  willMessage: Option[ByteVector],
  username: Option[String],
  password: Option[String]) extends MQTTMessage
case class Connack(sessionPresent: Boolean, returnCode: ReturnCode) extends MQTTMessage
case class Publish(topic: String, id: Option[Int], payload: ByteVector) extends MQTTMessage

case class PubAck(id: Int) extends MQTTMessage
case class PubRel(id: Int) extends MQTTMessage
case class PubRec(id: Int) extends MQTTMessage
case class PubComp(id: Int) extends MQTTMessage
case class TopicSubscription(topic: String, qos: QOS)
case class Subscribe(id: Int, subscription: List[TopicSubscription]) extends MQTTMessage
case class SubAck(id: Int, returnCodes: List[SubscriptionReturnCode]) extends MQTTMessage
case class Unsubscribe(id: Int, topics: List[String]) extends MQTTMessage
case class UnsubAck(id: Int) extends MQTTMessage
case object PingReq extends MQTTMessage
case object PingResp extends MQTTMessage
case object Disconnect extends MQTTMessage

