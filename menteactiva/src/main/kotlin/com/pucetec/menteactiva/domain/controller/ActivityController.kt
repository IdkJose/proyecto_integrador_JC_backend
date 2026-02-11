package com.pucetec.menteactiva.domain.controller

import com.pucetec.menteactiva.domain.dto.ActivityResponse
import com.pucetec.menteactiva.domain.dto.CreateActivityRequest
import com.pucetec.menteactiva.domain.service.ActivityService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/activities")
@CrossOrigin(origins = ["*"])
class ActivityController(
    private val activityService: ActivityService
) {

    @GetMapping("/{id}")
    fun getActivityById(@PathVariable id: Long): ResponseEntity<ActivityResponse> {
        return ResponseEntity.ok(activityService.getActivityById(id))
    }
    
    // Endpoint helper to get activities by category
    @GetMapping("/category/{categoryId}")
    fun getActivitiesByCategory(@PathVariable categoryId: Long): ResponseEntity<List<ActivityResponse>> {
        return ResponseEntity.ok(activityService.getActivitiesByCategoryId(categoryId))
    }

    @PostMapping
    fun createActivity(@Valid @RequestBody request: CreateActivityRequest): ResponseEntity<ActivityResponse> {
        val created = activityService.createActivity(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    @DeleteMapping("/{id}")
    fun deleteActivity(@PathVariable id: Long): ResponseEntity<Void> {
        activityService.deleteActivity(id)
        return ResponseEntity.noContent().build()
    }
}
