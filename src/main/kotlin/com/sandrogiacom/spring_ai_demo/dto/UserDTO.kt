package com.sandrogiacom.spring_ai_demo.dto

import com.sandrogiacom.spring_ai_demo.model.User

data class UserResponse(
    val id: Long?,
    val name: String,
    val email: String
)

data class UserRequest(
    val name: String,
    val email: String,
    val password: String
)

fun User.toResponse() = UserResponse(
    id = this.id,
    name = this.name,
    email = this.email
)

fun UserRequest.toModel() = User(
    name = this.name,
    email = this.email,
    password = this.password
)
