package com.wac.my_restuarant.Restaurant

import com.wac.my_restuarant.Persona.Persona
import jakarta.persistence.*

@Entity
data class Restaurant(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 1, // always use 1 as the id
    var name: String = "",
    var url: String = "",
    var streetAddress: String = "",
    var streetNumber: String = "",
    var city: String = "",
    var postalCode: String = "",

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    var persona: Persona? = null,
)
