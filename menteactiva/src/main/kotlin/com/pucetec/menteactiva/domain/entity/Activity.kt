package com.pucetec.menteactiva.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "activities")
data class Activity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 150)
    var title: String,

    @Column(name = "difficulty_level", nullable = false)
    var difficultyLevel: Int, // 1-5

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    var category: Category,

    @OneToMany(mappedBy = "activity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val questions: MutableList<Question> = mutableListOf()
)
