package edu.eci.controllers

import com.fasterxml.jackson.databind.JsonNode
import edu.eci.models.Flow
import edu.eci.services.FlowService
import io.micronaut.http.annotation.*
import javax.validation.Valid

@Controller("flows")
open class EnterpriseController(
    private val flowService: FlowService
) {

    @Post("execute")
    open fun execute(
        @Body flow: JsonNode
    ): String {

        this.flowService.execute(flow)
        return "OK"
    }

    @Post("apply-flows")
    open fun applyFlow(
        @Body flow: JsonNode
    ): List<Flow> {

        return this.flowService.applyFlows(flow)
    }

    @Post
    open fun create(
        @Body @Valid flow: Flow
    ): Flow {

        return this.flowService.create(flow)
    }

    @Put
    open fun update(
        @Body @Valid flow: Flow
    ): Flow {

        return this.flowService.update(flow)
    }

    @Delete
    open fun delete(
        @Body @Valid flow: Flow
    ): Flow {

        this.flowService.delete(flow)
        return flow
    }

    @Get
    open fun findAll(): List<Flow> {

        return this.flowService.getAll()
    }
}