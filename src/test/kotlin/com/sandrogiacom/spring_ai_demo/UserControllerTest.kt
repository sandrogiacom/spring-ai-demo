package com.sandrogiacom.spring_ai_demo

import tools.jackson.databind.ObjectMapper
import com.sandrogiacom.spring_ai_demo.dto.UserRequest
import com.sandrogiacom.spring_ai_demo.model.User
import com.sandrogiacom.spring_ai_demo.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*
import kotlin.test.assertEquals

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
    }

    @Test
    fun `should create a user`() {
        val userRequest = UserRequest(name = "John Doe", email = "john@example.com", password = "password123")

        mockMvc.post("/api/users") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(userRequest)
        }.andExpect {
            status { isCreated() }
            jsonPath("$.name") { value("John Doe") }
            jsonPath("$.email") { value("john@example.com") }
            jsonPath("$.password") { doesNotExist() }
        }

        val users = userRepository.findAll()
        assertEquals(1, users.size)
        assertEquals("John Doe", users[0].name)
    }

    @Test
    fun `should get all users`() {
        userRepository.save(User(name = "User 1", email = "user1@example.com", password = "pass"))
        userRepository.save(User(name = "User 2", email = "user2@example.com", password = "pass"))

        mockMvc.get("/api/users") {
            param("page", "0")
            param("size", "10")
        }.andExpect {
            status { isOk() }
            jsonPath("$.content.length()") { value(2) }
            jsonPath("$.totalElements") { value(2) }
        }
    }

    @Test
    fun `should get users with pagination`() {
        userRepository.save(User(name = "User 1", email = "user1@example.com", password = "pass"))
        userRepository.save(User(name = "User 2", email = "user2@example.com", password = "pass"))

        mockMvc.get("/api/users") {
            param("page", "0")
            param("size", "1")
        }.andExpect {
            status { isOk() }
            jsonPath("$.content.length()") { value(1) }
            jsonPath("$.totalElements") { value(2) }
            jsonPath("$.content[0].name") { value("User 1") }
        }
    }

    @Test
    fun `should get user by id`() {
        val savedUser = userRepository.save(User(name = "John", email = "john@example.com", password = "pass"))

        mockMvc.get("/api/users/${savedUser.id}")
            .andExpect {
                status { isOk() }
                jsonPath("$.name") { value("John") }
                jsonPath("$.password") { doesNotExist() }
            }
    }

    @Test
    fun `should update user`() {
        val savedUser = userRepository.save(User(name = "Old Name", email = "old@example.com", password = "pass"))
        val updatedUserRequest = UserRequest(name = "New Name", email = "new@example.com", password = "newpass")

        mockMvc.put("/api/users/${savedUser.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedUserRequest)
        }.andExpect {
            status { isOk() }
            jsonPath("$.name") { value("New Name") }
            jsonPath("$.email") { value("new@example.com") }
            jsonPath("$.password") { doesNotExist() }
        }
    }

    @Test
    fun `should delete user`() {
        val savedUser = userRepository.save(User(name = "Delete Me", email = "delete@example.com", password = "pass"))

        mockMvc.delete("/api/users/${savedUser.id}")
            .andExpect {
                status { isNoContent() }
            }

        assertEquals(0, userRepository.count())
    }

    @Test
    @Disabled
    fun `should have swagger documentation`() {
        mockMvc.get("/v3/api-docs")
            .andExpect {
                status { isOk() }
                jsonPath("$.info.title") { exists() }
            }
    }
}
