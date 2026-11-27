package com.example.atlas.core.execution

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.put

class ToolRegistry{
    private val tools = mutableMapOf<String, AgentTool>()
//    this is for managing the available tools on device
    fun register(tool: AgentTool) {
        val name = tool.definition.name

        require(name.isNotBlank()) {
            "Tool name can't be empty"
        }
        require(name !in tools) {
            "Tool with name '$name' is already registered"
        }

        tools[name] = tool
    }

    fun getDefinitions(): List<ToolDefinition> {
        return tools.values.map {it.definition}
    }

    suspend fun execute(call: ToolCall): ToolResult {
        val tool = tools[call.name]

        if (tool == null) {
            return ToolResult(
                callId = call.id,
                toolName = call.name,
                output = kotlinx.serialization.json.buildJsonObject {
                    put("error", "Unknown tool: ${call.name}")
                },
                isError = true
            )
        }

        return try{
            tool.execute(call)
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {

            ToolResult(
                callId = call.id,
                toolName = call.name,
                output = kotlinx.serialization.json.buildJsonObject {
                    put("Error", exception.message?.takeIf { it.isNotBlank() } ?: "Tool Execution Failed!")
                },
                isError = true
            )
        }
    }
}