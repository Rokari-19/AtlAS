package com.example.atlas.core.inference

import com.example.atlas.core.execution.ToolDefinition

data class ModelRequest(
    val messages: List<ModelMessage>,
    val tools: List<ToolDefinition>
)