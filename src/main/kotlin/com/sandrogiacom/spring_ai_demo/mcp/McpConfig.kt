package com.sandrogiacom.spring_ai_demo.mcp

import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class McpConfig {

    @Bean
    fun userTools(userMcpTools: UserMcpTools): ToolCallbackProvider {
        return MethodToolCallbackProvider.builder().toolObjects(userMcpTools).build()
    }
}
