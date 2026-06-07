package com.sandrogiacom.spring_ai_demo.controller

import com.sandrogiacom.spring_ai_demo.model.User
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
    fun getAllUsers(@ParameterObject pageable: Pageable): Page<User> = userService.findAll(pageable)

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.findById(id)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val savedUser = userService.save(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário existente")
    fun updateUser(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<User> {
        val updatedUser = userService.update(id, user)
        return if (updatedUser != null) ResponseEntity.ok(updatedUser) else ResponseEntity.notFound().build()
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
