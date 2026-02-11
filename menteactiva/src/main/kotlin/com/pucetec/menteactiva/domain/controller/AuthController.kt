package com.pucetec.menteactiva.domain.controller

import com.pucetec.menteactiva.domain.dto.LoginRequest
import com.pucetec.menteactiva.domain.dto.RegisterRequest
import com.pucetec.menteactiva.domain.dto.UserResponse
import com.pucetec.menteactiva.domain.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.register(request))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.login(request))
    }

    @PutMapping("/update/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody request: com.pucetec.menteactiva.domain.dto.UpdateUserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.updateUser(id, request))
    }
}
