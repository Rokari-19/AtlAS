package com.example.atlas.core.execution

import kotlinx.serialization.json.JsonObject

data class ToolResult(
//    how results of a tool's execution is displayed
    val callId: String,
    val toolName: String,
    val output: JsonObject,
    val isError: Boolean = false
)