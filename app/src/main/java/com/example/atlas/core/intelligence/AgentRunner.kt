package com.example.atlas.core.intelligence

import com.example.atlas.core.execution.ToolRegistry //class
import com.example.atlas.core.execution.ToolResult
import com.example.atlas.core.inference.MessageRole
import com.example.atlas.core.inference.ModelMessage
import com.example.atlas.core.inference.ModelProvider //interface
import com.example.atlas.core.inference.ModelRequest

class AgentRunner(
    private val modelProvider: ModelProvider,
    private val toolRegistry: ToolRegistry,
    private val maxToolRounds: Int = 5
) {
    suspend fun run(request: AgentRequest): AgentResponse {
        val messages = mutableListOf(
            ModelMessage(
                role = MessageRole.SYSTEM,
                content = """
                    You are a local Android AI agent.
                    Use available tools when necessary.
                    Do not invent or hallucinate tool execution results.
                """.trimIndent()
            ),
            ModelMessage(
                role = MessageRole.USER,
                content = request.message
            )
        )

        val executedTools = mutableListOf<ToolResult>()
//          try to repeat execution of the tool(s)
//          in an event of initial failure and subsequent failures,
//          max of 5 retries
        repeat(maxToolRounds) {
            val modelResponse = modelProvider.generate(
                ModelRequest(
                    messages = messages.toList(),
                    tools = toolRegistry.getDefinitions()
                )
            )

//            if no more tools to call, return the model's response
            if (modelResponse.toolCalls.isEmpty()) {
                return AgentResponse(
                    message = modelResponse.content.orEmpty(),
                    executedTools = executedTools
                )
            }
//            return the assistant's tool call messages
            messages.add(
                ModelMessage(
                    role = MessageRole.ASSISTANT,
                    content = modelResponse.content.orEmpty(),
                    toolCalls = modelResponse.toolCalls
                )
            )

//            execute every requested tool
            for (call in modelResponse.toolCalls) {

                val result = toolRegistry.execute(call)
                executedTools.add(result)

                messages.add(
                    ModelMessage(
                        role = MessageRole.TOOL,
                        content = result.output.toString(),
                        toolCallId = result.callId,
                        toolName = result.toolName

                    )
                )
            }
        }

        return AgentResponse(
            message = "Maximum Tool exec rounds reached",
            executedTools = executedTools
        )
    }
}