package com.pucetec.menteactiva.domain.service

import com.pucetec.menteactiva.domain.dto.CreateCategoryRequest
import com.pucetec.menteactiva.domain.entity.Category
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
class CategoryServiceTest {

    @Mock
    lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    lateinit var categoryService: CategoryService

    @Test
    fun `getAllCategories returns list of category responses`() {
        val categories = listOf(
            Category(id = 1, name = "Memory", description = "Memory games"),
            Category(id = 2, name = "Logic", description = "Logic games")
        )
        `when`(categoryRepository.findAll()).thenReturn(categories)

        val result = categoryService.getAllCategories()

        assertEquals(2, result.size)
        assertEquals("Memory", result[0].name)
    }

    @Test
    fun `getCategoryById returns category response when found`() {
        val category = Category(id = 1, name = "Memory", description = "Memory games")
        `when`(categoryRepository.findById(1)).thenReturn(Optional.of(category))

        val result = categoryService.getCategoryById(1)

        assertEquals("Memory", result.name)
    }

    @Test
    fun `getCategoryById throws EntityNotFoundException when not found`() {
        `when`(categoryRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows(EntityNotFoundException::class.java) {
            categoryService.getCategoryById(1)
        }
    }

    @Test
    fun `createCategory saves and returns category response`() {
        val request = CreateCategoryRequest(name = "New Category", description = "Desc")
        val savedEntity = Category(id = 1, name = "New Category", description = "Desc")
        
        `when`(categoryRepository.save(any(Category::class.java))).thenReturn(savedEntity)

        val result = categoryService.createCategory(request)

        assertEquals("New Category", result.name)
    }

    @Test
    fun `updateCategory updates and returns response when found`() {
        val existing = Category(id = 1, name = "Old", description = "Old Desc")
        val request = CreateCategoryRequest(name = "New", description = "New Desc")
        val updated = Category(id = 1, name = "New", description = "New Desc")

        `when`(categoryRepository.findById(1)).thenReturn(Optional.of(existing))
        `when`(categoryRepository.save(any(Category::class.java))).thenReturn(updated)

        val result = categoryService.updateCategory(1, request)

        assertEquals("New", result.name)
        assertEquals("New Desc", result.description)
    }

    @Test
    fun `updateCategory throws exception when not found`() {
        val request = CreateCategoryRequest(name = "New", description = "New Desc")
        `when`(categoryRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows(EntityNotFoundException::class.java) {
            categoryService.updateCategory(1, request)
        }
    }

    @Test
    fun `deleteCategory deletes when exists`() {
        `when`(categoryRepository.existsById(1)).thenReturn(true)
        doNothing().`when`(categoryRepository).deleteById(1)

        assertDoesNotThrow { categoryService.deleteCategory(1) }
        verify(categoryRepository).deleteById(1)
    }

    @Test
    fun `deleteCategory throws exception when not exists`() {
        `when`(categoryRepository.existsById(1)).thenReturn(false)

        assertThrows(EntityNotFoundException::class.java) {
            categoryService.deleteCategory(1)
        }
    }
}
