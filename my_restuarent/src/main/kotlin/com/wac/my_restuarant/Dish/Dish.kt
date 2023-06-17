package com.wac.my_restuarant.Dish

import jakarta.persistence.*
import com.wac.my_restuarant.Card.Card
import com.wac.my_restuarant.Menu.Menu

@Entity
data class Dish(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var name: String = "",

    var description: String = "",

    var image: String = "",

    var price: Double = 0.0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    var card: Card? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    var menu: Menu? = null
)
