package com.wac.my_restuarant.Menu

import jakarta.persistence.*
import com.wac.my_restuarant.Dish.Dish
import com.wac.my_restuarant.Card.Card

@Entity
data class Menu(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var name: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    var card: Card? = null,

    @OneToMany(mappedBy = "menu", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var dishes: List<Dish> = mutableListOf()
)
