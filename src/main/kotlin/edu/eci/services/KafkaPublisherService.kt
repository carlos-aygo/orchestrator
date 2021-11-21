package edu.eci.services

interface KafkaPublisherService {

    fun sendMessage(data: Any, topic: String)
}