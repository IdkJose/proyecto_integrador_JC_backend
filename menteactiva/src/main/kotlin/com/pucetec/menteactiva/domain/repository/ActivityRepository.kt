package com.pucetec.menteactiva.domain.repository

import com.pucetec.menteactiva.domain.entity.Activity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository : JpaRepository<Activity, Long> {
    // Busca todas las actividades que pertenezcan a una categoría específica
    // SELECT * FROM activity WHERE category_id = ?
    fun findByCategoryId(categoryId: Long): List<Activity>
}
