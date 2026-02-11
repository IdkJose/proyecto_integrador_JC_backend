package com.pucetec.menteactiva.domain.service

import com.pucetec.menteactiva.domain.dto.CategoryResponse
import com.pucetec.menteactiva.domain.dto.CategoryWithActivitiesResponse
import com.pucetec.menteactiva.domain.dto.CreateCategoryRequest
import com.pucetec.menteactiva.domain.mapper.toEntity
import com.pucetec.menteactiva.domain.mapper.toResponse
import com.pucetec.menteactiva.domain.mapper.toResponseWithActivities
import com.pucetec.menteactiva.domain.repository.CategoryRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    @Transactional(readOnly = true)
    fun getAllCategories(): List<CategoryResponse> {
        return categoryRepository.findAll().map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun getCategoryById(id: Long): CategoryWithActivitiesResponse {
        val category = categoryRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Category not found with id: $id") }
        return category.toResponseWithActivities()
    }

    @Transactional
    fun createCategory(request: CreateCategoryRequest): CategoryResponse {
        val entity = request.toEntity()
        val saved = categoryRepository.save(entity)
        return saved.toResponse()
    }

    @Transactional
    fun updateCategory(id: Long, request: CreateCategoryRequest): CategoryResponse {
        val existing = categoryRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Category not found with id: $id") }
        
        existing.name = request.name
        existing.description = request.description
        
        val updated = categoryRepository.save(existing)
        return updated.toResponse()
    }

    @Transactional
    fun deleteCategory(id: Long) {
        if (!categoryRepository.existsById(id)) {
            throw EntityNotFoundException("Category not found with id: $id")
        }
        categoryRepository.deleteById(id)
    }
}
