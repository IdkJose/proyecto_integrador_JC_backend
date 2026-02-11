package com.pucetec.menteactiva.domain.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    var email: String = "",

    @field:NotBlank(message = "Password is required")
    @Column(nullable = false)
    var password: String = "", // En un caso real esto se encriptaría, pero para clase lo dejaremos plano para que sea fácil de explicar.

    @field:NotBlank(message = "Display name is required")
    @Column(name = "display_name", nullable = false)
    var displayName: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as User
        return id != 0L && id == that.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "User(id=$id, email='$email')"
    }
}
