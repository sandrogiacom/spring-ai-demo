package com.sandrogiacom.spring_ai_demo.controller

import com.sandrogiacom.spring_ai_demo.dto.UserRequest
import com.sandrogiacom.spring_ai_demo.dto.UserResponse
import com.sandrogiacom.spring_ai_demo.dto.toModel
import com.sandrogiacom.spring_ai_demo.dto.toResponse
import com.sandrogiacom.spring_ai_demo.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "CRUD de usuários")
class UserController(private val userService: UserService) {

    @GetMapping
    @Operation(summary = "Listar todos os usuários com paginação")
    fun getAllUsers(@ParameterObject pageable: Pageable): Page<UserResponse> =
        userService.findAll(pageable).map { it.toResponse() }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        val user = userService.findById(id)
        return if (user != null) ResponseEntity.ok(user.toResponse()) else ResponseEntity.notFound().build()
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val savedUser = userService.save(userRequest.toModel())
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser.toResponse())
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário existente")
    fun updateUser(@PathVariable id: Long, @RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val updatedUser = userService.update(id, userRequest.toModel())
        return if (updatedUser != null) ResponseEntity.ok(updatedUser.toResponse()) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        return if (userService.delete(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
