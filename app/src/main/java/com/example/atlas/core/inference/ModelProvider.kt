package com.example.atlas.core.inference

interface ModelProvider {

    suspend fun initialize()

    suspend fun generate(
        request: ModelRequest
    ): ModelResponse

    suspend fun unload()
}