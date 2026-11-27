package com.example.atlas.implementations.inference

import com.example.atlas.core.inference.*
import com.example.atlas.core.execution.*
import kotlinx.serialization.json.buildJsonObject

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
                                description = "get current battery status",
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