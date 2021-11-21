package edu.eci.services

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory

@Singleton
open class KafkaPublisherServiceImpl(
    private val  kafkaProducer: Producer<String, String>,
    private val objectMapper: ObjectMapper
): KafkaPublisherService {

    private val logger = LoggerFactory.getLogger(this::class.java)
    
    override fun sendMessage(data: Any, topic: String) {

        this.kafkaProducer.send(ProducerRecord(topic, this.objectMapper.writeValueAsString(data))){ metadata, exception ->
            if (exception == null ){
                this.logger.info("success sending metadata {}", topic)
            }else {
                this.logger.error("error sending topic message {} with error {}", topic, exception.message, exception)
            }
        }
    }
}