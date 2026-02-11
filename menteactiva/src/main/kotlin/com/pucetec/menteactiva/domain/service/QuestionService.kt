package com.pucetec.menteactiva.domain.service

import com.pucetec.menteactiva.domain.dto.CreateQuestionRequest
import com.pucetec.menteactiva.domain.dto.QuestionResponse
import com.pucetec.menteactiva.domain.mapper.toEntity
import com.pucetec.menteactiva.domain.mapper.toResponse
import com.pucetec.menteactiva.domain.repository.ActivityRepository
import com.pucetec.menteactiva.domain.repository.QuestionRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestionService(
    private val questionRepository: QuestionRepository,
    private val activityRepository: ActivityRepository
) {

    // Obtiene las preguntas asociadas a una actividad (para el Quiz)
    @Transactional(readOnly = true)
    fun getQuestionsByActivityId(activityId: Long): List<QuestionResponse> {
        if (!activityRepository.existsById(activityId)) {
            throw EntityNotFoundException("Activity not found with id: $activityId")
        }
        return questionRepository.findByActivityId(activityId).map { it.toResponse() }
    }

    @Transactional
    fun createQuestion(request: CreateQuestionRequest): QuestionResponse {
        val activity = activityRepository.findById(request.activityId)
            .orElseThrow { EntityNotFoundException("Activity not found with id: ${request.activityId}") }

        val entity = request.toEntity(activity)
        val saved = questionRepository.save(entity)
        return saved.toResponse()
    }
    
    @Transactional
    fun deleteQuestion(id: Long) {
         if (!questionRepository.existsById(id)) {
            throw EntityNotFoundException("Question not found with id: $id")
        }
        questionRepository.deleteById(id)
    }
}
