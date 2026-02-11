package com.pucetec.menteactiva.domain.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.Email

// --- Category DTOs ---
data class CreateCategoryRequest(
    @field:NotBlank(message = "Name is required")
    @field:Size(max = 100)
    val name: String,
    
    val description: String?
)

data class CategoryResponse(
    val id: Long,
    val name: String,
    val description: String?
)

data class CategoryWithActivitiesResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val activities: List<ActivityResponse>
)

// --- Activity DTOs ---
data class CreateActivityRequest(
    @field:NotBlank(message = "Title is required")
    @field:Size(max = 150)
    val title: String,
    
    @field:NotNull(message = "Difficulty level is required")
    val difficultyLevel: Int,
    
    @field:NotNull(message = "Category ID is required")
    val categoryId: Long
)

data class ActivityResponse(
    val id: Long,
    val title: String,
    val difficultyLevel: Int,
    val categoryId: Long
)

// --- Question DTOs ---
data class CreateQuestionRequest(
    @field:NotBlank(message = "Content is required")
    val content: String,
    
    @field:NotBlank(message = "Correct answer is required")
    val correctAnswer: String,
    
    @field:NotNull(message = "Activity ID is required")
    val activityId: Long
)

data class QuestionResponse(
    val id: Long,
    val content: String,
    val correctAnswer: String,
    val activityId: Long
)

// --- Auth DTOs (Data Transfer Objects) ---
// Usamos DTOs para definir qué datos esperamos recibir del Frontend y qué respondemos.

data class LoginRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email
    val email: String,
    
    @field:NotBlank(message = "Password is required")
    val password: String
)

data class RegisterRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email
    val email: String,
    
    @field:NotBlank(message = "Password is required")
    val password: String,
    
    @field:NotBlank(message = "Name is required")
    val displayName: String
)

// Usado para endpoints que solo actualizan el perfil
data class UpdateUserRequest(
    @field:NotBlank(message = "Name is required")
    val displayName: String
)

// Lo que enviamos de vuelta al frontend (nunca devolver la contraseña)
data class UserResponse(
    val id: Long,
    val email: String,
    val displayName: String
)
