package com.pucetec.menteactiva.domain.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

// Usamos 'class' normal (no 'data class') para que JPA maneje correctamente las entidades mutables
@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "El email es requerido")
    @field:Email(message = "El email debe ser válido")
    @Column(nullable = false, unique = true)
    var email: String = "",

    @field:NotBlank(message = "La contraseña es requerida")
    @Column(nullable = false)
    var password: String = "", // Guardamos el hash encriptado, no la contraseña plana

    @field:NotBlank(message = "El nombre es requerido")
    @Column(name = "display_name", nullable = false)
    var displayName: String = ""
) {
    // Implementación correcta de equals y hashCode para entidades JPA
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
