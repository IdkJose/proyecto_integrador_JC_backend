package com.pucetec.menteactiva.domain.service

import com.pucetec.menteactiva.domain.dto.ActivityResponse
import com.pucetec.menteactiva.domain.dto.CreateActivityRequest
import com.pucetec.menteactiva.domain.mapper.toEntity
import com.pucetec.menteactiva.domain.mapper.toResponse
import com.pucetec.menteactiva.domain.repository.ActivityRepository
import com.pucetec.menteactiva.domain.repository.CategoryRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ActivityService(
    private val activityRepository: ActivityRepository,
    private val categoryRepository: CategoryRepository
) {

    @Transactional(readOnly = true)
    fun getActivitiesByCategoryId(categoryId: Long): List<ActivityResponse> {
        if (!categoryRepository.existsById(categoryId)) {
            throw EntityNotFoundException("Category not found with id: $categoryId")
        }
        return activityRepository.findByCategoryId(categoryId).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun getActivityById(id: Long): ActivityResponse {
        return activityRepository.findById(id)
            .map { it.toResponse() }
            .orElseThrow { EntityNotFoundException("Activity not found with id: $id") }
    }

    @Transactional
    fun createActivity(request: CreateActivityRequest): ActivityResponse {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { EntityNotFoundException("Category not found with id: ${request.categoryId}") }
        
        val entity = request.toEntity(category)
        val saved = activityRepository.save(entity)
        return saved.toResponse()
    }

    @Transactional
    fun deleteActivity(id: Long) {
        if (!activityRepository.existsById(id)) {
            throw EntityNotFoundException("Activity not found with id: $id")
        }
        activityRepository.deleteById(id)
    }
}
