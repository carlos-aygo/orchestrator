package edu.eci.entities

data class OrchestratorEvent(
    val event: String,
    val data: Any,
    val id: String
)
