package com.example.atlas.core.execution

interface AgentTool {
//  this handles the executable capabilities
    val definition: ToolDefinition

    suspend fun execute(
        call: ToolCall
    ): ToolResult

}