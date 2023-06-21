package com.wac.my_restuarant.Persona

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Review (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    var name: String = "",
    var password: String = "",
)
