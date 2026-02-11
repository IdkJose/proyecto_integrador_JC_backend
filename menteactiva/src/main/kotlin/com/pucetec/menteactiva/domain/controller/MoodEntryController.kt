package com.pucetec.menteactiva.domain.controller

import com.pucetec.menteactiva.domain.dto.CreateMoodEntryRequest
import com.pucetec.menteactiva.domain.dto.MoodEntryResponse
import com.pucetec.menteactiva.domain.service.MoodEntryService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Controlador REST para los registros de estado de ánimo (check-ins)
@RestController
@RequestMapping("/api/v1/mood-entries")
class MoodEntryController(private val moodEntryService: MoodEntryService) {

    // GET /api/v1/mood-entries/user/{userId} - Obtener todos los registros de un usuario
    @GetMapping("/user/{userId}")
    fun getByUser(@PathVariable userId: Long): ResponseEntity<List<MoodEntryResponse>> {
        return ResponseEntity.ok(moodEntryService.getEntriesByUser(userId))
    }

    // POST /api/v1/mood-entries/{userId} - Crear un nuevo registro de ánimo
    @PostMapping("/{userId}")
    fun create(
        @PathVariable userId: Long,
        @Valid @RequestBody request: CreateMoodEntryRequest
    ): ResponseEntity<MoodEntryResponse> {
        return ResponseEntity.ok(moodEntryService.createEntry(userId, request))
    }

    // DELETE /api/v1/mood-entries/{id} - Eliminar un registro
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        moodEntryService.deleteEntry(id)
        return ResponseEntity.noContent().build()
    }
}
