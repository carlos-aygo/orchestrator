package edu.eci.services.flows

import com.fasterxml.jackson.databind.JsonNode
import edu.eci.entities.MetadataFlow
import edu.eci.enums.FlowEnum
import edu.eci.models.Flow

interface ExecutionFlow {

    fun getFlow(): FlowEnum
    fun execute(data: JsonNode, flow: MetadataFlow)
}