package edu.eci.services

import com.fasterxml.jackson.databind.JsonNode
import edu.eci.models.Flow

interface FlowService {

    fun execute(data: JsonNode)
    fun applyFlows(data: JsonNode): List<Flow>

    fun create(flow: Flow): Flow
    fun update(flow: Flow): Flow
    fun delete(flow: Flow)
    fun getAll(): List<Flow>
}