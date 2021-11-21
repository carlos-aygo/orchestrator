package edu.eci.services

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import edu.eci.entities.MetadataFlow
import edu.eci.entities.OrchestratorEvent
import edu.eci.models.Flow
import edu.eci.repositories.FlowRepository
import edu.eci.services.flows.ExecutionFlow
import edu.eci.utils.JsonPathUtil.applyExpressions
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton
import java.time.LocalDateTime

@Singleton
open class FlowServiceImpl(
    private val flowRepository: FlowRepository,
    private val executionFlows: List<ExecutionFlow>,
    private val objectMapper: ObjectMapper
) : FlowService {

    override fun execute(data: JsonNode) {

        this.flowRepository.findAll().toList()
            .filter { flow ->
                applyExpressions(flow.conditions, data)
            }.forEach { flow ->
                this.executionFlows.firstOrNull { executionFlow ->
                    executionFlow.getFlow().name == flow.flow
                }?.execute(data, this.objectMapper.convertValue(flow.metadata, MetadataFlow::class.java))
            }
    }

    override fun applyFlows(data: JsonNode): List<Flow> {

        return this.flowRepository.findAll().toList()
            .filter { flow ->
                applyExpressions(flow.conditions, data)
            }
    }

    override fun create(flow: Flow): Flow {

        return this.flowRepository.save(flow)
    }

    override fun update(flow: Flow): Flow {

        return this.flowRepository.update(flow)
    }

    override fun delete(flow: Flow) {

        return this.flowRepository.deleteById(flow.id)
    }

    override fun getAll(): List<Flow> {

        return this.flowRepository.findAll().toList()
    }

}