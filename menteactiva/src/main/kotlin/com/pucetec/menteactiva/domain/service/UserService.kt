package com.pucetec.menteactiva.domain.service

import com.pucetec.menteactiva.domain.dto.LoginRequest
import com.pucetec.menteactiva.domain.dto.RegisterRequest
import com.pucetec.menteactiva.domain.dto.UserResponse
import com.pucetec.menteactiva.domain.entity.User
import com.pucetec.menteactiva.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Service
class UserService(private val userRepository: UserRepository) {

    // Inicializamos el encriptador de contraseñas (BCrypt)
    private val passwordEncoder = BCryptPasswordEncoder()

    @Transactional
    fun register(request: RegisterRequest): UserResponse {
        // 1. Validación de dominio institucional
        if (!request.email.endsWith("@puce.edu.ec")) {
            throw IllegalArgumentException("El correo debe ser institucional (@puce.edu.ec)")
        }
        // 2. Verificar si ya existe
        if (userRepository.findByEmail(request.email).isPresent) {
            throw IllegalArgumentException("El email ya esta registrado")
        }

        // 3. Crear entidad con contraseña encriptada
        val newUser = User(
            email = request.email,
            password = passwordEncoder.encode(request.password), // ¡Importante! Nunca guardar texto plano
            displayName = request.displayName
        )
        
        // 4. Guardar en Base de Datos
        val savedUser = userRepository.save(newUser)
        return UserResponse(savedUser.id, savedUser.email, savedUser.displayName, savedUser.goal, savedUser.reminderTime, savedUser.notificationsEnabled)
    }

    fun login(request: LoginRequest): UserResponse {
        // Buscamos al usuario por email
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { IllegalArgumentException("Credenciales invalidas") }

        // Comparamos la contraseña plana (request) con el hash (DB) usando BCrypt
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("Credenciales invalidas")
        }

        // Devolvemos las preferencias guardadas en BD para que el frontend las aplique
        return UserResponse(user.id, user.email, user.displayName, user.goal, user.reminderTime, user.notificationsEnabled)
    }

    @Transactional
    fun updateUser(id: Long, request: com.pucetec.menteactiva.domain.dto.UpdateUserRequest): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }

        // Modificamos directamente la entidad gestionada por JPA
        user.displayName = request.displayName
        user.goal = request.goal
        user.reminderTime = request.reminderTime
        user.notificationsEnabled = request.notificationsEnabled
        
        // Al terminar la transacción, JPA detecta el cambio y hace el UPDATE automáticamente
        val savedUser = userRepository.save(user)
        return UserResponse(savedUser.id, savedUser.email, savedUser.displayName, savedUser.goal, savedUser.reminderTime, savedUser.notificationsEnabled)
    }
}
