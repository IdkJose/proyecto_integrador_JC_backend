package com.pucetec.menteactiva.domain.service

import com.pucetec.menteactiva.domain.dto.CreateMoodEntryRequest
import com.pucetec.menteactiva.domain.dto.MoodEntryResponse
import com.pucetec.menteactiva.domain.entity.MoodEntry
import com.pucetec.menteactiva.domain.repository.MoodEntryRepository
import com.pucetec.menteactiva.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// Servicio que maneja la lógica de negocio para los registros de estado de ánimo
@Service
class MoodEntryService(
    private val moodEntryRepository: MoodEntryRepository,
    private val userRepository: UserRepository
) {

    // Obtener todos los registros de un usuario específico
    fun getEntriesByUser(userId: Long): List<MoodEntryResponse> {
        return moodEntryRepository.findByUserIdOrderByCreatedAtDesc(userId)
            .map { it.toResponse() }
    }

    // Crear un nuevo registro de estado de ánimo
    @Transactional
    fun createEntry(userId: Long, request: CreateMoodEntryRequest): MoodEntryResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }

        val entry = MoodEntry(
            user = user,
            moodId = request.moodId,
            label = request.label,
            note = request.note
        )

        val saved = moodEntryRepository.save(entry)
        return saved.toResponse()
    }

    // Eliminar un registro por su ID
    @Transactional
    fun deleteEntry(entryId: Long) {
        if (!moodEntryRepository.existsById(entryId)) {
            throw IllegalArgumentException("Registro no encontrado")
        }
        moodEntryRepository.deleteById(entryId)
    }

    // Extensión privada para convertir entidad a DTO de respuesta
    private fun MoodEntry.toResponse() = MoodEntryResponse(
        id = this.id,
        userId = this.user?.id ?: 0,
        moodId = this.moodId,
        label = this.label,
        note = this.note,
        createdAt = this.createdAt.toString()
    )
}
