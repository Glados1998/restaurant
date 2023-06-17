package com.wac.my_restuarant.Card

import jakarta.persistence.*
import com.wac.my_restuarant.Dish.Dish
import com.wac.my_restuarant.Menu.Menu

@Entity
data class Card(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var name: String = "",

    @OneToMany(mappedBy = "card", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var menus: List<Menu> = mutableListOf(),

    @OneToMany(mappedBy = "card", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var standaloneDishes: List<Dish> = mutableListOf()
)
