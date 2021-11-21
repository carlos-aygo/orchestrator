package edu.eci.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.jayway.jsonpath.JsonPath
import net.minidev.json.JSONArray
import kotlin.math.exp

object JsonPathUtil {

    private val objectMapper = ObjectMapper()

    fun applyExpressions(expression: List<String>, node: JsonNode): Boolean {

        return expression.map { applyExpression(it, node) }.reduce { prev, actual -> prev && actual }
    }

    private fun applyExpression(expression: String, node: JsonNode): Boolean {

        return try {
            val data = JsonPath.read<Any?>(this.objectMapper.writeValueAsString(listOf(node)), expression)
            (data as JSONArray).size > 0
        }catch (ex: Exception){
            false
        }
    }
}