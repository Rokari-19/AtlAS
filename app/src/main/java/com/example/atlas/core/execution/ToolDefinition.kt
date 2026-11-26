package com.example.atlas.core.execution

import kotlinx.serialization.json.JsonObject

data class ToolDefinition(
//    handles describing the tool to our agent
//    advertising tool capabilities to the agent
    val name: String,
    val description: String,
    val inputSchema: JsonObject,
    val requiresConfirmation: Boolean = false
)