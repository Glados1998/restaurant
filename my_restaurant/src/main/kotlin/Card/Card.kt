package Card

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType

@Entity
data class Card (
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var name: String = "",

    var price: Int = 0,

    var description: String = "",

    var image: String = ""
)