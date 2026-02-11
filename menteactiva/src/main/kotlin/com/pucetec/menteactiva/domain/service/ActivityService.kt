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

@Service // Indica que esta clase contiene la lógica de negocio y Spring la administra
class ActivityService(
    // Inyección de dependencias de los repositorios
    private val activityRepository: ActivityRepository,
    private val categoryRepository: CategoryRepository
) {

    // @Transactional(readOnly = true) optimiza las consultas de solo lectura
    @Transactional(readOnly = true)
    fun getActivitiesByCategoryId(categoryId: Long): List<ActivityResponse> {
        if (!categoryRepository.existsById(categoryId)) {
            throw EntityNotFoundException("Category not found with id: $categoryId")
        }
        // Busca las actividades y las convierte a DTOs (Data Transfer Objects)
        return activityRepository.findByCategoryId(categoryId).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun getActivityById(id: Long): ActivityResponse {
        return activityRepository.findById(id)
            .map { it.toResponse() }
            .orElseThrow { EntityNotFoundException("Activity not found with id: $id") }
    }

    // @Transactional asegura que si algo falla, se haga Rollback (se deshagan los cambios)
    @Transactional
    fun createActivity(request: CreateActivityRequest): ActivityResponse {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { EntityNotFoundException("Category not found with id: ${request.categoryId}") }
        
        // Convertimos el DTO a Entidad para guardarlo en BD
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
