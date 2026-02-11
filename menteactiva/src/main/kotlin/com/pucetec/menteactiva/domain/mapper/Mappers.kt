package com.pucetec.menteactiva.domain.mapper

import com.pucetec.menteactiva.domain.dto.*
import com.pucetec.menteactiva.domain.entity.Activity
import com.pucetec.menteactiva.domain.entity.Category
import com.pucetec.menteactiva.domain.entity.Question

// --- Category Mappers ---

fun Category.toResponse(): CategoryResponse {
    return CategoryResponse(
        id = this.id!!,
        name = this.name,
        description = this.description
    )
}

fun Category.toResponseWithActivities(): CategoryWithActivitiesResponse {
    return CategoryWithActivitiesResponse(
        id = this.id!!,
        name = this.name,
        description = this.description,
        activities = this.activities.map { it.toResponse() }
    )
}

fun CreateCategoryRequest.toEntity(): Category {
    return Category(
        name = this.name,
        description = this.description
    )
}

// --- Activity Mappers ---

fun Activity.toResponse(): ActivityResponse {
    return ActivityResponse(
        id = this.id!!,
        title = this.title,
        difficultyLevel = this.difficultyLevel,
        categoryId = this.category.id!!
    )
}

fun CreateActivityRequest.toEntity(category: Category): Activity {
    return Activity(
        title = this.title,
        difficultyLevel = this.difficultyLevel,
        category = category
    )
}

// --- Question Mappers ---

fun Question.toResponse(): QuestionResponse {
    return QuestionResponse(
        id = this.id!!,
        content = this.content,
        correctAnswer = this.correctAnswer,
        activityId = this.activity.id!!
    )
}

fun CreateQuestionRequest.toEntity(activity: Activity): Question {
    return Question(
        content = this.content,
        correctAnswer = this.correctAnswer,
        activity = activity
    )
}
