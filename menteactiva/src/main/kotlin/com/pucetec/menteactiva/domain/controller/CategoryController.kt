package com.pucetec.menteactiva.domain.controller

import com.pucetec.menteactiva.domain.dto.CategoryResponse
import com.pucetec.menteactiva.domain.dto.CategoryWithActivitiesResponse
import com.pucetec.menteactiva.domain.dto.CreateCategoryRequest
import com.pucetec.menteactiva.domain.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = ["*"]) // Allow generic access for simplicity in demo
class CategoryController(
    private val categoryService: CategoryService
) {

    @GetMapping
    fun getAllCategories(): ResponseEntity<List<CategoryResponse>> {
        return ResponseEntity.ok(categoryService.getAllCategories())
    }

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<CategoryWithActivitiesResponse> {
        return ResponseEntity.ok(categoryService.getCategoryById(id))
    }

    @PostMapping
    fun createCategory(@Valid @RequestBody request: CreateCategoryRequest): ResponseEntity<CategoryResponse> {
        val created = categoryService.createCategory(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable id: Long, 
        @Valid @RequestBody request: CreateCategoryRequest
    ): ResponseEntity<CategoryResponse> {
        return ResponseEntity.ok(categoryService.updateCategory(id, request))
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Void> {
        categoryService.deleteCategory(id)
        return ResponseEntity.noContent().build()
    }
}
