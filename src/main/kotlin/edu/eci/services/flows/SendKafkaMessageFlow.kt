package edu.eci.services.flows

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import edu.eci.entities.MetadataFlow
import edu.eci.entities.SendKafkaMessage
import edu.eci.enums.FlowEnum
import edu.eci.services.KafkaPublisherServiceImpl
import edu.eci.utils.JsonExtension.process
import jakarta.inject.Singleton

@Singleton
class SendKafkaMessageFlow(
    private val kafkaPublisherServiceImpl: KafkaPublisherServiceImpl
): ExecutionFlow {
    override fun getFlow(): FlowEnum {

        return FlowEnum.SEND_KAFKA_MESSAGE
    }

    override fun execute(data: JsonNode, flow: MetadataFlow) {

        flow as SendKafkaMessage

        this.kafkaPublisherServiceImpl.sendMessage(flow.value.process(data as ObjectNode), flow.topic)
    }
}