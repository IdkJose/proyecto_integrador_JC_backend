package com.pucetec.menteactiva.domain.repository

import com.pucetec.menteactiva.domain.entity.MoodEntry
import org.springframework.data.jpa.repository.JpaRepository

// Repositorio para acceder a los registros de estado de ánimo en la BD
// Spring Data JPA genera automáticamente la implementación de estas consultas
interface MoodEntryRepository : JpaRepository<MoodEntry, Long> {

    // Busca todos los registros de un usuario, ordenados del más reciente al más antiguo
    fun findByUserIdOrderByCreatedAtDesc(userId: Long): List<MoodEntry>

    // Cuenta cuántos registros tiene un usuario (útil para badges)
    fun countByUserId(userId: Long): Long
}
