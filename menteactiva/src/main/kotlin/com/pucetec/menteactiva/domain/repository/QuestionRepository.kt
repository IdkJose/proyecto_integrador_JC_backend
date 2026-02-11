package com.pucetec.menteactiva.domain.repository

import com.pucetec.menteactiva.domain.entity.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<Question, Long> {
    // Recupera todas las preguntas de una actividad específica
    // SELECT * FROM question WHERE activity_id = ?
    fun findByActivityId(activityId: Long): List<Question>
}
