package com.example.atlas.core.execution

class ToolRegistry{
    private val tools = mutableMapOf<String, AgentTool>()
//    this is for managing the available tools on device
fun register(tool: AgentTool) {
    val name = tool.definition.name

    require(name.isNotBlank()) {
        "Tool name can't be empty"
    }
}
}