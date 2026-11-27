package com.example.atlas

import com.example.atlas.core.execution.ToolRegistry
import com.example.atlas.core.intelligence.AgentRequest
import com.example.atlas.core.intelligence.AgentRunner
import com.example.atlas.implementations.inference.FakeModelProvider
import com.example.atlas.implementations.tools.FakeBatteryStatusTool
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class AgentRunnerTest{
    @Test
    fun batteryRequest_executesTool_andReturnsResponse() = runBlocking {
        val registry = ToolRegistry()

        registry.register(FakeBatteryStatusTool())

        val provider = FakeModelProvider()

        val agent = AgentRunner(
            modelProvider = provider,
            toolRegistry = registry
        )

        val response = agent.run(
            AgentRequest(
                message = "what's my battery level?"
            )
        )

        assertEquals(
            "your battery is at 82 and is not charging",
            response.message
        )

        assertEquals(1, response.executedTools.size)

        assertFalse(response.executedTools.first().isError)
    }

    @Test
    fun unsupportedRequest_doesNotExecuteTools() = runBlocking {

        val registry = ToolRegistry()
        registry.register(FakeBatteryStatusTool())

        val provider = FakeModelProvider()

        val agent = AgentRunner(
            modelProvider = provider,
            toolRegistry = registry
        )

        val response = agent.run(
            AgentRequest(
                message = "Tell me a joke."
            )
        )

        assertEquals(
            "I don't have a tool for this action yet",
            response.message
        )

        assertEquals(0, response.executedTools.size)
    }
}
