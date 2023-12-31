package com.wac.my_restuarant.Review

import com.wac.my_restuarant.Dish.Dish
import jakarta.persistence.*

@Entity
data class Review(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    var authorFirstName: String = "",
    var authorLastName: String = "",
    var email: String = "",
    var rating: Int = 0,
    var comment: String = "",
    var createdAt: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    var dish: Dish? = null,
)
