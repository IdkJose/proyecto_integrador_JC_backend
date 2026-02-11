package com.pucetec.menteactiva.domain.repository

import com.pucetec.menteactiva.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository // Indica que es un componente de acceso a datos
interface UserRepository : JpaRepository<User, Long> {
    // Spring Data JPA implementa automáticamente este método basado en el nombre
    // SELECT * FROM users WHERE email = ?
    fun findByEmail(email: String): Optional<User>
}
