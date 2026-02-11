package com.pucetec.menteactiva.domain.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    val email: String = "",

    @field:NotBlank(message = "Password is required")
    @Column(nullable = false)
    val password: String = "", // En un caso real esto se encriptaría, pero para clase lo dejaremos plano para que sea fácil de explicar.

    @field:NotBlank(message = "Display name is required")
    @Column(name = "display_name", nullable = false)
    val displayName: String = ""
)
