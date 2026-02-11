package com.pucetec.menteactiva.domain.repository

import com.pucetec.menteactiva.domain.entity.Activity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository : JpaRepository<Activity, Long> {
    fun findByCategoryId(categoryId: Long): List<Activity>
}
