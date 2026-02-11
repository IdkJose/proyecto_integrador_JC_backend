package com.pucetec.menteactiva.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

// Entidad para registrar el estado de ánimo del usuario (check-in)
// Tiene una relación ManyToOne con User: un usuario puede tener muchos registros de ánimo
@Entity
@Table(name = "mood_entries")
class MoodEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // Relación con la tabla users (clave foránea)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    // Identificador del estado de ánimo: great, good, okay, bad, awful
    @Column(name = "mood_id", nullable = false)
    var moodId: String = "",

    // Etiqueta legible: Excelente, Bien, Neutral, Mal, Pésimo
    @Column(nullable = false)
    var label: String = "",

    // Nota opcional que el usuario escribe al hacer check-in
    @Column(columnDefinition = "TEXT")
    var note: String? = null,

    // Fecha y hora del registro
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as MoodEntry
        return id != 0L && id == that.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String = "MoodEntry(id=$id, moodId='$moodId', userId=${user?.id})"
}
