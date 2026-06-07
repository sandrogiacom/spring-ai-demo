package com.sandrogiacom.spring_ai_demo.service

import com.sandrogiacom.spring_ai_demo.model.User
import com.sandrogiacom.spring_ai_demo.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    fun findAll(pageable: Pageable): Page<User> = userRepository.findAll(pageable)

    fun findById(id: Long): User? = userRepository.findById(id).orElse(null)

    @Transactional
    fun save(user: User): User = userRepository.save(user)

    @Transactional
    fun update(id: Long, userDetails: User): User? {
        val user = findById(id) ?: return null
        user.name = userDetails.name
        user.email = userDetails.email
        user.password = userDetails.password
        return userRepository.save(user)
    }

    @Transactional
    fun delete(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
