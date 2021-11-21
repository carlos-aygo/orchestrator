package edu.eci.entities

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "event",
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(value = SendKafkaMessage::class, name = "SEND_KAFKA_MESSAGE"),
    JsonSubTypes.Type(value = SendEmailMessage::class, name = "SEND_EMAIL_MESSAGE"),
    JsonSubTypes.Type(value = CancelSendEmailMessage::class, name = "CANCEL_SEND_EMAIL_MESSAGE")
)
abstract class MetadataFlow {

    var event: String = ""
}

open class SendKafkaMessage(
    var topic: String = "",
    var value: Map<String, Any>
) : MetadataFlow()

open class SendEmailMessage(
    var from: String = "",
    var to: String = "",
    var subject: String = "",
    var message: String = "",
    var delay: Long = 0,
) : MetadataFlow()

open class CancelSendEmailMessage(
    var path: String
) : MetadataFlow()