package com.sandrogiacom.spring_ai_demo.mcp

import com.sandrogiacom.spring_ai_demo.dto.UserRequest
import com.sandrogiacom.spring_ai_demo.dto.UserResponse
import com.sandrogiacom.spring_ai_demo.dto.toModel
import com.sandrogiacom.spring_ai_demo.dto.toResponse
import com.sandrogiacom.spring_ai_demo.service.UserService
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class UserMcpTools(private val userService: UserService) {

    @Tool(description = "List all users with pagination")
    fun listUsers(
        @ToolParam(description = "Page number (0-based)") page: Int = 0,
        @ToolParam(description = "Page size") size: Int = 10
    ): List<UserResponse> {
        return userService.findAll(PageRequest.of(page, size)).content.map { it.toResponse() }
    }

    @Tool(description = "Get a user by ID")
    fun getUserById(
        @ToolParam(description = "User ID") id: Long
    ): UserResponse? {
        return userService.findById(id)?.toResponse()
    }

    @Tool(description = "Create a new user")
    fun createUser(
        @ToolParam(description = "User name") name: String,
        @ToolParam(description = "User email") email: String,
        @ToolParam(description = "User password") password: String
    ): UserResponse {
        val user = UserRequest(name = name, email = email, password = password).toModel()
        return userService.save(user).toResponse()
    }

    @Tool(description = "Update an existing user")
    fun updateUser(
        @ToolParam(description = "User ID") id: Long,
        @ToolParam(description = "New user name") name: String,
        @ToolParam(description = "New user email") email: String,
        @ToolParam(description = "New user password") password: String
    ): UserResponse? {
        val userDetails = UserRequest(name = name, email = email, password = password).toModel()
        return userService.update(id, userDetails)?.toResponse()
    }

    @Tool(description = "Delete a user by ID")
    fun deleteUser(
        @ToolParam(description = "User ID") id: Long
    ): Boolean {
        return userService.delete(id)
    }
}
