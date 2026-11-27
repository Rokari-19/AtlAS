package com.example.atlas.core.inference
import com.example.atlas.core.execution.ToolCall

enum class MessageRole {
    SYSTEM,
    USER,
    ASSISTANT,
    TOOL
}

data class ModelMessage (
    val role: MessageRole,
    val content: String,
    val toolCalls: List<ToolCall> = emptyList(),
    val toolCallId: String? = null,
    val toolName: String? = null
)

