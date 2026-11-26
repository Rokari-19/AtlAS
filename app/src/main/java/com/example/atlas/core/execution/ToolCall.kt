package com.example.atlas.core.execution

import kotlinx.serialization.json.JsonObject

data class ToolCall(
//    tool execution request formatting
    val id: String,
    val name: String,
    val description: String,
    val arguments: JsonObject
)