package com.sandrogiacom.spring_ai_demo.repository

import com.sandrogiacom.spring_ai_demo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
