package com.wac.my_restuarant.Restaurant

import com.wac.my_restuarant.Persona.Persona
import jakarta.persistence.*

@Entity
data class Restaurant(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    var name: String = "",
    var url: String = "",
    var address: String = "",

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    var persona: Persona? = null,
)
