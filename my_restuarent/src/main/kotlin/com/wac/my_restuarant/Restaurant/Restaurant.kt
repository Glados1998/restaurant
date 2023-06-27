package com.wac.my_restuarant.Restaurant

import jakarta.persistence.*

@Entity
data class Restaurant(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 1,
    var name: String = "",
    var url: String = "",
    var streetAddress: String = "",
    var streetNumber: String = "",
    var city: String = "",
    var postalCode: String = "",
    var email: String = "",
    var phone: String = "",
    var mainColor: String = "",
    var secondaryColor: String = "",
    var fontColor: String = "",
    var linkColor : String = "",
    var imagePath: String = "",
    var description: String = ""
) {

}
