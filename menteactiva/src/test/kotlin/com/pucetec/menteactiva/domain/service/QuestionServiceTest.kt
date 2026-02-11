package com.pucetec.menteactiva.domain.service

import com.pucetec.menteactiva.domain.dto.CreateQuestionRequest
import com.pucetec.menteactiva.domain.entity.Activity
import com.pucetec.menteactiva.domain.entity.Category
import com.pucetec.menteactiva.domain.entity.Question
import com.pucetec.menteactiva.domain.repository.ActivityRepository
import com.pucetec.menteactiva.domain.repository.QuestionRepository
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class QuestionServiceTest {

    @Mock
    lateinit var questionRepository: QuestionRepository

    @Mock
    lateinit var activityRepository: ActivityRepository

    @InjectMocks
    lateinit var questionService: QuestionService

    private val category = Category(id = 1, name = "Cat", description = "Desc")
    private val activity = Activity(id = 1, title = "Act", difficultyLevel = 1, category = category)
    private val question = Question(id = 1, content = "Q1", correctAnswer = "A", activity = activity)

    @Test
    fun `getQuestionsByActivityId returns list when activity exists`() {
        `when`(activityRepository.existsById(1)).thenReturn(true)
        `when`(questionRepository.findByActivityId(1)).thenReturn(listOf(question))

        val result = questionService.getQuestionsByActivityId(1)

        assertEquals(1, result.size)
        assertEquals("Q1", result[0].content)
    }

    @Test
    fun `getQuestionsByActivityId throws exception when activity not found`() {
        `when`(activityRepository.existsById(1)).thenReturn(false)

        assertThrows(EntityNotFoundException::class.java) {
            questionService.getQuestionsByActivityId(1)
        }
    }

    @Test
    fun `createQuestion creates and returns question`() {
        val request = CreateQuestionRequest(content = "New Q", correctAnswer = "B", activityId = 1)
        `when`(activityRepository.findById(1)).thenReturn(Optional.of(activity))

        val savedQuestion = Question(id = 2, content = "New Q", correctAnswer = "B", activity = activity)
        `when`(questionRepository.save(any(Question::class.java))).thenReturn(savedQuestion)

        val result = questionService.createQuestion(request)

        assertEquals("New Q", result.content)
    }

    @Test
    fun `createQuestion throws exception when activity not found`() {
        val request = CreateQuestionRequest(content = "New Q", correctAnswer = "B", activityId = 1)
        `when`(activityRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows(EntityNotFoundException::class.java) {
            questionService.createQuestion(request)
        }
    }

    @Test
    fun `deleteQuestion deletes when exists`() {
        `when`(questionRepository.existsById(1)).thenReturn(true)
        doNothing().`when`(questionRepository).deleteById(1)

        assertDoesNotThrow { questionService.deleteQuestion(1) }
    }

    @Test
    fun `deleteQuestion throws exception when not exists`() {
        `when`(questionRepository.existsById(1)).thenReturn(false)

        assertThrows(EntityNotFoundException::class.java) {
            questionService.deleteQuestion(1)
        }
    }
}
