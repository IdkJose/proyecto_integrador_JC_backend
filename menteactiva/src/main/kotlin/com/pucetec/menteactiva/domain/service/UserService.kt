package com.pucetec.menteactiva.domain.service

import com.pucetec.menteactiva.domain.dto.LoginRequest
import com.pucetec.menteactiva.domain.dto.RegisterRequest
import com.pucetec.menteactiva.domain.dto.UserResponse
import com.pucetec.menteactiva.domain.entity.User
import com.pucetec.menteactiva.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun register(request: RegisterRequest): UserResponse {
        if (userRepository.findByEmail(request.email).isPresent) {
            throw IllegalArgumentException("El email ya esta registrado")
        }

        val newUser = User(
            email = request.email,
            password = request.password, // NOTA: En producción, usar BCryptEncoder
            displayName = request.displayName
        )
        val savedUser = userRepository.save(newUser)
        return UserResponse(savedUser.id, savedUser.email, savedUser.displayName)
    }

    fun login(request: LoginRequest): UserResponse {
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { IllegalArgumentException("Credenciales invalidas") }

        if (user.password != request.password) { 
            throw IllegalArgumentException("Credenciales invalidas")
        }

        return UserResponse(user.id, user.email, user.displayName)
    }

    @Transactional
    fun updateUser(id: Long, request: com.pucetec.menteactiva.domain.dto.UpdateUserRequest): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }

        val updatedUser = user.copy(displayName = request.displayName)
        val savedUser = userRepository.save(updatedUser)
        return UserResponse(savedUser.id, savedUser.email, savedUser.displayName)
    }
}
