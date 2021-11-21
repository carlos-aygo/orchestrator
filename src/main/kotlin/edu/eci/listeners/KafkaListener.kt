package edu.eci.listeners

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import edu.eci.models.Flow
import edu.eci.services.FlowService
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import org.slf4j.LoggerFactory

@KafkaListener(
    groupId = "contracts",
    offsetReset = OffsetReset.EARLIEST,
    pollTimeout = "10000ms",
    sessionTimeout = "30000"
)
class KafkaListener(
    private val objectMapper: ObjectMapper,
    private val enterpriseService: FlowService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Topic("orchestrator")
    fun processEmail(data: String) {
        this.logger.info("start reading orchestrator ", data)
        try {
            val flow = this.objectMapper.readValue(data, JsonNode::class.java)
            this.enterpriseService.execute(flow)

        }catch (ex: Exception){
            this.logger.error("error reading orchestrator {} because {}", data, ex.message, ex)
        }
    }
}