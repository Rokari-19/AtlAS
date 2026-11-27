package com.example.atlas.core.intelligence

import com.example.atlas.core.execution.ToolResult

data class AgentResponse(
    val message: String,
    val executedTools: List<ToolResult>
)