package com.pucetec.menteactiva.domain.controller

import com.pucetec.menteactiva.domain.dto.CreateQuestionRequest
import com.pucetec.menteactiva.domain.dto.QuestionResponse
import com.pucetec.menteactiva.domain.service.QuestionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/questions")

class QuestionController(
    private val questionService: QuestionService
) {

    @GetMapping("/activity/{activityId}")
    fun getQuestionsByActivity(@PathVariable activityId: Long): ResponseEntity<List<QuestionResponse>> {
        return ResponseEntity.ok(questionService.getQuestionsByActivityId(activityId))
    }

    @PostMapping
    fun createQuestion(@Valid @RequestBody request: CreateQuestionRequest): ResponseEntity<QuestionResponse> {
        val created = questionService.createQuestion(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    @DeleteMapping("/{id}")
    fun deleteQuestion(@PathVariable id: Long): ResponseEntity<Void> {
        questionService.deleteQuestion(id)
        return ResponseEntity.noContent().build()
    }
}
