package com.pucetec.menteactiva.domain.repository

import com.pucetec.menteactiva.domain.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long>
