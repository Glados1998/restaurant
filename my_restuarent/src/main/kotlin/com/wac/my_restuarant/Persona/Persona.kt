package com.wac.my_restuarant.Persona

import com.wac.my_restuarant.Restaurant.Restaurant
import jakarta.persistence.*
import org.springframework.web.multipart.MultipartFile

@Entity
data class Persona (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    var mainColor: String = "",
    var secondaryColor: String = "",
    var linkColor : String = "",

    @Lob
    var headerImage : ByteArray = byteArrayOf(),

    @OneToOne(mappedBy = "persona", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var restaurant: Restaurant? = null
)
