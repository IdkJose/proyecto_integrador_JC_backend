package com.pucetec.menteactiva.domain.service

import com.pucetec.menteactiva.domain.dto.CreateActivityRequest
import com.pucetec.menteactiva.domain.entity.Activity
import com.pucetec.menteactiva.domain.entity.Category
import com.pucetec.menteactiva.domain.repository.ActivityRepository
import com.pucetec.menteactiva.domain.repository.CategoryRepository
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
class ActivityServiceTest {

    @Mock
    lateinit var activityRepository: ActivityRepository

    @Mock
    lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    lateinit var activityService: ActivityService

    private val category = Category(id = 1, name = "Cat1", description = "Desc")
    private val activity = Activity(id = 1, title = "Act1", difficultyLevel = 1, category = category)

    @Test
    fun `getActivitiesByCategoryId returns list when category exists`() {
        `when`(categoryRepository.existsById(1)).thenReturn(true)
        `when`(activityRepository.findByCategoryId(1)).thenReturn(listOf(activity))

        val result = activityService.getActivitiesByCategoryId(1)

        assertEquals(1, result.size)
        assertEquals("Act1", result[0].title)
    }

    @Test
    fun `getActivitiesByCategoryId throws exception when category not found`() {
        `when`(categoryRepository.existsById(1)).thenReturn(false)

        assertThrows(EntityNotFoundException::class.java) {
            activityService.getActivitiesByCategoryId(1)
        }
    }

    @Test
    fun `getActivityById returns activity when found`() {
        `when`(activityRepository.findById(1)).thenReturn(Optional.of(activity))

        val result = activityService.getActivityById(1)

        assertEquals("Act1", result.title)
    }

    @Test
    fun `getActivityById throws exception when not found`() {
        `when`(activityRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows(EntityNotFoundException::class.java) {
            activityService.getActivityById(1)
        }
    }

    @Test
    fun `createActivity creates and returns activity`() {
        val request = CreateActivityRequest(title = "New Act", difficultyLevel = 2, categoryId = 1)
        `when`(categoryRepository.findById(1)).thenReturn(Optional.of(category))
        
        val savedActivity = Activity(id = 2, title = "New Act", difficultyLevel = 2, category = category)
        `when`(activityRepository.save(any(Activity::class.java))).thenReturn(savedActivity)

        val result = activityService.createActivity(request)

        assertEquals("New Act", result.title)
    }

    @Test
    fun `createActivity throws exception when category not found`() {
        val request = CreateActivityRequest(title = "New Act", difficultyLevel = 2, categoryId = 1)
        `when`(categoryRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows(EntityNotFoundException::class.java) {
            activityService.createActivity(request)
        }
    }

    @Test
    fun `deleteActivity deletes when exists`() {
        `when`(activityRepository.existsById(1)).thenReturn(true)
        doNothing().`when`(activityRepository).deleteById(1)

        assertDoesNotThrow { activityService.deleteActivity(1) }
    }

    @Test
    fun `deleteActivity throws exception when not exists`() {
        `when`(activityRepository.existsById(1)).thenReturn(false)

        assertThrows(EntityNotFoundException::class.java) {
            activityService.deleteActivity(1)
        }
    }
}
