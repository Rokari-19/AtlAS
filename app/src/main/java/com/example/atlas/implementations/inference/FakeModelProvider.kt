package com.example.atlas.implementations.inference

import com.example.atlas.core.inference.*
import com.example.atlas.core.execution.*
import com.example.atlas.core.intelligence.*
import kotlinx.serialization.json.buildJsonObject
import com.example.atlas.implementations.tools.*


class FakeModelProvider: ModelProvider {
    override suspend fun initialize() {}
    override suspend fun unload() {}

    override suspend fun generate(request: ModelRequest): ModelResponse {
        val lastMessage = request.messages.lastOrNull()

        return when(lastMessage?.role) {
            MessageRole.USER -> {
                if(
                    lastMessage.content.contains(
                        "battery",
                        ignoreCase = true
                    )
                ) {
                    ModelResponse(
                        toolCalls = listOf(
                            ToolCall(
                                id = "call_001",
                                name = "get_battery_status",
                                arguments = buildJsonObject {}
                            )
                        )
                    )
                } else {
                    ModelResponse(
                        content = "I don't have a tool for this action yet"
                    )
                }
            }

            MessageRole.TOOL -> {
                ModelResponse(
                    content = "your battery is at 82 and is not charging"
                )
            }

            else -> ModelResponse(
                content = "i'm ready!"
            )
        }
    }
}


fun main() {
    val registry = ToolRegistry()

    registry.register(
        FakeBatteryStatusTool()
    )

    val provider = FakeModelProvider()

    provider.initialize()

    val agent = AgentRunner(
        modelProvider = provider,
        toolRegistry = registry
    )

    val response = agent.run(
        AgentRequest(
            message = "What's my current battery percentage?"
        )
    )

    println(response.message)
}