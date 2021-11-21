package edu.eci.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.*

object JsonExtension {

    private val objectMapper = ObjectMapper()

    fun Map<String, Any>.process(attribute: HashMap<String, String>): Map<String, Any> {

        val originValue  = objectMapper.valueToTree<JsonNode>(this)
        val config = objectMapper.valueToTree<ObjectNode>(attribute)
        val targetValue = originValue.processJson(config)

        return objectMapper.convertValue(targetValue, object : TypeReference<HashMap<String, Any>>() {})
    }

    fun Map<String, Any>.process(config: ObjectNode): Map<String, Any> {

        val originValue  = objectMapper.valueToTree<JsonNode>(this)
        val targetValue = originValue.processJson(config)

        return objectMapper.convertValue(targetValue, object : TypeReference<HashMap<String, Any>>() {})
    }

    fun JsonNode.processJson(node: ObjectNode): JsonNode {

        return when (this) {
            is ObjectNode -> this.processObject(node)
            is ArrayNode -> this.processArray(node)
            is BooleanNode, is NumericNode -> this
            else -> {
                val stringNode = this.textValue()
                val processNode = stringNode.processString(node)
                if (processNode != stringNode) {
                    TextNode.valueOf(processNode)
                } else this
            }
        }
    }

    fun ObjectNode.processObject(node: ObjectNode): ObjectNode {

        return ObjectNode(
            JsonNodeFactory.instance,
            this.fields().asSequence().toList().associate { entry ->
                entry.key to entry.value?.processJson(node)
            }
        )
    }

    fun ArrayNode.processArray(node: ObjectNode): ArrayNode {

        return ArrayNode(
            JsonNodeFactory.instance,
            this.map { subNode ->
                subNode.processJson(node)
            }
        )
    }

    fun String.processString(node: ObjectNode): String {
        val replaceBracketFirst = this.indexOf("{{")
        return if (replaceBracketFirst == -1) {
            this
        } else {
            val replaceBracketLast = this.indexOf("}}")
            if (replaceBracketLast == -1 || replaceBracketLast < replaceBracketFirst) {
                this
            } else {
                val objectName = this.substring(replaceBracketFirst + 2, replaceBracketLast).trim()
                val dataToReplace = node.getNodeData(objectName)
                this.replaceRange(replaceBracketFirst, replaceBracketLast + 2, dataToReplace).processString(node)
            }
        }
    }

    fun ObjectNode.getNodeData(name: String): String {
        return when (val data = this.getChildNode(name)) {
            is TextNode -> data.asText()
            is NullNode -> ""
            else -> data.toString()
        }
    }

    fun ObjectNode.getChildNode(name: String): JsonNode {

        val dotIndex = name.indexOfFirst { it == '.' }

        return if (dotIndex == -1) {
            this.get(name) ?: NullNode.instance
        } else {

            val attributeName = name.filterIndexed { index, _ -> index < dotIndex }

            if (attributeName.contains("|")) {
                val nameSplit = attributeName.split("|")

                val fieldName = nameSplit.first()
                when (val newNode = this.get(fieldName)) {
                    is ArrayNode -> {
                        when (val otherNode = newNode.get(nameSplit.last().toInt())) {
                            is ObjectNode -> otherNode.getChildNode(name.substring(dotIndex + 1))
                            else -> NullNode.instance
                        }
                    }
                    else -> NullNode.instance
                }
            } else {
                when (val newNode = this.get(attributeName)) {
                    is ObjectNode -> newNode.getChildNode(name.substring(dotIndex + 1))
                    else -> NullNode.instance
                }
            }
        }
    }
}