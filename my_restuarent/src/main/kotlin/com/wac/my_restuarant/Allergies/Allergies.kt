package com.wac.my_restuarant.Allergies

import jakarta.persistence.*

@Entity
data class Allergy(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var name: String = ""
)

