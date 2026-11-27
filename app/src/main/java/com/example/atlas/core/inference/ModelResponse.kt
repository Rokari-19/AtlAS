package com.example.atlas.core.inference

import com.example.atlas.core.execution.ToolCall

data class ModelResponse(
    val content: String? = null,
    val toolCalls: List<ToolCall> = emptyList()
)