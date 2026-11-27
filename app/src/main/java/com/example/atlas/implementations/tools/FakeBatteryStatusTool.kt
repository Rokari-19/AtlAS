package com.example.atlas.implementations.tools

import com.example.atlas.core.execution.*

import kotlinx.serialization.json.*


class FakeBatteryStatusTool: AgentTool {
    override val definition = ToolDefinition(
        name = "get_battery_status",
        description = "Returns the current battery percentage",
        inputSchema = buildJsonObject {
            put("type", "object")
            put("properties", buildJsonObject {  })
            put("additionalProperties", false)
        }
    )

    override suspend fun execute(call: ToolCall): ToolResult {
        require(call.arguments.isEmpty()) {
            "this tool doesn't accept arguments"
        }

        return ToolResult(
            callId = call.id,
            toolName = definition.name,
            output = buildJsonObject {
                put("percentage", 82)
                put("charging", false)
            }
        )
    }
}